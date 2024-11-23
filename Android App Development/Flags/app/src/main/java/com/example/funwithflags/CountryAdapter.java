package com.example.funwithflags;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class CountryAdapter extends RecyclerView.Adapter<CountryAdapter.ViewHolder> {

    private ArrayList<Country> countryList;
    CountryAdapterListener parentActivity;

    // interface for connecting to activity
    public interface CountryAdapterListener {
        void onCountryCardClicked(int index);
        int getRowCol();
        boolean getHorizontal();
        int getOrientation();
    }

    // country adapter constructor
    public CountryAdapter(Context context, ArrayList<Country> inCountryList){
        countryList = inCountryList;
        parentActivity = (CountryAdapterListener) context;
    }

    // countryViewHolder constructor
    public class ViewHolder extends RecyclerView.ViewHolder{

        // widget declarations
        ImageView countryFlagImage;

        // View holder constructor
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            // widget linking
            countryFlagImage = itemView.findViewById(R.id.country_flag_image);

            // setup click listener for cards
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    parentActivity.onCountryCardClicked(countryList.indexOf((Country)itemView.getTag()));
                }
            });


        }
    }



    @NonNull
    @Override
    public CountryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_card_view, parent, false);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) view.getLayoutParams();
        // This if statement creates the viewholder with a 4:3 aspect ratio. Must use static
        // variables from Flag choice activity.
        // Frag A and B set to 50 dp height therefore - 100 - 20 for each side with card padding

        // get the screen orientation.
        int screenOrientation = parentActivity.getOrientation();


        if(screenOrientation == Configuration.ORIENTATION_PORTRAIT) {
            if (parentActivity.getHorizontal() || parentActivity.getRowCol() == 1) {
                lp.width = ((parent.getMeasuredWidth()) / parentActivity.getRowCol());
                lp.height = (int) (lp.width * 0.75);
            } else {
                lp.height = ((parent.getMeasuredHeight()) / parentActivity.getRowCol());
                lp.width = (int) (lp.height * 1.3); // 1.3 is scaler for 4:3 in this dimension
            }
        }
        else{
            lp.height = ((parent.getMeasuredHeight()) / parentActivity.getRowCol());
            lp.width = (int) (lp.height * 1.3); // 1.3 is scaler for 4:3 in this dimension
        }
            lp.setMargins(15, 15, 15, 15);

        view.setLayoutParams(lp);
        return new ViewHolder(view);
    }



    // each element in countryList passes through this method to create cards
    @Override
    public void onBindViewHolder(@NonNull CountryAdapter.ViewHolder holder, int position) {
        int flagRef;
        holder.itemView.setTag(countryList.get(position));

        // get the int reference for the drawable
        flagRef = countryList.get(position).getFlagImage();

        // set the drawable
        holder.countryFlagImage.setBackgroundResource(flagRef);
    }

    // get size of country list
    @Override
    public int getItemCount() {
        return countryList.size();
    }
}
