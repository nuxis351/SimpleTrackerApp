package nuxis351.github.com.simpletracker;

import android.content.res.Resources;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements RecordFragment.OnChronoSwitchListener {

    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeActivityElements();

        TabInfo tabInfo = new TabInfo(tabLayout);
        viewPager.setAdapter(createPagerAdapter(tabLayout));

        tabLayout.setupWithViewPager(viewPager);
        tabInfo.setTabIcons(tabLayout);
        tabInfo.setTabTexts(tabLayout);

        viewPager.setCurrentItem(2);
    }

    @Override
    public void onAttachFragment(Fragment fragment){
        if(fragment instanceof RecordFragment) {
            RecordFragment recordFragment =(RecordFragment) fragment;
            recordFragment.setChronoCallbackListener(this);
        }
    }

    private PagerAdapter createPagerAdapter(TabLayout tabLayout){
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        return pagerAdapter;
    }
    private void initializeActivityElements(){
        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);
    }

    @Override
    public void onChronoSwitch(boolean chronoState) {
        setViewPagerNavigation(chronoState);
    }

    public void setViewPagerNavigation(boolean pageingEnabled){
        viewPager.setPagingEnabled(pageingEnabled);
        setTabChildrenClickableValue(pageingEnabled);
    }

    private void setTabChildrenClickableValue(boolean isClickable){
        LinearLayout tabStrip = ((LinearLayout) tabLayout.getChildAt(0));
        if(isClickable)
            tabStrip.setAlpha(1f);
        else
            tabStrip.setAlpha(0.5f);
        tabStrip.setEnabled(isClickable);
        for(int i = 0; i < tabStrip.getChildCount(); i++){
            tabStrip.getChildAt(i).setClickable(isClickable);
        }
    }

    public CustomViewPager getViewPager(){
        return this.viewPager;
    }
}
