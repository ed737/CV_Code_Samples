package com.example.citybuilder;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;


public class ResidentialFragment extends Fragment {

    RecyclerView recyclerView;
    ResidentialFragListener parentActivity;
    LinearLayoutAdapter myAdapter;

    public interface ResidentialFragListener {
        ArrayList<Structure> getResidentialList();
    }

    public ResidentialFragment() {
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
        View v  = inflater.inflate(R.layout.fragment_residential, container, false);

        assert parentActivity != null;
        parentActivity = (ResidentialFragment.ResidentialFragListener) this.getActivity();
        recyclerView = (RecyclerView) v.findViewById(R.id.residentialFragment);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity(),  LinearLayoutManager.HORIZONTAL, false));
        myAdapter = new LinearLayoutAdapter(this.getActivity(), parentActivity.getResidentialList());
        recyclerView.setAdapter(myAdapter);

        return v;

    }
}