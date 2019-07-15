package nuxis351.github.com.simpletracker;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.os.SystemClock;

/**
 * Created by nuxis on 5/1/2019.
 */

public class GPSManager {
    private Location previousLocation;
    private double distance;
    private double topSpeed;
    private double avgSpeed;

    private boolean locationSkipped;

    static private long CHRONO_BASE;
    final private double MIN_LOCATION_ACCURACY = 3;

    public GPSManager(long CHRONO_BASE){
        this.CHRONO_BASE = CHRONO_BASE;
        this.previousLocation = new Location("dummyprovidor");
        this.locationSkipped = true;
        this.distance = 0;
        this.topSpeed = 0;
        this.avgSpeed = 0;
    }

    public void tick(Location currentLocation){
        if(currentLocation != null){
            if(verifyLocationAccuracy(currentLocation)){
                if(locationSkipped){
                    locationSkipped = false;
                } else {
                    setDistance(calculateDistance(previousLocation, currentLocation, distance));
                    setTopSpeed(calculateTopSpeed(topSpeed, currentLocation));
                    calculateAvgSpeed(distance, getElapsedTimeMillis());
                }
                if(!locationSkipped) {
                    previousLocation.set(currentLocation);
                }

            } else {
                locationSkipped = true;
            }
        }
    }

    private double calculateDistance(Location previousLocation, Location currentLocation, double totalDistance){
        if(currentLocation.getAccuracy() < previousLocation.distanceTo(currentLocation)) {
            locationSkipped = true;
            return -1;
        }
        return totalDistance + previousLocation.distanceTo(currentLocation);
    }
    private double calculateTopSpeed(double previousTopSpeed, Location currentLocation){
        if(currentLocation.hasSpeed()){
            //convert speed from meters/second here
            if(currentLocation.getSpeed() > previousTopSpeed){
                return currentLocation.getSpeed();
            }
        }
        return previousTopSpeed;
    }
    private double calculateAvgSpeed(double distance, long ElapsedTime){ //assumes distance as miles/kilometers
        return distance / ((double) ((ElapsedTime / (1000*60*60)) % 24)); // convert millis elapsed time to hours. divide distance by hours.
    }
    private long getElapsedTimeMillis(){
        return SystemClock.elapsedRealtime() - CHRONO_BASE;
    }

    private boolean verifyLocationAccuracy(Location location){
        return location.getAccuracy() < MIN_LOCATION_ACCURACY;
    }

    private void setTopSpeed(double topSpeed){
        if(this.topSpeed < topSpeed){
            this.topSpeed = topSpeed;
        }
    }
    private void setDistance(double distance){
        if(distance > 0)
            this.distance = distance; // code to change meters to feet / miles / kilometer goes here?
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
