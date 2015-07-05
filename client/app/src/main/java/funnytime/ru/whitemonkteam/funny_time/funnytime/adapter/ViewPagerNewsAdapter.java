package funnytime.ru.whitemonkteam.funny_time.funnytime.adapter;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import funnytime.ru.whitemonkteam.funny_time.funnytime.R;
import funnytime.ru.whitemonkteam.funny_time.funnytime.fragments.load.ListNewsFragment;

/**
 * Created by Андрей on 24.05.2015.
 */
public class ViewPagerNewsAdapter extends FragmentPagerAdapter
{
    private static final int PAGE_COUNT = 3;

    private Activity context;;

    public ViewPagerNewsAdapter(FragmentManager fm,  Activity context) {
        super(fm);
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return ListNewsFragment.newInstanse(position);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String [] array = context.getResources().getStringArray(R.array.strViewPagerTitle);

        return array[position];
    }
}
