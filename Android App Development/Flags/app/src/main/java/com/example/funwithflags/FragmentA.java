/******************************************************************
 * FragmentA, contains the buttons for row and column controls
 * for fragments b and d
 *****************************************************************/

package com.example.funwithflags;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ToggleButton;



public class FragmentA extends Fragment implements View.OnClickListener {

    ToggleButton singleDim;
    ToggleButton doubleDim;
    ToggleButton tripleDim;
    Button orientationBtn;
    FragAListener parentActivity;

    // interface for returning direction click to activity
    public interface FragAListener{
        void onDirectionClicked(boolean horizontal, int rowCol);
        void setRowCol(int rowCol);
        int getRowCol();
        void setHorizontal(boolean horizontal);
        boolean getHorizontal();
    }

    public FragmentA() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_a, container, false);

        // assign parent activity for interface
        parentActivity = (FragAListener) this.getActivity();

        // identify buttons
        singleDim = (ToggleButton) view.findViewById(R.id.singleDim);
        doubleDim = (ToggleButton) view.findViewById(R.id.doubleDim);
        tripleDim = (ToggleButton) view.findViewById(R.id.tripleDim);
        orientationBtn = (Button) view.findViewById(R.id.orientationBtn);

        // set onClickListeners
        singleDim.setOnClickListener(this);
        doubleDim.setOnClickListener(this);
        tripleDim.setOnClickListener(this);
        orientationBtn.setOnClickListener(this);

        // restore the instance data on screen rotation
        if(savedInstanceState != null){

        }

        return view;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public void onClick(View v) {

        // switch based on button id
        switch(v.getId()){

            // singleDim btn pressed
            case(R.id.singleDim) :

                // toggle button logic for single dim
                tripleDim.setChecked(false);
                tripleDim.setBackgroundResource(R.drawable.ic_baseline_looks_3_24);
                singleDim.setChecked(true);
                singleDim.setBackgroundResource(R.drawable.ic_baseline_looks_one_24_clicked);
                doubleDim.setChecked(false);
                doubleDim.setBackgroundResource(R.drawable.ic_baseline_looks_two_24);
                parentActivity.setRowCol(1);
            break;

            // doubleDim btn pressed
            case(R.id.doubleDim) :

                // toggle button logic for double dim
                tripleDim.setChecked(false);
                tripleDim.setBackgroundResource(R.drawable.ic_baseline_looks_3_24);
                singleDim.setChecked(false);
                singleDim.setBackgroundResource(R.drawable.ic_baseline_looks_one_24);
                doubleDim.setChecked(true);
                doubleDim.setBackgroundResource(R.drawable.ic_baseline_looks_two_24_clicked);
                parentActivity.setRowCol(2);
            break;

            // tripleDim btn pressed
            case(R.id.tripleDim) :

                // toggle button logic for triple dim
                tripleDim.setChecked(true);
                tripleDim.setBackgroundResource(R.drawable.ic_baseline_looks_3_24_clicked);
                singleDim.setChecked(false);
                singleDim.setBackgroundResource(R.drawable.ic_baseline_looks_one_24);
                doubleDim.setChecked(false);
                doubleDim.setBackgroundResource(R.drawable.ic_baseline_looks_two_24);
                parentActivity.setRowCol(3);
            break;

            // orientation btn pressed
            case(R.id.orientationBtn) :
                // flip direction button
                if(parentActivity.getHorizontal()) {
                    orientationBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_downward_24);
                }
                else{
                    orientationBtn.setBackgroundResource(R.drawable.ic_baseline_arrow_forward_24);
                }

                // pass orientation through interface
                parentActivity.onDirectionClicked(parentActivity.getHorizontal(), parentActivity.getRowCol());
                parentActivity.setHorizontal(!parentActivity.getHorizontal()); // flip flag
            break;
        }

    }

}