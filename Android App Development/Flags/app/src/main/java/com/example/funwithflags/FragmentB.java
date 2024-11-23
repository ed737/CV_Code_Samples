/******************************************************************
 * FragmentB, contains the recycler view for flag selection
 *****************************************************************/

package com.example.funwithflags;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FragmentB extends Fragment{

    RecyclerView recyclerView;
    CountryAdapter myAdapter;
    View view;
    FragBListener parentActivity;

    public interface FragBListener{
        void onDirectionClicked(boolean horizontal, int rowCol);
        void setRowCol(int rowCol);
        int getRowCol();
        void setHorizontal(boolean horizontal);
        boolean getHorizontal();
        GameInstance getGameInstance();
    }

    public FragmentB() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_b, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity = (FragBListener) this.getActivity();
        assert parentActivity != null;
        setVertical(parentActivity.getRowCol());

    }


    // method to set recyclerView to horizontal layout
    public void setHorizontal(int inDim){
        recyclerView = (RecyclerView) view.findViewById(R.id.fragmentB);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), inDim, GridLayoutManager.HORIZONTAL, false));
        myAdapter = new CountryAdapter(this.getActivity(), parentActivity.getGameInstance().getCountryList());
        recyclerView.setAdapter(myAdapter);

    }

    // method to set recyclerView to vertical layout
    public void setVertical(int inDim){
        recyclerView = (RecyclerView) view.findViewById(R.id.fragmentB);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), inDim, GridLayoutManager.VERTICAL, false));
        myAdapter = new CountryAdapter(this.getActivity(), parentActivity.getGameInstance().getCountryList());
        recyclerView.setAdapter(myAdapter);

    }


    public void onDirectionClicked(boolean horizontal, int rowCol){

        if(horizontal){
            setHorizontal(rowCol);
        }
        else{
            setVertical(rowCol);
        }
    }
}