package mobilecourse.sapp;

import android.app.Activity;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;

import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class searchFragment extends Fragment implements Button.OnClickListener{

    private onMainFragmentInteractionListener fragmentChange;
    private WebView webView;
    private jsonHandler json;
    public searchFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        this.json = new jsonHandler();

        this.webView = (WebView) view.findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://se.timeedit.net/web/bth/db1/sched1/ri1Q7.html");

        this.webView.setWebViewClient(new WebViewClient(){
            public void onPageFinished(WebView view, String url){
                if(!url.equals("https://se.timeedit.net/web/bth/db1/sched1/ri1Q7.html")) {
                    try{
                        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                        StrictMode.setThreadPolicy(policy);
                        webView.destroy();
                        json.getJsonFromTimeEdit(url,new File(getContext().getFilesDir(), "save.json"));
                        fragmentChange.fragmentChange();

                    }catch (Exception e){
                        System.out.println(url+" gave result:");
                        System.out.println(e.toString());
                    }
                }
            }
        });
        System.out.println("Test");

        return view;
    }

    public void onClick(View v) {


                /*try{
                    jsonValue = json.getJsonFromTimeEdit(this.courseList.getText().toString());
                }catch(Exception e){
                    System.out.println(e.getMessage());
                    t = false;
                }


                    try{
                        File file = new File(getContext().getFilesDir(), "save.json");
                        BufferedWriter input = new BufferedWriter(new FileWriter(file));
                        input.write(jsonValue);
                        input.close();
                        fragmentChange.fragmentChange();
                    }catch(IOException i){
                        System.out.println("Couldn't write to file");
                    }*/


    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            this.fragmentChange = (onMainFragmentInteractionListener) activity;
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
