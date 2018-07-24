# RukingFrame
这是一个自定义框架

## Step 1. Add the JitPack repository to your build file
	
```
  allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
  
## Step 2. Add the dependency
### 自适应框架
``` 
     implementation 'com.github.RukingCheng.RukingFrame:autolayout:1.0.3.0'
 ```
     代码来源：[https://github.com/hongyangAndroid/AndroidAutoLayout](https://github.com/hongyangAndroid/AndroidAutoLayout)
   
### RK框架
``` 
     implementation 'com.github.RukingCheng.RukingFrame:framelibrary:1.0.3.0'
```
  
### 二维码扫一扫
``` 
     implementation 'com.github.RukingCheng.RukingFrame:simplezxing:1.0.3.0'
```
     代码来源：[https://github.com/GuoJinyu/SimpleZXing](https://github.com/GuoJinyu/SimpleZXing)

### 图片选择器
```
     implementation 'com.github.RukingCheng.RukingFrame:photolibrary:1.0.3.0'
```
#### 需要添加
```
    //glide
    implementation 'com.github.bumptech.glide:glide:4.7.1'
    annotationProcessor 'com.github.bumptech.glide:compiler:4.7.1'
```


      
