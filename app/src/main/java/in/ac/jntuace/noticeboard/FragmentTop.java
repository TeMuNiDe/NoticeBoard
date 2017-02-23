package in.ac.jntuace.noticeboard;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class FragmentTop extends Fragment {
    public OnTopFragmentClickedListener listener;
   public TextView selectOriginTop;
   public EditText textInputTop;
    int viewToShow;


    @Override
    public void onAttach(Activity activity) {
        try {

            listener = (OnTopFragmentClickedListener) activity;

            super.onAttach(activity);
        }catch (ClassCastException e){
            Log.d("Not Implemented" ,"NOwhere");
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View fragmentView = inflater.inflate(R.layout.setup_fragment_top, container, false);
        viewToShow = getArguments().getInt("viewToShow");
        View.OnClickListener topFragmentListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onTopFragmentClickInteraction(viewToShow);
            }

        };
        switch (viewToShow){

            case 0:selectOriginTop = (TextView)fragmentView.findViewById(R.id.select_origin_top);
                selectOriginTop.setVisibility(View.VISIBLE);selectOriginTop.setOnClickListener(topFragmentListener);break;
            case 1: textInputTop = (EditText)fragmentView.findViewById(R.id.text_input_top);
                textInputTop.setVisibility(View.VISIBLE);break;
        }
        return fragmentView;
    }

    public interface OnTopFragmentClickedListener {
        void onTopFragmentClickInteraction(int command);
    }
}
