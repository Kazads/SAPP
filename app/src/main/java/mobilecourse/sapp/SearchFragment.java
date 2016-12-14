package mobilecourse.sapp;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends Fragment implements Button.OnClickListener{

    private OnFragmentInteractionListener fragmentChange;
    private Button mainButton;
    public SearchFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        mainButton = (Button) view.findViewById(R.id.main_button);
        mainButton.setOnClickListener(this);
        return view;
    }

    public void onClick(View v){
        switch(v.getId()){
            case R.id.main_button:
                fragmentChange.changeFragments("show");
            break;
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
