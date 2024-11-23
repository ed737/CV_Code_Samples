package com.example.citybuilder;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

public class OptionsFragment extends Fragment implements View.OnClickListener{

    private ImageButton roadButton;
    private ImageButton residentialButton;
    private ImageButton commercialButton;
    private ImageButton bulldozerButton;
    private ImageButton detailButton;
    OptionsFragListener parentActivity;

    public interface OptionsFragListener {
        void setSelectionFrag(int inFragId);
        void setCurrentStructure(@Nullable Structure inStructure);
        Bulldozer getBulldozer();
        Detail getDetail();
    }

    public OptionsFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_options, container, false);
        parentActivity = (OptionsFragListener) this.getActivity();

        // define widgets
        roadButton = v.findViewById(R.id.roadSelectButton);
        residentialButton = v.findViewById(R.id.residentialSelectButton);
        commercialButton = v.findViewById(R.id.commercialSelectButton);
        bulldozerButton = v.findViewById(R.id.bulldozerButton);
        detailButton = v.findViewById(R.id.detailsButton);

        // set click listeners
        roadButton.setOnClickListener(this);
        residentialButton.setOnClickListener(this);
        commercialButton.setOnClickListener(this);
        bulldozerButton.setOnClickListener(this);
        detailButton.setOnClickListener(this);

        return v;
    }

    @Override
    public void onClick(View v) {

        parentActivity.setCurrentStructure(null);

        switch(v.getId()){
            case R.id.roadSelectButton:
                roadButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                residentialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                commercialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                bulldozerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                detailButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                parentActivity.setSelectionFrag(R.id.roadFragment);
                break;

            case R.id.residentialSelectButton:
                roadButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                residentialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                commercialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                bulldozerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                detailButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                parentActivity.setSelectionFrag(R.id.residentialFragment);
                break;

            case R.id.commercialSelectButton:
                roadButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                residentialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                commercialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                bulldozerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                detailButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                parentActivity.setSelectionFrag(R.id.commercialFragment);
                break;

            case R.id.bulldozerButton:
                roadButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                residentialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                commercialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                bulldozerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                detailButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                parentActivity.setCurrentStructure(parentActivity.getBulldozer());
                parentActivity.setSelectionFrag(-1);
                break;
            case R.id.detailsButton:
                roadButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                residentialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                commercialButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                bulldozerButton.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                detailButton.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                parentActivity.setCurrentStructure(parentActivity.getDetail());
                parentActivity.setSelectionFrag(-1);
                break;
        }
    }
}