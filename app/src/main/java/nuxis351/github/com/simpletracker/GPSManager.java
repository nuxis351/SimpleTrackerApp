package nuxis351.github.com.simpletracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;

/**
 * Created by nuxis on 5/1/2019.
 */

public class GPSManager {
    private Location previousLocation;
    private double distance;
    private double topSpeed;
    private double avgSpeed;

    private boolean locationSkipped;

    final private double MIN_LOCATION_ACCURACY = 10;

    public GPSManager(){
        previousLocation = new Location("dummyprovidor");
        distance = 0;
        topSpeed = 0;
        avgSpeed = 0;
    }

    public void tick(Location currentLocation){
        if(currentLocation != null){
            if(verifyLocationAccuracy(currentLocation)){
                if(locationSkipped){
                    locationSkipped = false;
                    previousLocation.set(currentLocation);
                } else {
                    distance += previousLocation.distanceTo(currentLocation);
                    previousLocation.set(currentLocation);
                }
                if(currentLocation.hasSpeed()){
                    if(currentLocation.getSpeed() > topSpeed){
                        topSpeed = currentLocation.getSpeed();
                    }
                }
            } else {
                locationSkipped = true;
            }
        }
    }

    private boolean verifyLocationAccuracy(Location location){
        return location.getAccuracy() < MIN_LOCATION_ACCURACY;
    }

    public double getDistance(){
        return this.distance;
    }
    public double getTopSpeed(){
        return this.topSpeed;
    }
    public double getAvgSpeed(){
        return this.avgSpeed;
    }
}
