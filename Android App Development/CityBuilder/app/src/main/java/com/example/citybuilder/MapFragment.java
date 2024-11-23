package com.example.citybuilder;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class MapFragment extends Fragment {

    private RecyclerView recyclerView;
    private MapAdapter mapAdapter;
    private MapFragListener parentActivity;
    private int fragHeight;
    private int fragWidth;

    public interface MapFragListener{
        int getOrientation();
        GameData getGameData();
    }

    public MapFragment() {
        // Required empty public constructor
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity = (MapFragListener) this.getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_map, container, false);


        assert parentActivity != null;
        parentActivity = (MapFragListener) this.getActivity();
        recyclerView = (RecyclerView) v.findViewById(R.id.mapRecyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), parentActivity.getGameData().getSettings().getMapHeight(), GridLayoutManager.HORIZONTAL, false));
        mapAdapter = new MapAdapter(this.getActivity(), parentActivity.getGameData().getMap());
        recyclerView.setAdapter(mapAdapter);
        fragHeight = v.getHeight();
        fragWidth = v.getWidth();
        return v;
    }


}