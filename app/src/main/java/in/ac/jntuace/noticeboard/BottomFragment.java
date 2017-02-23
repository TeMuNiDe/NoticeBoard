package in.ac.jntuace.noticeboard;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioGroup;
import android.widget.TextView;

public class BottomFragment extends Fragment {
  int viewToShow;

public RadioGroup departmentSelect;
public RadioGroup positionSelect;
    TextView bottomSelectOrigin;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
                View fragmentView =  inflater.inflate(R.layout.fragment_bottom, container, false);
viewToShow = getArguments().getInt("viewToShow");



        switch (viewToShow){
            case 0: bottomSelectOrigin = (TextView)fragmentView.findViewById(R.id.select_origin_bottom);bottomSelectOrigin.setVisibility(View.VISIBLE);break;
            case 2:  positionSelect = (RadioGroup)fragmentView.findViewById(R.id.choice_position);positionSelect.setVisibility(View.VISIBLE);break;
            case 1: departmentSelect = (RadioGroup)fragmentView.findViewById(R.id.choice_department);
                departmentSelect.setVisibility(View.VISIBLE);break;
        }
        return fragmentView;
    }
}

