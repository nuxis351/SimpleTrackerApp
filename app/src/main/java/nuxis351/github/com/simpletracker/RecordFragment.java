package nuxis351.github.com.simpletracker;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Chronometer;

/**
 * Created by nuxis on 4/23/2019.
 */

public class RecordFragment extends Fragment {
    private Chronometer chrono;
    private Button startStopTrackerButton;

    OnChronoSwitchListener chronoCallbackListener;

    private boolean isChronRunning;

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

        startStopTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (isChronRunning)
                    chrono.stop();
                else {
                    chrono.setBase(SystemClock.elapsedRealtime());
                    chrono.start();
                }
                isChronRunning = !isChronRunning;
                chronoCallbackListener.onChronoSwitch(!isChronRunning);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        chrono.stop();
    }

    private void initializeViews(View view){
        chrono = view.findViewById(R.id.trip_chrono);
        startStopTrackerButton = view.findViewById(R.id.start_stop_tracker_button);
    }

    public interface OnChronoSwitchListener {
        public void onChronoSwitch(boolean chronoState);
    }

    public void setChronoCallbackListener(OnChronoSwitchListener chronoCallbackListener){
        this.chronoCallbackListener = chronoCallbackListener;
    }
}
