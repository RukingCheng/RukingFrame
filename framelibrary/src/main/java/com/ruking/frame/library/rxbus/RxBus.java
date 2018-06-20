package com.ruking.frame.library.rxbus;


import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;


/**
 * @author Ruking.Cheng
 * @descrilbe RxBus
 * @email 495095492@qq.com
 * @tel 18075121944
 * @date on 2017/8/23 8:54
 */
public class RxBus {
    private static volatile RxBus defaultInstance;

    private Map<Class, List<Disposable>> disposablesByEventType = new HashMap<>();
    private Map<Object, List<Class>> eventTypesBySubscriber = new HashMap<>();
    private Map<Class, List<SubscriberMethod>> subscriberMethodByEventType = new HashMap<>();
    /*stick数据*/
    private final Map<Class<?>, Object> stickyEvent = new ConcurrentHashMap<>();
    // 主题
    private final Subject<Object> bus;

    // PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
    private RxBus() {
        bus = PublishSubject.create().toSerialized();
    }

    // 单例RxBus
    public static RxBus getDefault() {
        RxBus rxBus = defaultInstance;
        if (defaultInstance == null) {
            synchronized (RxBus.class) {
                rxBus = defaultInstance;
                if (defaultInstance == null) {
                    rxBus = new RxBus();
                    defaultInstance = rxBus;
                }
            }
        }
        return rxBus;
    }

    /**
     * 提供了一个新的事件,单一类型
     *
     * @param o 事件数据
     */
    public void post(Object o) {
        synchronized (stickyEvent) {
            stickyEvent.put(o.getClass(), o);
        }
        bus.onNext(o);
    }

    /**
     * 提供了一个新的事件,根据code进行分发
     *
     * @param code 事件code
     * @param o    内容
     */
    public void post(int code, Object o) {
        bus.onNext(new RxBusMessage(code, o));
    }

    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param eventType 事件类型
     * @param <T>       泛型
     * @return Observable
     */
    private <T> Observable<T> toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }


    /**
     * 根据传递的code和 eventType 类型返回特定类型(eventType)的 被观察者
     *
     * @param <T>       <T>
     * @param code      事件code
     * @param eventType 事件类型
     * @return Observable
     */
    private <T> Observable<? extends T> toObservable(final int code, final Class<T> eventType) {
        return bus.ofType(RxBusMessage.class)
                .filter(o -> {
                    //过滤code和eventType都相同的事件
                    return o.getCode() == code && eventType.isInstance(o.getObject());
                }).map(RxBusMessage::getObject).cast(eventType);
    }


    /**
     * 根据传递的 eventType 类型返回特定类型(eventType)的 被观察者
     */
    private <T> Observable<T> toObservableSticky(final Class<T> eventType) {
        synchronized (stickyEvent) {
            Observable<T> observable = bus.ofType(eventType);
            final Object event = stickyEvent.get(eventType);

            if (event != null) {
                return observable.mergeWith(Observable.create(
                        subscriber -> subscriber.onNext(eventType.cast(event))));
            } else {
                return observable;
            }
        }
    }


    /**
     * 注册
     *
     * @param subscriber 订阅者
     */
    public void register(Object subscriber) {
          /*避免重复创建*/
        if (eventTypesBySubscriber.containsKey(subscriber)) {
            return;
        }
        Class<?> subClass = subscriber.getClass();
        Method[] methods = subClass.getDeclaredMethods();
        for (Method method : methods) {
            if (method.isAnnotationPresent(Subscribe.class)) {
                //获得参数类型
                Class[] parameterType = method.getParameterTypes();
                //参数不为空 且参数个数为1
                if (parameterType != null && parameterType.length == 1) {
                    Class eventType = parameterType[0];
                    addEventTypeToMap(subscriber, eventType);
                    Subscribe sub = method.getAnnotation(Subscribe.class);
                    int code = sub.code();
                    ThreadMode threadMode = sub.threadMode();
                    boolean sticky = sub.sticky();
                    SubscriberMethod subscriberMethod = new SubscriberMethod(subscriber, method, eventType, code, threadMode,
                            sticky);
                    if (isAdd(eventType, subscriberMethod)) {
                        addSubscriberToMap(eventType, subscriberMethod);
                        addSubscriber(subscriberMethod);
                    }
                }
            }
        }
    }


    /**
     * 检查是否已经添加过sub事件
     *
     * @param eventType        类
     * @param subscriberMethod 数据模型
     * @return 是否执行
     */
    private boolean isAdd(Class eventType, SubscriberMethod subscriberMethod) {
        boolean resulte = true;
        List<SubscriberMethod> subscriberMethods = subscriberMethodByEventType.get(eventType);
        if (subscriberMethods != null && subscriberMethods.size() > 0) {
            for (SubscriberMethod subscriberMethod1 : subscriberMethods) {
                if (subscriberMethod1.code == subscriberMethod.code &&
                        subscriberMethod.subscriber == subscriberMethod1.subscriber &&
                        subscriberMethod.eventType == subscriberMethod1.eventType) {
                    resulte = false;
                }
            }
        }
        return resulte;
    }


    /**
     * 将event的类型以订阅中subscriber为key保存到map里
     *
     * @param subscriber 订阅者
     * @param eventType  event类型
     */
    private void addEventTypeToMap(Object subscriber, Class eventType) {
        List<Class> eventTypes = eventTypesBySubscriber.get(subscriber);
        if (eventTypes == null) {
            eventTypes = new ArrayList<>();
            eventTypesBySubscriber.put(subscriber, eventTypes);
        }

        if (!eventTypes.contains(eventType)) {
            eventTypes.add(eventType);
        }
    }

    /**
     * 将注解方法信息以event类型为key保存到map中
     *
     * @param eventType        event类型
     * @param subscriberMethod 注解方法信息
     */
    private void addSubscriberToMap(Class eventType, SubscriberMethod subscriberMethod) {
        List<SubscriberMethod> subscriberMethods = subscriberMethodByEventType.get(eventType);
        if (subscriberMethods == null) {
            subscriberMethods = new ArrayList<>();
            subscriberMethodByEventType.put(eventType, subscriberMethods);
        }

        if (!subscriberMethods.contains(subscriberMethod)) {
            subscriberMethods.add(subscriberMethod);
        }
    }


    /**
     * 将订阅事件以event类型为key保存到map,用于取消订阅时用
     *
     * @param eventType  event类型
     * @param disposable 订阅事件
     */
    private void addDisposableToMap(Class eventType, Disposable disposable) {
        List<Disposable> disposables = disposablesByEventType.get(eventType);
        if (disposables == null) {
            disposables = new ArrayList<>();
            disposablesByEventType.put(eventType, disposables);
        }

        if (!disposables.contains(disposable)) {
            disposables.add(disposable);
        }
    }


    /**
     * 用RxJava添加订阅者
     *
     * @param subscriberMethod 数据类型
     */
    private void addSubscriber(final SubscriberMethod subscriberMethod) {
        Observable observable;
        if (subscriberMethod.sticky) {
            observable = toObservableSticky(subscriberMethod.eventType);
        } else {
            if (subscriberMethod.code == -1) {
                observable = toObservable(subscriberMethod.eventType);
            } else {
                observable = toObservable(subscriberMethod.code, subscriberMethod.eventType);
            }
        }
        observable = postToObservable(observable, subscriberMethod);
        observable.subscribe(new Observer() {
            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onComplete() {

            }

            @Override
            public void onSubscribe(Disposable d) {
                addDisposableToMap(subscriberMethod.eventType, d);
            }

            @Override
            public void onNext(Object o) {
                callEvent(subscriberMethod.code, o);
            }
        });
    }


    /**
     * 用于处理订阅事件在那个线程中执行
     *
     * @param observable       observable
     * @param subscriberMethod subscriberMethod
     * @return Observable
     */
    private Observable postToObservable(Observable<?> observable, SubscriberMethod subscriberMethod) {
        switch (subscriberMethod.threadMode) {
            case MAIN:
                observable.observeOn(AndroidSchedulers.mainThread());
                break;
            case NEW_THREAD:
                observable.observeOn(Schedulers.newThread());
                break;
            case COMPUTATION:
                observable.observeOn(Schedulers.computation());
                break;
            case IO:
                observable.observeOn(Schedulers.io());
                break;
            default:
                //observable.observeOn(AndroidSchedulers.mainThread());
                break;
        }
        return observable;
    }


    /**
     * 回调到订阅者的方法中
     *
     * @param code   code
     * @param object obj
     */
    private void callEvent(int code, Object object) {
        Class eventClass = object.getClass();
        List<SubscriberMethod> methods = subscriberMethodByEventType.get(eventClass);
        if (methods != null && methods.size() > 0) {
            for (SubscriberMethod subscriberMethod : methods) {

                Subscribe sub = subscriberMethod.method.getAnnotation(Subscribe.class);
                int c = sub.code();
                if (c == code) {
                    subscriberMethod.invoke(object);
                }

            }
        }
    }


    /**
     * 取消注册
     *
     * @param subscriber subscriber
     */
    public void unRegister(Object subscriber) {
        List<Class> subscribedTypes = eventTypesBySubscriber.get(subscriber);
        if (subscribedTypes != null) {
            for (Class<?> eventType : subscribedTypes) {
                unSubscribeByEventType(eventType);
                unSubscribeMethodByEventType(subscriber, eventType);
            }
            eventTypesBySubscriber.remove(subscriber);
        }
    }


    /**
     * subscriptions unsubscribe
     *
     * @param eventType eventType
     */
    private void unSubscribeByEventType(Class eventType) {
        List<Disposable> disposables = disposablesByEventType.get(eventType);
        if (disposables != null) {
            Iterator<Disposable> iterator = disposables.iterator();
            while (iterator.hasNext()) {
                Disposable disposable = iterator.next();
                if (disposable != null && !disposable.isDisposed()) {
                    disposable.dispose();
                    iterator.remove();
                }
            }
        }
    }

    /**
     * 移除subscriber对应的subscriberMethods
     *
     * @param subscriber subscriber
     * @param eventType  eventType
     */
    private void unSubscribeMethodByEventType(Object subscriber, Class eventType) {
        List<SubscriberMethod> subscriberMethods = subscriberMethodByEventType.get(eventType);
        if (subscriberMethods != null) {
            Iterator<SubscriberMethod> iterator = subscriberMethods.iterator();
            while (iterator.hasNext()) {
                SubscriberMethod subscriberMethod = iterator.next();
                if (subscriberMethod.subscriber.equals(subscriber)) {
                    iterator.remove();
                }
            }
        }
    }


    /**
     * 移除指定eventType的Sticky事件
     */
    public <T> T removeStickyEvent(Class<T> eventType) {
        synchronized (stickyEvent) {
            return eventType.cast(stickyEvent.remove(eventType));
        }
    }

    /**
     * 移除所有的Sticky事件
     */
    public void removeAllStickyEvents() {
        synchronized (stickyEvent) {
            stickyEvent.clear();
        }
    }

}
