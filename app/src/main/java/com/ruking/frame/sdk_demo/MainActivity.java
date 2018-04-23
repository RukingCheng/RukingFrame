package com.ruking.frame.sdk_demo;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.NavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ruking.frame.library.base.RKBaseActivity;
import com.ruking.frame.library.base.RKTransitionMode;
import com.ruking.frame.library.view.SnackbarUtil;
import com.ruking.frame.sdk_demo.recycleviewdemo.LFRecyclerViewActivity;

import butterknife.OnClick;

public class MainActivity extends RKBaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

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
                type = 0;
                break;
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
        //            SnackbarUtil.ShortSnackbar(coordinator,"妹子向你发来一条消息",SnackbarUtil.Info).show();
        Snackbar snackbar = SnackbarUtil.ShortSnackbar(coordinator, "这是测试适配的ImageView", SnackbarUtil.Warning)
                .setActionTextColor(Color.RED).setAction("再次发送",
                        v -> SnackbarUtil.LongSnackbar(coordinator, "这真的是ImageView", SnackbarUtil.Alert)
                                .setActionTextColor(Color.WHITE).show());
//        SnackbarUtil.SnackbarAddView(snackbar, R.layout.nav_header_main, 0);
//        SnackbarUtil.SnackbarAddView(snackbar, R.layout.nav_header_main, 2);
        snackbar.show();
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
