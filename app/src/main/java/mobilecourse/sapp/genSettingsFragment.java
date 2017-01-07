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

import java.io.File;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class genSettingsFragment extends Fragment {

    private CheckBox notifications;
    private EditText notificationTimer;
    private CheckBox mute;
    private Button saveButton;
    private jsonHandler json;
    private TextView message;
    public genSettingsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gen_settings, container, false);
        this.notifications = (CheckBox) view.findViewById(R.id.notifGen);
        this.notificationTimer = (EditText) view.findViewById(R.id.notifTimerGen);
        this.mute = (CheckBox) view.findViewById(R.id.muteGen);
        this.saveButton =  (Button) view.findViewById(R.id.saveGenSettings);
        this.json = new jsonHandler();
        this.message = (TextView) view.findViewById(R.id.genSettMessage);
        try {
            Vector<String> jsonInfo = json.getGeneralSettings(new File(getContext().getFilesDir(), "save.json"));
        }catch(Exception e){
            this.notifications.setClickable(false);
            this.notificationTimer.setFocusable(false);
            this.mute.setClickable(false);
            this.message = (TextView) view.findViewById(R.id.genSettMessage);
            message.setText("Load data from the search tab");
        }

        this.saveButton.setOnClickListener(onClickListener);
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v == saveButton){
                int note,timer,muted;
                if(notifications.isChecked()){
                    note = 1;
                }else{
                    note = 0;
                }
                timer = Integer.parseInt(notificationTimer.getText().toString());
                if(mute.isChecked()){
                    muted = 1;
                }else{
                    muted = 0;
                }
                try{
                    json.setGeneralSettings(new File(getContext().getFilesDir(), "save.json"),note,timer,muted);
                    message.setText("Data saved");
                }catch(Exception e){
                    message.setText("Data couldn't be saved");
                }

            }
        }
    };


}
