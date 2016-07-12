package com.avevanjagmail.moviesapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by John on 10.07.2016.
 */
public  class TopTabFragment extends Fragment {
    RecyclerView rv;
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public TopTabFragment() {

    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static TopTabFragment newInstance(int sectionNumber) {
        TopTabFragment fragment = new TopTabFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View parentView = inflater.inflate(R.layout.fragment_main, container, false);
        rv = (RecyclerView) parentView.findViewById(R.id.rv);
        rv.setAdapter(new RvMovieAdapter(Movie.initializeData()));
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        return parentView;
    }
}

/**
 * A {@link android.support.v4.app.FragmentPagerAdapter} that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */