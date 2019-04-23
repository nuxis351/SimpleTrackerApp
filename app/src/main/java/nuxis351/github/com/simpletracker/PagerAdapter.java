package nuxis351.github.com.simpletracker;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by nuxis on 4/23/2019.
 */

public class PagerAdapter extends FragmentStatePagerAdapter {

    int numTabs;

    public PagerAdapter(FragmentManager fm, int numTabs){
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                TrackerListFragment trackListFrag = new TrackerListFragment();
                return trackListFrag;
            case 1:
                RecordListFragment recListFrag = new RecordListFragment();
                return recListFrag;
            case 2:
                RecordFragment recFrag = new RecordFragment();
                return recFrag;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 0;
    }
}
