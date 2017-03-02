package mysupercompany.photomapapp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mat on 01/03/2017.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> FragmentList = new ArrayList<>();
    private final List<String> FragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return FragmentList.get(position);
    }

    @Override
    public int getCount() {
        return FragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        FragmentList.add(fragment);
        FragmentTitleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return FragmentTitleList.get(position);
    }
}