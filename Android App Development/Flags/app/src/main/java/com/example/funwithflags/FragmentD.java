/******************************************************************
 * FragmentD, contains the question card recyclerview
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

public class FragmentD extends Fragment {

    RecyclerView recyclerView;
    QuestionAdapter questionAdapter;
    View view;
    FragmentDListener parentActivity;



    public interface FragmentDListener{
    int getRowCol();
    GameInstance getGameInstance();
    }

    public FragmentD() {
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
        view = inflater.inflate(R.layout.fragment_d, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        parentActivity = (FragmentDListener) this.getActivity();
        recyclerView = (RecyclerView) view.findViewById(R.id.fragmentD);
        setVertical(parentActivity.getRowCol());

    }

    // method to set recyclerView to horizontal layout
    public void setHorizontal(int inDim){
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), inDim, GridLayoutManager.HORIZONTAL, false));
        questionAdapter = new QuestionAdapter(this.getActivity(), parentActivity.getGameInstance().getCountryList().get(parentActivity.getGameInstance().getCurrentCountryIndex()).getQuestions());
        recyclerView.setAdapter(questionAdapter);

    }

    // method to set recyclerView to vertical layout
    public void setVertical(int inDim){
        recyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(), inDim, GridLayoutManager.VERTICAL, false));
        questionAdapter = new QuestionAdapter(this.getActivity(), parentActivity.getGameInstance().getCountryList().get(parentActivity.getGameInstance().getCurrentCountryIndex()).getQuestions());
        recyclerView.setAdapter(questionAdapter);

    }

    // event handlers
    public void onDirectionClicked(boolean horizontal, int rowCol){

        if(horizontal){
            setHorizontal(rowCol);
        }
        else{
            setVertical(rowCol);
        }
    }

}