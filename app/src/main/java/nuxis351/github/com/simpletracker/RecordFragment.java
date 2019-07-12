package nuxis351.github.com.simpletracker;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderApi;

/**
 * Created by nuxis on 4/23/2019.
 */

public class RecordFragment extends Fragment {
    private Chronometer chrono;
    private OnChronoSwitchListener chronoCallbackListener;
    private LocationServiceController locationServiceController;

    private Button startStopTrackerButton;
    private TextView distanceTraveled;
    private TextView topSpeed;
    private TextView avgSpeed;
    private boolean isChronRunning;
    private boolean serviceBound = false;

    private int REQUEST_PERMISSIONS_CODE_FINE_LOCATION = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        isChronRunning = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        initializeViews(view);

        startStopTrackerButton.setOnClickListener(trackerButtonOnClickListener());
    }

    @Override
    public void onStop() {
        super.onStop();
        if(serviceBound) {
            LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
        }
        serviceBound = false;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        chrono.stop();
    }

    public interface OnChronoSwitchListener {
        void onChronoSwitch(boolean chronoState);
    }
    public interface LocationServiceController {
        void bindLocationService();
        void unBindLocationService();
    }

    public void setChronoCallbackListener(OnChronoSwitchListener chronoCallbackListener){
        this.chronoCallbackListener = chronoCallbackListener;
    }
    public void setLocationServiceController(LocationServiceController locationServiceController){
        this.locationServiceController = locationServiceController;
    }

    private void initializeViews(View view){
        chrono = view.findViewById(R.id.trip_chrono);
        startStopTrackerButton = view.findViewById(R.id.start_stop_tracker_button);
        distanceTraveled = view.findViewById(R.id.rec_distance_display);
        topSpeed = view.findViewById(R.id.top_speed_display);
        avgSpeed = view.findViewById(R.id.avg_speed_display);
    }

    private View.OnClickListener trackerButtonOnClickListener(){
        return new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChronRunning) {
                    chrono.stop();
                    startStopTrackerButton.setText(getResources().getText(R.string.record_start_button_text));
                        locationServiceController.unBindLocationService();
                        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(mMessageReceiver);
                } else {
                    chrono.setBase(SystemClock.elapsedRealtime());
                    chrono.start();
                    locationServiceController.bindLocationService();
                    LocalBroadcastManager.getInstance(getActivity()).registerReceiver(mMessageReceiver, new IntentFilter("GPSLocationUpdates"));
                    startStopTrackerButton.setText(getResources().getText(R.string.record_stop_button_text));
                    serviceBound = true;
                }
                isChronRunning = !isChronRunning;
                chronoCallbackListener.onChronoSwitch(!isChronRunning);
            }
        };
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS_CODE_FINE_LOCATION) {
            if (permissions[0].equals(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                locationServiceController.bindLocationService();
            }
        }
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String message = intent.getStringExtra("Status");
            Bundle b = intent.getBundleExtra("Location");
            Location location = (Location) b.getParcelable("Location");
            if(location != null){
                distanceTraveled.setText(location.getLatitude()+"");
            }
        }
    };

}

