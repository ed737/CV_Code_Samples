package com.example.citybuilder;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;


public class TitleFragment extends Fragment implements View.OnClickListener{

    private ImageButton newGameButton, resumeGameButton;
    private TitleFragmentListener parentActivity;

    public interface TitleFragmentListener{
        void resumeGame();
        void launchSettingFragment();
        void checkDatabase();
    }

    public TitleFragment() {
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
        View v = inflater.inflate(R.layout.fragment_title, container, false);

        parentActivity = (TitleFragmentListener)this.getActivity();

        newGameButton = (ImageButton) v.findViewById(R.id.new_game_button);
        resumeGameButton = (ImageButton) v.findViewById(R.id.resume_game_button);

        // set click listeners
        resumeGameButton.setOnClickListener(this);
        resumeGameButton.setClickable(true);
        newGameButton.setOnClickListener(this);

        parentActivity.checkDatabase();

        return v;
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        switch(id){
            case R.id.new_game_button:
                parentActivity.launchSettingFragment();
                break;

            case R.id.resume_game_button:
                parentActivity.resumeGame();
                break;
        }
    }

    public void setResumeUnClickable(){
        resumeGameButton.setClickable(false);
        resumeGameButton.setImageResource(R.color.colorTransparent);
    }
}