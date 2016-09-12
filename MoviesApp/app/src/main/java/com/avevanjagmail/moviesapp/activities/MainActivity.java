package com.avevanjagmail.moviesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.avevanjagmail.moviesapp.fragments.FavoriteTabFragment;
import com.avevanjagmail.moviesapp.fragments.NewTabFragment;
import com.avevanjagmail.moviesapp.fragments.TopTabFragment;
import com.avevanjagmail.moviesapp.R;
import com.avevanjagmail.moviesapp.presenter.MainActivityPresenter;
import com.avevanjagmail.moviesapp.view.MainActivityView;
import com.facebook.Profile;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainActivityView {


    private SectionsPagerAdapter mSectionsPagerAdapter;
    private Profile profile;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;
    private MainActivityPresenter mMainActivityPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_main);
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
        adapter.addFragment(TopTabFragment.newInstance(), "Top");
        adapter.addFragment(NewTabFragment.newInstance(), "New");
        adapter.addFragment(FavoriteTabFragment.newInstance(3), "Favorite");
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

                Intent intent = new Intent(getApplicationContext(), SearchActivity.class);
                String word = getIntent().getStringExtra("mail");
                intent.putExtra("query", query);
                intent.putExtra("mail",word);
                startActivity(intent);
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
        if (id == R.id.action_logout) {

            profile = Profile.getCurrentProfile();
            if (profile == null) {
                mMainActivityPresenter.logout();

            }
            else {
                mMainActivityPresenter.logOutFromFB();
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
            }

            return super.onOptionsItemSelected(item);
        }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public void setLogOut(boolean success) {
        if(success){
            Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
            startActivity(intent);
            finish();}


        else {
            Toast.makeText(getApplicationContext(), "Something wrong", Toast.LENGTH_LONG).show();
        }
    }


    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> mFragmentList = new ArrayList<>();
        private ArrayList<String> mFragmentListTitle = new ArrayList<>();

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        public void addFragment(Fragment fragment, String title){
            mFragmentList.add(fragment);
            mFragmentListTitle.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
           return mFragmentListTitle.get(position);
        }
    }
}
