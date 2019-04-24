package nuxis351.github.com.simpletracker;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by nuxis on 4/23/2019.
 */

public class PagerAdapter extends FragmentPagerAdapter {

    int numTabs;

    public PagerAdapter(FragmentManager fm, int numTabs){
        super(fm);
        this.numTabs = numTabs;
    }

    @Override
    public Fragment getItem(int position) {
        Log.v("tag",""+position);
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
        return numTabs;
    }
}
