package mobilecourse.sapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenSettingsFragment extends Fragment {

    private OnFragmentInteractionListener fragmentChange;
    private CheckBox notifications;
    private EditText notificationTimer;
    private CheckBox mute;
    private Button saveButton;
    public GenSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gen_settings, container, false);
        this.notifications = (CheckBox) view.findViewById(R.id.notifGen);
        this.notificationTimer = (EditText) view.findViewById(R.id.notifTimerGen);
        this.mute = (CheckBox) view.findViewById(R.id.muteGen);
        this.saveButton =  (Button) view.findViewById(R.id.saveGenSettings);

        this.saveButton.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == saveButton){
                System.out.println("Notifications: "+ notifications.isChecked());
                System.out.println("Timer: " + notificationTimer.getText().toString());
                System.out.println("Mute: " + mute.isChecked());
            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentChange = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentChange = null;

    }

}
