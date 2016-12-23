package mobilecourse.sapp;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements Button.OnClickListener{

    private OnFragmentInteractionListener fragmentChange;
    private EditText newCourses;
    private Button addNewCourses;
    private TextView courseList;
    private LinearLayout removeButtonLayout;
    private Vector<Button> removeButtons;
    private TextView faultMessage;
    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        this.newCourses = (EditText) view.findViewById(R.id.newCourses);
        this.addNewCourses = (Button) view.findViewById(R.id.addNewCourses);
        this.courseList = (TextView) view.findViewById(R.id.courseList);
        this.removeButtonLayout = (LinearLayout) view.findViewById(R.id.removeButtonLayout);
        this.removeButtons = new Vector<>();
        this.faultMessage = (TextView) view.findViewById(R.id.faultMessage);
        this.addNewCourses.setOnClickListener(this);
        return view;
    }

    public void onClick(View v) {

        if (v == this.addNewCourses) {
            String input = newCourses.getText().toString();
            if (!input.equals("")) {
                String alreadyInList = courseList.getText().toString();
                if (alreadyInList == "") {
                    this.courseList.setText(input);
                } else {
                    this.courseList.setText(alreadyInList + "\n" + input);
                }
                this.removeButtons.add(new Button(getContext(),null,R.attr.buttonStyle));
                this.removeButtons.get(this.removeButtons.size()-1).setTextColor(Color.parseColor("#FF0000"));
                this.removeButtons.get(this.removeButtons.size()-1).setText("X");
                this.removeButtons.get(this.removeButtons.size()-1).setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
                this.removeButtons.get(this.removeButtons.size()-1).setHeight(10);
                this.removeButtonLayout.addView(this.removeButtons.get(this.removeButtons.size()-1));
                this.removeButtons.elementAt(this.removeButtons.size()-1).setOnClickListener(this);
                this.newCourses.setText("");
            }

        }

        for(int i = 0; i < this.removeButtons.size(); i++){
            if(v == this.removeButtons.elementAt(i)){
                String list = this.courseList.getText().toString();
                String[] listArray = list.split("\n");
                listArray[i] = "";
                list = "";
                for(int j = 0; j < this.removeButtons.size(); j++){
                    list += listArray[j] + " ";
                }
                this.courseList.setText(list);

                this.removeButtonLayout.removeView(this.removeButtons.elementAt(i));
                this.removeButtons.remove(i);
            }
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.fragmentChange = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.fragmentChange = null;

    }
}
