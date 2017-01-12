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
import java.text.SimpleDateFormat;
import java.util.Date;
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
        TextView dateText;
        TextView course;
        TextView time;
        TextView room;
        Vector<String> dates = new Vector<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = sdf.format(now);
        try{
            Date today = sdf.parse(strDate);
            for(int i = 0; i < schedule.size(); i++){
                Date date = sdf.parse(this.schedule.get(i).getDate());
                if(dates.indexOf(this.schedule.get(i).getDate()) == -1 && today.compareTo(date) <= 0){
                    dates.add(this.schedule.get(i).getDate());
                    box = inflater.inflate(R.layout.show_date, container,false);
                    dateText = (TextView) box.findViewById(R.id.showDate);
                    dateText.setText(this.schedule.get(i).getDate());
                    this.showLayout.addView(box);
                    for(int j = 0; j < schedule.size(); j++){
                        if(this.schedule.get(j).getDate().equals(this.schedule.get(i).getDate())){
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
        }catch(Exception e){
            System.out.println(e.toString());
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
