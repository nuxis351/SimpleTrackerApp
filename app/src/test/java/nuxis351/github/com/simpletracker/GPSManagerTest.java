package nuxis351.github.com.simpletracker;

import android.location.Location;
import android.os.SystemClock;

import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

@RunWith(RobolectricTestRunner.class)
@Config(manifest= Config.NONE)

public class GPSManagerTest {

    static GPSManager gpsManager;

    float unacceptableAccuracy = 5f;
    float maxAcceptableAccuracy = 4.99f;
    float minAcceptableAccuracy = 0f;

    double[] previousLocationCords = {41.070980, -71.856628};
    double[] currentCloseLocationCords = {41.070987, -71.856647};
    double[] currentFarLocationCords = {41.071000, -71.856699};

    @BeforeClass
    public static void testSetup() {
        gpsManager = new GPSManager(0);
    }

    @Test
    public void previousLocationInsideCurrentLocationAccuracyTest(){
        tickTwoLocations(previousLocationCords, currentCloseLocationCords, maxAcceptableAccuracy);

        assertTrue(Arrays.equals(pullCordinates(gpsManager.getPreviousLocation()), previousLocationCords));
    }

    @Test
    public void previousLocationOutsideCurrentLocationAccuracyTest(){
        tickTwoLocations(previousLocationCords, currentFarLocationCords, maxAcceptableAccuracy);

        assertTrue(Arrays.equals(pullCordinates(gpsManager.getPreviousLocation()), currentFarLocationCords));
    }

    @Test
    public void InaccurateLocationTest(){
        tickTwoLocations(previousLocationCords, currentCloseLocationCords, maxAcceptableAccuracy, unacceptableAccuracy);

        assertTrue(Arrays.equals(pullCordinates(gpsManager.getPreviousLocation()), previousLocationCords));
    }

    private void tickTwoLocations(double[] locCords1, double[] locCords2, float accuracy1, float accuracy2){
        gpsManager.tick(assignLocationValues(locCords1, accuracy1));
        gpsManager.tick(assignLocationValues(locCords2, accuracy2));
    }
    private void tickTwoLocations(double[] locCords1, double[] locCords2, float accuracy){
        tickTwoLocations(locCords1,locCords2,accuracy,accuracy);
    }
    private double[] pullCordinates(Location location){
        double[] locationCords = {location.getLongitude(), location.getLatitude()};
        return locationCords;
    }
    private Location assignLocationValues(double[] cords, float accuracy){
        Location location = new Location("dummyprovider");
        location.setLongitude(cords[0]);
        location.setLatitude(cords[1]);
        location.setAccuracy(accuracy);
        return location;
    }
}
