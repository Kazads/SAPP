package mobilecourse.sapp;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.File;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class showFragment extends Fragment {

    private onMainFragmentInteractionListener fragmentChange;
    private Vector<scheduleInformation> schedule;
    private Vector<View> scheduleGUI;
    private LinearLayout showLayout;
    private jsonHandler json;


    public showFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show, container, false);
        //this.json = new jsonHandler();
        this.showLayout = (LinearLayout) view.findViewById(R.id.show_layout);
        this.json = new jsonHandler();
        try {
            this.schedule = json.getSchedule(new File(getContext().getFilesDir(), "save.json"));
        }catch (Exception e){
            //System.out.println(e.toString());
            TextView fault = new TextView(getContext());
            fault.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            fault.setTextSize(18);
            fault.setText("Load schedule from the search tab");
            this.showLayout.addView(fault);
        }
        this.scheduleGUI = new Vector<>();
        View box;
        TextView date;
        TextView course;
        TextView time;
        TextView room;
        Vector<String> dates = new Vector<>();
        for(int i = 0; i < schedule.size(); i++){
            if(dates.indexOf(this.schedule.get(i).getDate()) == -1){
                dates.add(this.schedule.get(i).getDate());
                box = inflater.inflate(R.layout.show_date, container,false);
                date = (TextView) box.findViewById(R.id.showDate);
                date.setText(this.schedule.get(i).getDate());
                this.showLayout.addView(box);
                System.out.println("First Check");
                for(int j = 0; j < schedule.size(); j++){
                    System.out.println("Second Check");
                    if(this.schedule.get(j).getDate().equals(this.schedule.get(i).getDate())){
                        System.out.println("Third Check");
                        box = inflater.inflate(R.layout.show_post, container,false);
                        course = (TextView) box.findViewById(R.id.showCourse);
                        time = (TextView) box.findViewById(R.id.showTime);
                        room = (TextView) box.findViewById(R.id.showRoom);
                        course.setText(this.schedule.elementAt(i).getCourseSmall());
                        time.setText(this.schedule.elementAt(i).getStartTime());
                        room.setText(this.schedule.elementAt(i).getRoomSmall());
                        this.scheduleGUI.add(box);
                        this.showLayout.addView(this.scheduleGUI.elementAt(scheduleGUI.size()-1));
                        this.scheduleGUI.elementAt(this.scheduleGUI.size()-1).setOnClickListener(onClickListener);
                    }
                }
            }
        }
        for(int i = 0; i < dates.size(); i++){
            System.out.println(dates.get(i));
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


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            fragmentChange = (onMainFragmentInteractionListener) activity;
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
