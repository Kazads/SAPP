package mobilecourse.sapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class specInfoFragment extends Fragment {
    private scheduleInformation scheduleInformation;
    private TextView specInfo;

    public specInfoFragment() {
        // Required empty public constructor
    }

    public static specInfoFragment newInstance(scheduleInformation schedule){
        specInfoFragment specInfoFragment = new specInfoFragment();
        Bundle args = new Bundle();
        args.putParcelable("schedule", schedule);
        specInfoFragment.setArguments(args);
        return specInfoFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.spec_info, container, false);
        this.scheduleInformation = getArguments().getParcelable("schedule");
        this.specInfo = (TextView) view.findViewById(R.id.specInfo);
        this.specInfo.setText(this.scheduleInformation.toString());
        return view;
    }

}
