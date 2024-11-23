package com.example.citybuilder;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;


public class MapAdapter extends RecyclerView.Adapter<MapAdapter.ViewHolder>  {

    private MapElement[][] map;
    private ArrayList<MapElement> mapElementList;
    private MapAdapterListener parentActivity;

    // interface for connecting to activity
    public interface MapAdapterListener {
        Structure getCurrentStructure();
        int getOrientation();
        GameData getGameData();
        int getMapFragHeight();
        void updateMoneyText();
        void runDetailsFragment(MapElement me);
        void updateDatabase();

    }

    // map adapter constructor
    public MapAdapter(Context context, MapElement[][] inMap){
        map = inMap;
        parentActivity = (MapAdapterListener) context;
        arrayToList(inMap);
    }

    // ViewHolder constructor
    public class ViewHolder extends RecyclerView.ViewHolder{

        // widget declarations
        private ImageView structureImage, northWest, northEast, southWest, southEast;

        // View holder constructor
        public ViewHolder(@NonNull final View itemView) {
            super(itemView);

            // widget linking
            structureImage = itemView.findViewById(R.id.structure_image);
            northWest = itemView.findViewById(R.id.north_west);
            northEast = itemView.findViewById(R.id.north_east);
            southWest = itemView.findViewById(R.id.south_west);
            southEast = itemView.findViewById(R.id.south_east);

            //setup click listener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Structure myStructure = parentActivity.getCurrentStructure();
                    map = parentActivity.getGameData().getMap();
                    MapElement me = (MapElement)v.getTag();

                    if(myStructure != null){

                        if(myStructure instanceof Bulldozer && me.getStructure() instanceof Road){
                            int[][] bulldozerMask = ((Road) me.getStructure()).getMask();

                            // go through mask and multiply by negative 1
                            for(int r = 0; r < 3; r++){
                                for(int c = 0; c < 3;c++){
                                    bulldozerMask[r][c] *= -1;
                                }
                            }
                            updateBuildable(me, bulldozerMask, -1);
                            me.setStructure(null);
                            //v.setBackgroundResource(R.color.colorTransparent);
                            structureImage.setImageResource(R.color.colorTransparent);
                        }

                        else if(myStructure instanceof Bulldozer){
                            me.setStructure(null);
                            //v.setBackgroundResource(R.color.colorTransparent);
                            structureImage.setImageResource(R.color.colorTransparent);
                        }

                        else if(myStructure instanceof Road) {
                            if( parentActivity.getGameData().getCurrentMoney() > myStructure.getCost()) {
                                if(me.getStructure() == null) {
                                    int[][] mask = ((Road) myStructure).getMask();
                                    updateBuildable(me, mask, 1);
                                    me.setStructure(myStructure);
                                    structureImage.setImageResource(myStructure.getImageId());
                                    parentActivity.getGameData().updateCosts(me.getStructure().getCost());
                                    parentActivity.updateMoneyText();
                                }
                            }
                        }

                        else if(myStructure instanceof Residential || myStructure instanceof Commercial){
                            if(me.getBuildable() > 0 && parentActivity.getGameData().getCurrentMoney() > myStructure.getCost()){
                                if(me.getStructure() == null) {
                                    me.setStructure(myStructure);
                                    me.setPlayerEnteredName(me.getStructure().getType());
                                    if(me.getImageBitmap() != null){
                                        structureImage.setImageResource(R.color.colorTransparent);
                                        structureImage.setImageBitmap(me.getImageBitmap());
                                    }
                                    else {
                                        structureImage.setImageResource(myStructure.getImageId());
                                    }
                                    parentActivity.getGameData().updateCosts(me.getStructure().getCost());
                                    parentActivity.updateMoneyText();
                                }
                            }
                        }

                        else if(myStructure instanceof Detail){
                            if(me.getStructure() instanceof Residential || me.getStructure() instanceof Commercial) {
                                parentActivity.runDetailsFragment(me);

                                if(me.getImageBitmap() != null){
                                    structureImage.setImageResource(R.color.colorTransparent);
                                    structureImage.setImageBitmap(me.getImageBitmap());
                                }
                            }
                        }
                        // notify adapter to update data and update database.
                        notifyDataSetChanged();
                        parentActivity.updateDatabase();
                    }

                }
            });
        }
    }

    @NonNull
    @Override
    public MapAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.map_cell, parent, false);

        GridLayoutManager.LayoutParams lp = (GridLayoutManager.LayoutParams) v.getLayoutParams();

        // get map height
        int mapCellHeight = parentActivity.getGameData().getSettings().getMapHeight();

        // get height of Map fragment
        int fragHeight = parentActivity.getMapFragHeight();

        int cellDim = fragHeight/mapCellHeight;
        lp.height = cellDim;
        lp.width = cellDim;

        v.setLayoutParams(lp);

        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MapAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(mapElementList.get(position));

        // set the drawable

        if(mapElementList.get(position).getStructure() != null) {
            if(mapElementList.get(position).getImageBitmap() != null) {
                Bitmap bitmap = mapElementList.get(position).getImageBitmap();
                mapElementList.get(position).setArrayIndex(position);
                holder.structureImage.setImageBitmap(bitmap);
            }
            else {
                // get the int reference for the drawable
                int imgRef = mapElementList.get(position).getStructure().getImageId();
                mapElementList.get(position).setArrayIndex(position);
                holder.structureImage.setImageResource(imgRef);
            }
        }
        else{
            holder.structureImage.setImageResource(R.color.colorTransparent);
        }
    }

    @Override
    public int getItemCount() {
        return mapElementList.size();
    }

    // note array to list must be adjusted depending upon screen orientation to keep indexing correct.
    private void arrayToList(MapElement[][] map){

        mapElementList = new ArrayList<>();

        for (int ii = 1; ii < parentActivity.getGameData().getSettings().getMapWidth() + 1; ii++) {
            for (int jj = 1; jj < parentActivity.getGameData().getSettings().getMapHeight() + 1; jj++) {
                map[jj][ii].setArrayIndex(mapElementList.size());
                mapElementList.add(map[jj][ii]);
            }
        }
    }

    public void updateBuildable(MapElement me, int[][] mask, int val){

        int listIndex;

        // case for updating buildable logic in Landscape mode
        if(parentActivity.getOrientation() == Configuration.ORIENTATION_PORTRAIT) {

            // go through the adjacency matrix of this road tile and update map
            for (int cMap = me.getCol() - 1, cMask = 0; cMask < 3; cMap++, cMask++) {
                for (int rMap = me.getRow() - 1, rMask = 0; rMask < 3; rMap++, rMask++) {
                    listIndex = map[rMap][cMap].getArrayIndex();

                    // this if statement to ignore tiles in map buffer border
                    if(rMap != 0 && cMap != 0 && rMap != parentActivity.getGameData().getSettings().getMapHeight() + 2 && cMap != parentActivity.getGameData().getSettings().getMapWidth() +2) {
                        // case for road built
                        if (mask[rMask][cMask] == 1) {
                            map[rMap][cMap].updateBuildable(val);
                        }
                        // case for bulldozer
                        if (mask[rMask][cMask] == -1 && map[rMap][cMap].getBuildable() > 0) {
                            map[rMap][cMap].updateBuildable(val);
                        }
                        // update the list for adapter
                        if (map[rMap][cMap].getBuildable() != mapElementList.get(listIndex).getBuildable()) {
                            mapElementList.get(listIndex).setBuildable(map[rMap][cMap].getBuildable());
                        }
                        // if this tile just became unbuildable remove the structure and reset image
                        if (mapElementList.get(listIndex).getBuildable() == 0 && mapElementList.get(listIndex).getStructure() != null) {
                            mapElementList.get(listIndex).setStructure(null);
                        }
                    }
                }
            }
        }

        // case for updating buildable logic in Landscape mode
        if(parentActivity.getOrientation() == Configuration.ORIENTATION_LANDSCAPE) {
            // go through the adjacency matrix of this road tile and update map
            for (int rMap = me.getRow() - 1, rMask = 0; rMask < 3; rMap++, rMask++) {
                for (int cMap = me.getCol() - 1, cMask = 0; cMask < 3; cMap++, cMask++) {
                    listIndex = map[rMap][cMap].getArrayIndex();

                    // this if statement to ignore tiles in map buffer border
                    if(rMap != 0 && cMap != 0 && rMap != parentActivity.getGameData().getSettings().getMapHeight() + 2 && cMap != parentActivity.getGameData().getSettings().getMapWidth() +2) {
                        // case for road built
                        if (mask[rMask][cMask] == 1) {
                            map[rMap][cMap].updateBuildable(val);
                        }
                        // case for bulldozer
                        if (mask[rMask][cMask] == -1 && map[rMap][cMap].getBuildable() > 0) {
                            map[rMap][cMap].updateBuildable(val);
                        }
                        // update the list for adapter
                        if (map[rMap][cMap].getBuildable() != mapElementList.get(listIndex).getBuildable()) {
                            mapElementList.get(listIndex).setBuildable(map[rMap][cMap].getBuildable());
                        }
                        // if this tile just became unbuildable remove the structure and reset image
                        if (mapElementList.get(listIndex).getBuildable() == 0 && mapElementList.get(listIndex).getStructure() != null) {
                            mapElementList.get(listIndex).setStructure(null);
                        }
                    }
                }
            }
        }
    }
}
