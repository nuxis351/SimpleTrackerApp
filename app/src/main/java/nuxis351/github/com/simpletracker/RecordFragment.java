package nuxis351.github.com.simpletracker;

import android.os.Bundle;
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
    Chronometer chrono;
    Button startTrackerButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.record_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        initializeViews(view);

        startTrackerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chrono.start();
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
        startTrackerButton = view.findViewById(R.id.start_stop_tracker_button);
    }
}
