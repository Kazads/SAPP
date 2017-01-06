package mobilecourse.sapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 */
public class specSettingsFragment extends Fragment {

    private scheduleInformation scheduleInformation;
    public specSettingsFragment() {
        // Required empty public constructor
    }

    public static specSettingsFragment newInstance(scheduleInformation schedule){
        specSettingsFragment specSettingsFragment = new specSettingsFragment();
        Bundle args = new Bundle();
        args.putParcelable("schedule", schedule);
        specSettingsFragment.setArguments(args);
        return specSettingsFragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spec_settings, container, false);
        this.scheduleInformation = getArguments().getParcelable("schedule");
        return view;
    }

}
