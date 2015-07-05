package funnytime.ru.whitemonkteam.funny_time.funnytime.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.SecondActivity;
import funnytime.ru.whitemonkteam.funny_time.funnytime.adapter.ViewPagerNewsAdapter;

/**
 * Created by Андрей on 24.05.2015.
 */
public class ViewPagerNewsFragment extends Fragment
{
    ViewPager viewPager;
    ViewPagerNewsAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.vp_layout, container,  false);

        viewPager = (ViewPager) v.findViewById(R.id.pager);

        SecondActivity activity = (SecondActivity) getActivity();

        FragmentManager fragmentManager = activity.getSupportFragmentManager();
        adapter = new ViewPagerNewsAdapter(fragmentManager, getActivity());

        viewPager.setAdapter(adapter);


        return v;
    }

}
