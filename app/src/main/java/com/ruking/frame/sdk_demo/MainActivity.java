package com.ruking.frame.sdk_demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.photolibrary.activity.ImagesActivity;
import com.ruking.frame.library.base.RKBaseActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.sdk_demo.picturedemo.PhotoDemoActivity;
import com.ruking.frame.sdk_demo.recycleviewdemo.LFRecyclerViewActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends RKBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.imageView2)
    ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show());
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @OnClick({R.id.LEFT, R.id.TOP, R.id.RIGHT, R.id.BOTTOM, R.id.SCALE, R.id.FADE, R.id.elasticScrollActivity, R.id.listView, R.id.CategoryActivity, R.id.dialogactivity})
    void OnClick(View view) {
        int type = 0;
        switch (view.getId()) {
            case R.id.LEFT:
//                type = 0;
//                break;
                readyGo(PhotoDemoActivity.class);
                return;
            case R.id.TOP:
                type = 1;
                break;
            case R.id.RIGHT:
                type = 2;
                break;
            case R.id.BOTTOM:
                type = 3;
                break;
            case R.id.SCALE:
                type = 4;
                break;
            case R.id.FADE:
                type = 5;
                break;
            case R.id.elasticScrollActivity:
                readyGo(ElasticScrollActivity.class);
                return;
            case R.id.dialogactivity:
                readyGo(DialogActivity.class);
                return;
            case R.id.CategoryActivity:
                readyGo(CategoryActivity.class);
                return;
            case R.id.listView:
                readyGo(LFRecyclerViewActivity.class);
                return;
        }
        Bundle bundle = new Bundle();
        bundle.putInt("type", type);
        readyGo(TransitionModeActivity.class, bundle);
    }

    @OnClick({R.id.imageView2})
    void imageView(View coordinator) {
//        //            SnackbarUtil.ShortSnackbar(coordinator,"妹子向你发来一条消息",SnackbarUtil.Info).show();
//        Snackbar snackbar = SnackbarUtil.ShortSnackbar(coordinator, "这是测试适配的ImageView", SnackbarUtil.Warning)
//                .setActionTextColor(Color.RED).setAction("再次发送",
//                        v -> SnackbarUtil.LongSnackbar(coordinator, "这真的是ImageView", SnackbarUtil.Alert)
//                                .setActionTextColor(Color.WHITE).show());
////        SnackbarUtil.SnackbarAddView(snackbar, R.layout.nav_header_main, 0);
////        SnackbarUtil.SnackbarAddView(snackbar, R.layout.nav_header_main, 2);
//        snackbar.show();
        ImagesActivity.starUrlsActivity(activity, getImages(), imageView2, 1);

    }

    public static List<String> getImages() {
        ArrayList<String> images = new ArrayList<>();
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526031541347&di=4d3fad5623d659adec6a6c69361d7e4d&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F015ace5743c5f36ac725550b92c3ea.gif");
        images.add("http://img3.imgtn.bdimg.com/it/u=524208507,12616758&fm=206&gp=0.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526032331918&di=9b4606f600d480d665d1391e696b8f4a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20171201%2Ff975b1a61b3b4c45b02038ebe7fe3861.gif");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526032331918&di=a2a8da59b57c837972022c994bd55b79&imgtype=0&src=http%3A%2F%2Fimg.zcool.cn%2Fcommunity%2F0175a85875e338a801219c77ea934e.gif");
        images.add("http://img5.imgtn.bdimg.com/it/u=3191365283,111438732&fm=21&gp=0.jpg");
        images.add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1526894998&di=d5114e996aed4e470995c4d4cc8b9043&imgtype=jpg&er=1&src=http%3A%2F%2Fimg5.duitang.com%2Fuploads%2Fblog%2F201412%2F04%2F20141204184751_NAH3T.thumb.700_0.gif");
        images.add("http://img4.imgtn.bdimg.com/it/u=2440866214,1867472386&fm=21&gp=0.jpg");
        images.add("http://img3.imgtn.bdimg.com/it/u=3040385967,1031044866&fm=21&gp=0.jpg");
        images.add("http://img1.imgtn.bdimg.com/it/u=1832737924,144748431&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=2091366266,1524114981&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=2091366266,1524114981&fm=21&gp=0.jpg");
        images.add("http://img5.imgtn.bdimg.com/it/u=1424970962,1243597989&fm=21&gp=0.jpg");
        return images;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean isBindEventBusHere() {
        return false;
    }

    @Override
    public int getStatusBarColor() {
//        return Color.parseColor("#000");
        return ContextCompat.getColor(activity, R.color.color_9000);
    }

    @Override
    public int getStatusBarPlaceColor() {
        return ContextCompat.getColor(activity, R.color.colorPrimaryDark);
    }

    @Override
    public boolean isShowStatusBarPlaceColor() {
        return false;
    }

    @Override
    public boolean isWindowSetting() {
        return false;
    }

    @Override
    public boolean toggleOverridePendingTransition() {
        return false;
    }

    @Override
    public RKTransitionMode getOverridePendingTransitionMode() {
        return null;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_camera) {
            // Handle the camera action
            startActivity(new Intent(activity, BusinessCaptureActivity.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
