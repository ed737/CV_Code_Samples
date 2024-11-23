package com.example.citybuilder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class LinearLayoutAdapter extends RecyclerView.Adapter<LinearLayoutAdapter.ViewHolder>  {

    private ArrayList<Structure> structureList;
    private LinearLayoutListener parentActivity;
    private ArrayList<LinearLayoutAdapter.ViewHolder> viewHolderList;

    // interface for connecting to activity
    public interface LinearLayoutListener {
        int getSelectionFragHeight();
        void setCurrentStructure(Structure inStructure);
    }

    // map adapter constructor
    public LinearLayoutAdapter (Context context, ArrayList<Structure> inStructureList){
        viewHolderList = new ArrayList<>();
        structureList = inStructureList;
        parentActivity = (LinearLayoutListener) context;
    }

    // countryViewHolder constructor
    public class ViewHolder extends RecyclerView.ViewHolder{

        // widget declarations
        ImageView structureImage;

        // View holder constructor
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            // widget linking
            structureImage = itemView.findViewById(R.id.mapTileImageView);

            // setup click listener for map cells
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // reset all other itemView backgrounds to color primary dark
                    for(int ii = 0; ii < viewHolderList.size(); ii++){
                        viewHolderList.get(ii).itemView.setBackgroundResource(R.color.colorPrimaryDark);
                    }

                    Structure myStructure = (Structure)v.getTag();
                    itemView.setBackgroundResource(R.color.colorPrimary);
                    parentActivity.setCurrentStructure(myStructure);
                }
            });


        }
    }
    @NonNull
    @Override
    public LinearLayoutAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_tile, parent, false);

        RecyclerView.LayoutParams lp = (RecyclerView.LayoutParams) v.getLayoutParams();

        // set height of each linear layout tile to fragment height and set width to this value also.
        int fragHeight = parentActivity.getSelectionFragHeight();
        lp.leftMargin = 4;
        lp.height = fragHeight;
        lp.width = fragHeight;

        v.setLayoutParams(lp);

        return new LinearLayoutAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LinearLayoutAdapter.ViewHolder holder, int position) {

        int imgRef;
        viewHolderList.add(holder);
        holder.itemView.setTag(structureList.get(position));

        // get the int reference for the drawable
        imgRef = structureList.get(position).getImageId();
        holder.itemView.setBackgroundResource(R.color.colorPrimaryDark);
        // set the drawable
        holder.structureImage.setImageResource(imgRef);
    }

    @Override
    public int getItemCount() {
        return structureList.size();
    }


}
