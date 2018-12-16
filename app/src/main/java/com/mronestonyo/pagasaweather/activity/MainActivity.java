package com.mronestonyo.pagasaweather.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mronestonyo.pagasaweather.R;
import com.mronestonyo.pagasaweather.adapter.MenuLeftDrawerRecyclerAdapter;
import com.mronestonyo.pagasaweather.base.BaseActivity;
import com.mronestonyo.pagasaweather.fragments.WeatherInformationMainScreenFragment;
import com.yarolegovich.slidingrootnav.SlidingRootNav;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    //    private RecyclerView recyclerView;
    private MenuLeftDrawerRecyclerAdapter mAdapter;
    private List<String> mGridData;

    private SlidingRootNav slidingRootNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        slidingRootNav = new SlidingRootNavBuilder(this)
                .withToolbarMenuToggle(toolbar)
                .withMenuOpened(false)
                .withContentClickableWhenMenuOpened(false)
                .withSavedState(savedInstanceState)
                .withMenuLayout(R.layout.menu_left_drawer)
                .inject();

        mAdapter = new MenuLeftDrawerRecyclerAdapter(getViewContext());

        RecyclerView list = findViewById(R.id.list);
        list.setNestedScrollingEnabled(false);
        list.setLayoutManager(new LinearLayoutManager(this));
        list.setAdapter(mAdapter);

        initData();
    }

    private void initData() {
        mGridData = new ArrayList<>();
        mGridData.add("London");
        mGridData.add("Prague");
        mGridData.add("San Francisco");
        mAdapter.addAll(mGridData);

        ImageView imgProfileImage = findViewById(R.id.imgMenuProfileImage);
        Glide.with(getViewContext())
                .load(R.drawable.menu_drawer_image_sample)
                .apply(RequestOptions.circleCropTransform())
                .into(imgProfileImage);

        int mIndexSelected = getIntent().getIntExtra("index", 0);
        Bundle args = getIntent().getBundleExtra("args");
        displayView(mIndexSelected, args);
    }

    public void displayView(int position, Bundle args) {
        Fragment fragment = null;
        String mTitle = "Pagasa";
        switch (position) {
            case 0: {
                if(args == null){
                    args = new Bundle();
                    args.putString("LOCATION_NAME", mGridData.get(position));
                }
                mTitle = mGridData.get(position);
                fragment = new WeatherInformationMainScreenFragment();
                break;
            }
        }

        if (args != null) {
            fragment.setArguments(args);
        }

        if (fragment != null) {
            FragmentManager manager = getSupportFragmentManager();
            manager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            FragmentTransaction transaction = manager.beginTransaction();
            transaction.replace(R.id.container, fragment);
            transaction.addToBackStack(null);
            transaction.commit();
        }

            // set the toolbar title
            setActionBarTitle(mTitle);
    }

    public void setActionBarTitle(String title) {
        setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 1) {
            getSupportFragmentManager().popBackStack();
        } else {
            finish();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_screen, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public static void start(Context context, int index, Bundle args) {
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("args", args);
        intent.putExtra("index", index);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
