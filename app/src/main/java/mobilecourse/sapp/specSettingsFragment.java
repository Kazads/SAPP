package mobilecourse.sapp;


import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;

/**
 * A simple {@link Fragment} subclass.
 */
public class specSettingsFragment extends Fragment {

    private onSwipeFragmentInteractionListener swipeFragment;
    private scheduleInformation scheduleInformation;
    private jsonHandler json;
    private CheckBox genSettings;
    private CheckBox notifications;
    private EditText notificationTimer;
    private CheckBox mute;
    private Button saveButton;
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
        this.genSettings = (CheckBox) view.findViewById(R.id.genSettSpec);
        this.notifications = (CheckBox) view.findViewById(R.id.NotifSpec);
        this.notificationTimer = (EditText) view.findViewById(R.id.timerSpec);
        this.mute = (CheckBox) view.findViewById(R.id.muteSpec);
        this.json = new jsonHandler();
        this.saveButton = (Button) view.findViewById(R.id.saveSpec);
        if(scheduleInformation.getFollowGenSett() == 1){
            this.genSettings.setChecked(true);
            this.notifications.setClickable(false);
            this.notificationTimer.setFocusable(false);
            this.mute.setClickable(false);
            this.notifications.setTextColor(Color.parseColor("#666666"));
            this.notificationTimer.setTextColor(Color.parseColor("#666666"));
            this.mute.setTextColor(Color.parseColor("#666666"));
        }
        if(scheduleInformation.getNotifications()== 1){
            this.notifications.setChecked(true);
        }
        this.notificationTimer.setText("" + this.scheduleInformation.getNotificationTimer());
        if(this.scheduleInformation.getMuted() == 1){
            this.mute.setChecked(true);
        }
        this.genSettings.setOnClickListener(onClickListener);
        this.saveButton.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v == genSettings) {
                if (genSettings.isChecked()) {
                    notifications.setClickable(false);
                    notificationTimer.setFocusable(false);
                    mute.setClickable(false);
                    notifications.setTextColor(Color.parseColor("#666666"));
                    notificationTimer.setTextColor(Color.parseColor("#666666"));
                    mute.setTextColor(Color.parseColor("#666666"));
                } else {
                    notifications.setClickable(true);
                    notificationTimer.setFocusable(true);
                    mute.setClickable(true);
                    notifications.setTextColor(Color.parseColor("#000000"));
                    notificationTimer.setTextColor(Color.parseColor("#000000"));
                    mute.setTextColor(Color.parseColor("#000000"));
                }
            }
            if (v == saveButton) {
                if (genSettings.isChecked()) {
                    scheduleInformation.setFollowGenSett(1);
                } else {
                    scheduleInformation.setFollowGenSett(0);
                    if (notifications.isChecked()) {
                        scheduleInformation.setNotifications(1);
                    } else {
                        scheduleInformation.setNotifications(0);
                    }
                    scheduleInformation.setNotificationTimer(Integer.parseInt(notificationTimer.toString()));
                    if (mute.isChecked()) {
                        scheduleInformation.setMuted(1);
                    } else {
                        scheduleInformation.setMuted(0);
                    }
                }
                try {
                    json.changeSpecificObject(scheduleInformation, new File(getContext().getFilesDir(), "save.json"));
                    swipeFragment.closeActivity();
                } catch (Exception e) {
                    System.out.println(e.toString());
                }

            }
        }
    };

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            swipeFragment = (onSwipeFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        swipeFragment = null;

    }



}
