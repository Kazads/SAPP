package mobilecourse.sapp;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Scanner;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowFragment extends Fragment {

    private OnFragmentInteractionListener fragmentChange;
    private Vector<scheduleInformation> schedule;
    private Vector<View> scheduleGUI;
    private LinearLayout showLayout;


    public ShowFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        this.showLayout = (LinearLayout) view.findViewById(R.id.show_layout);
        this.schedule = new Vector<>();
        this.scheduleGUI = new Vector<>();
        this.readSchedule();
        View box;
        TextView course;
        TextView time;
        TextView room;
        for(int i = 0; i < schedule.size();i++){
            box = inflater.inflate(R.layout.show_post, container,false);
            course = (TextView) box.findViewById(R.id.showCourse);
            time = (TextView) box.findViewById(R.id.showTime);
            room = (TextView) box.findViewById(R.id.showRoom);
            course.setText(this.schedule.elementAt(i).getCourse());
            time.setText(this.schedule.elementAt(i).getStartTime());
            room.setText(this.schedule.elementAt(i).getRoom());
            this.scheduleGUI.add(box);
            this.showLayout.addView(this.scheduleGUI.elementAt(scheduleGUI.size()-1));
            this.scheduleGUI.elementAt(this.scheduleGUI.size()-1).setOnClickListener(onClickListener);
        }
        return view;
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            for(int i = 0; i < schedule.size(); i++){
               if(v == scheduleGUI.elementAt(i)) {
                   fragmentChange.changeActivity(schedule.elementAt(i));
               }
            }

        }
    };
    
    public void readSchedule(){
        BufferedWriter input;
        BufferedReader output;
        try{
            File file = new File(getContext().getFilesDir(), "test.json");
            /*input = new BufferedWriter(new FileWriter(file));
            input.write("{'columnheaders':['Kurs','Moment','Grupp','Person','Lokal','Text','URL','Mitt namn','Kurs/program'],'info':{'reservationlimit':200,'reservationcount':1},'reservations':[{'id':'151430','startdate':'2017-01-09','starttime':'10:00','enddate':'2017-01-09','endtime':'12:00','columns':['DV1519, DV1537, DV1553','','','Betty Bergqvist, Mats-Ola Landbris','Lärosal C245','Friv. frågetillf. inför tenta','','','']}]}");
            input.close();*/
            output = new BufferedReader(new FileReader(file));
            String line;
            String resultLine = "";
            while((line = output.readLine()) != null){
                resultLine += line;
            }
            output.close();

            JSONObject obj = new JSONObject(resultLine);
            JSONArray arr = obj.getJSONArray("reservations");

            String origString;
            int startPlace;
            int endPlace;
            String course;
            String person;
            String room;
            String text;
            String startDate;
            String startTime;
            String endTime;
            for(int i = 0; i < arr.length(); i++){
                origString = arr.get(i).toString();

                startPlace = origString.indexOf("\"startdate\":")+13;
                endPlace = origString.indexOf("\",\"", startPlace);
                startDate = origString.substring(startPlace,endPlace);

                startPlace = origString.indexOf("\"starttime\":")+13;
                endPlace = origString.indexOf("\",\"", startPlace);
                startTime = origString.substring(startPlace,endPlace);

                startPlace = origString.indexOf("\"endtime\":")+11;
                endPlace = origString.indexOf("\",\"", startPlace);
                endTime = origString.substring(startPlace,endPlace);

                startPlace = origString.indexOf("\"columns\":[")+12;
                endPlace = origString.indexOf("\"", startPlace);
                course = origString.substring(startPlace,endPlace);

                for(int j = 0; j < 3; j++){
                    startPlace = origString.indexOf("\",\"",startPlace)+3;
                }
                endPlace = origString.indexOf("\"", startPlace);
                person = origString.substring(startPlace,endPlace);

                startPlace = origString.indexOf("\",\"",startPlace)+3;
                endPlace = origString.indexOf("\"", startPlace);
                room = origString.substring(startPlace,endPlace);

                startPlace = origString.indexOf("\",\"",startPlace)+3;
                endPlace = origString.indexOf("\"", startPlace);
                text = origString.substring(startPlace,endPlace);


                this.schedule.add(new scheduleInformation(course,person,room,text,startDate,startTime,endTime));

                //System.out.println("Schedule post " + i+1 +".");
                //System.out.println(this.schedule.elementAt(this.schedule.size()-1).toString());
            }

        }catch(Exception e){
            System.out.println(e.getMessage());
        }

    }

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
