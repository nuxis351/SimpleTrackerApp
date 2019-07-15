package nuxis351.github.com.simpletracker;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

/**
 * Created by nuxis on 4/29/2019.
 */

public class LocationService extends Service {
    private static final String TAG = "GPSLOGTAG";
    protected LocationManager locationManager = null;

    private static final long LOCATION_INTERVAL = 1000;
    private static final float LOCATION_DISTANCE = 0; // set distance interval to 0 to prevent missed distance

    private boolean locationUpdatesRequested = false;

    private final IBinder binder = new LocalBinder();

    private LocationListener locationListener = new LocationListener(LocationManager.GPS_PROVIDER);

    private class LocationListener implements android.location.LocationListener{
        Location lastLocation;

        public LocationListener(String provider){
            Log.e(TAG, "LocationListener " + provider);
            lastLocation = new Location(provider);
        }

        @Override
        public void onLocationChanged(Location location) {
            Log.v(TAG, "IN ON LOCATION CHANGE, lat=" + location.getLatitude() + ", lon=" + location.getLongitude());
            Log.v(TAG, "IN ON LOCATION CHANGE, accuracy=" + location.getAccuracy());
            sendMessageToActivity(location, "GPS Location");
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
        }

        @Override
        public void onProviderEnabled(String s) {
        }

        @Override
        public void onProviderDisabled(String s) {
        }
    }

    public LocationService(){
        super();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    public LocationService(Context context){
        locationManager = (LocationManager) context.getSystemService(LOCATION_SERVICE);
    }

    public class LocalBinder extends Binder {
        LocationService getService(){
            return LocationService.this;
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");
        super.onCreate();
        initializeLocationManager();
        setupLocationUpdates();
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        removeLocationUpdates();
        super.onDestroy();
    }

    public final void removeLocationUpdates(){
        if(locationManager != null) {
            try {
                locationManager.removeUpdates(locationListener);
                setLocationUpdatesRequested(false);
            } catch (Exception e) {
                Log.i(TAG, "failed to remove location listeners", e);
            }
        }
    }

    public void initializeLocationManager(){
        if(locationManager == null){
            locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        }
    }

    public void setupLocationUpdates(){
        try{
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER, LOCATION_INTERVAL, LOCATION_DISTANCE, locationListener);
            setLocationUpdatesRequested(true);
        } catch (java.lang.SecurityException e){
            Log.i(TAG, "failed to request location update", e);
        }
    }

    public boolean isLocationUpdatesRequested(){
        return locationUpdatesRequested;
    }

    private void setLocationUpdatesRequested(boolean locationUpdatesRequested){
            if(this.locationUpdatesRequested == locationUpdatesRequested){
                throw new IllegalArgumentException("locationUpdatesRequested already set to value " +locationUpdatesRequested);
            } else {
                this.locationUpdatesRequested = locationUpdatesRequested;
            }
    }

    private void sendMessageToActivity(Location l, String msg){
        Intent intent = new Intent("GPSLocationUpdates");
        intent.putExtra("Status", msg);
        Bundle b = new Bundle();
        b.putParcelable("Location", l);
        intent.putExtra("Location", b);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
    }
}
