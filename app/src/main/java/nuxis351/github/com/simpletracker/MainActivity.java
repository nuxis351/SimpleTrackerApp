package nuxis351.github.com.simpletracker;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity implements RecordFragment.OnChronoSwitchListener, RecordFragment.LocationServiceController {

    private String TAG = "MAINACTIVITYLOGTAG";

    private TabLayout tabLayout;
    private CustomViewPager viewPager;

    private LocationService locationService;
    private boolean serviceBound = false;
    private Intent locationIntent;

    LocationService.LocalBinder locationBinder;

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

    public void onStop() {
        super.onStop();
        if(serviceBound) {
            stopLocationService();
            unbindService(locationServiceConnection);
        }
        serviceBound = false;
    }

    @Override
    public void onAttachFragment(Fragment fragment){
        if(fragment instanceof RecordFragment) {
            RecordFragment recordFragment =(RecordFragment) fragment;
            recordFragment.setChronoCallbackListener(this);
            recordFragment.setLocationServiceController(this);
        }
    }

    @Override
    public void onChronoSwitch(boolean chronoState) {
        setViewPagerNavigation(chronoState);
    }

    @Override
    public void bindLocationService() {
        locationIntent = new Intent(this, LocationService.class);
        locationServiceConnection = getServiceConnection();
        bindService(locationIntent, locationServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void unBindLocationService() {
        stopLocationService();
    }

    private void stopLocationService(){
        try {
            locationService.removeLocationUpdates();
            stopService(locationIntent);
        } catch (Exception e){
            Log.i(TAG, "failed to stop LocationService", e);
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

    private ServiceConnection locationServiceConnection;

    private ServiceConnection getServiceConnection(){
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                locationBinder = (LocationService.LocalBinder) iBinder;
                locationService = locationBinder.getService();
                serviceBound = true;
                verifyLocationServiceIsRunning();
            }

            @Override
            public void onServiceDisconnected(ComponentName componentName) {
                serviceBound = false;
            }

            private void verifyLocationServiceIsRunning(){
                if(!locationService.isLocationUpdatesRequested()){
                    locationService.initializeLocationManager();
                    locationService.setupLocationUpdates();
                }
            }
        };
    }
}
