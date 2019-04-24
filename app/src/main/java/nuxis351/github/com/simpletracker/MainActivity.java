package nuxis351.github.com.simpletracker;

import android.graphics.drawable.Drawable;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    FrameLayout container;
    FragmentManager fragmentManager;
    RecordFragment recordFragment;

    TabLayout tabLayout;
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tabLayout = findViewById(R.id.tabs);
        viewPager = findViewById(R.id.pager);

        TabInfo tabInfo = new TabInfo(tabLayout);
        viewPager.setAdapter(createPagerAdapter(tabLayout));

        tabLayout.setupWithViewPager(viewPager);
        tabInfo.setTabIcons(tabLayout);
        tabInfo.setTabTexts(tabLayout);
    }

    private PagerAdapter createPagerAdapter(TabLayout tabLayout){
        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        return pagerAdapter;
    }
}
