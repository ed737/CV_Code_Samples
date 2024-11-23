package com.example.citybuilder;

import android.graphics.Bitmap;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


public class DetailsFragment extends Fragment implements View.OnClickListener {

    private TextView rowText, colText, structureText;
    private EditText nameText;
    private ImageButton takePhotoButton, updateButton;
    private MapElement me;
    private ImageView thumbnail;
    private static Bitmap bitmap;
    DetailsFragmentListener parentActivity;


    public DetailsFragment() {
        // Required empty public constructor
    }

    public interface DetailsFragmentListener{
        void takePhoto();
        GameData getGameData();
        void updateDatabase();
        void onDetailsSaveClicked();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_details, container, false);

        parentActivity = (DetailsFragmentListener) this.getActivity();
        me = new MapElement();

        // assign widget ids
        rowText= v.findViewById(R.id.row_text);
        colText = v.findViewById(R.id.col_text);
        structureText = v.findViewById(R.id.structure_type_text);
        nameText = v.findViewById(R.id.name_edit_text);
        takePhotoButton = v.findViewById(R.id.take_photo_button);
        updateButton = v.findViewById(R.id.updateButton);
        thumbnail = v.findViewById(R.id.photo_thumbnail);

        // set click listener
        takePhotoButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ROW_TEXT", String.valueOf(rowText.getText()));
        outState.putString("COL_TEXT", String.valueOf(colText.getText()));
        outState.putString("STRUCTURE_TEXT", String.valueOf(structureText.getText()));
        outState.putString("NAME_TEXT", String.valueOf(nameText.getText()));
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            rowText.setText(savedInstanceState.getString("ROW_TEXT"));
            colText.setText(savedInstanceState.getString("COL_TEXT"));
            structureText.setText(savedInstanceState.getString("STRUCTURE_TEXT"));
            nameText.setText(savedInstanceState.getString("NAME_TEXT"));
            thumbnail.setImageBitmap(bitmap);
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.updateButton:
                me.setPlayerEnteredName(String.valueOf(nameText.getText()));
                saveImage();
                parentActivity.onDetailsSaveClicked();
                break;

            case R.id.take_photo_button:

                // invoke device camera app to take photo.
                me.setPlayerEnteredName(String.valueOf(nameText.getText()));
                parentActivity.takePhoto();
                break;
        }
    }

    public void setDetailsFragment(MapElement inMapElement){
        me = inMapElement;
        String txt = "Row = " + inMapElement.getRow();
        rowText.setText(txt);
        txt = "Col = " + inMapElement.getCol();
        colText.setText(txt);
        structureText.setText(inMapElement.getStructure().getType());
        nameText.setText(inMapElement.getPlayerEnteredName());
        if(me.getImageBitmap() != null) {
            thumbnail.setImageBitmap(me.getImageBitmap());
        }
        else{
            thumbnail.setImageResource(R.drawable.ic_baseline_list_24);
        }
    }

    public void saveImage(){
        me.setImage(bitmap);
        parentActivity.getGameData().setMapElement(me, me.getRow(), me.getCol());
        parentActivity.updateDatabase();
    }

    public void setImage(Bitmap inImage){
        bitmap = inImage;
        thumbnail.setImageBitmap(inImage);
    }

}