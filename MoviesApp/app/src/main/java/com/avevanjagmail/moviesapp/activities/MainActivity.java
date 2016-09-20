package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.adapters.SectionsPagerAdapter;
import com.avevanjagmail.moviesapp.fragments.FavoriteTabFragment;
import com.avevanjagmail.moviesapp.fragments.NewTabFragment;
import com.avevanjagmail.moviesapp.fragments.TopTabFragment;
import com.avevanjagmail.moviesapp.presenter.MainActivityPresenter;
import com.avevanjagmail.moviesapp.view.MainActivityView;

public class MainActivity extends AppCompatActivity implements MainActivityView {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainActivityPresenter mMainActivityPresenter;
    public static void start(Context context) {
        Intent starter = new Intent(context, MainActivity.class);
        context.startActivity(starter);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMainActivityPresenter = new MainActivityPresenter();
        mMainActivityPresenter.setMainActivityView(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_tb);
        setSupportActionBar(toolbar);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.movies_list_mcv);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setOffscreenPageLimit(2);
        setupViewPager(mViewPager);
        mTabLayout = (TabLayout) findViewById(R.id.tabs_mcv);
        mTabLayout.setupWithViewPager(mViewPager);
//        mMainActivityPresenter.saveFaceBook();

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2) {
                    Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.movies_list_mcv);
                    if (fragment instanceof FavoriteTabFragment) {
//                        ((FavoriteTabFragment) fragment).update();
                    }
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void setupViewPager(ViewPager mViewPager) {
        SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(TopTabFragment.newInstance(), getApplicationContext().getString(R.string.tab_first_name));
        adapter.addFragment(NewTabFragment.newInstance(), getApplicationContext().getString(R.string.tab_second_name));
        adapter.addFragment(FavoriteTabFragment.newInstance(3), getApplicationContext().getString(R.string.tab_third_name));
        mViewPager.setAdapter(adapter);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView)
                MenuItemCompat.getActionView(searchItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                mMainActivityPresenter.startSearch(query);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;

            }
        });
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, UserActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_about) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder
                    .setTitle("About")
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                        }
                    })
                    .setView(R.layout.dialog_about);
            builder.create().show();

        }
        if (id == R.id.action_logout) {
            mMainActivityPresenter.logOut();

        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void finishLogOut() {
        finish();
    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        mMainActivityPresenter.onDestroy();
//    }
}
