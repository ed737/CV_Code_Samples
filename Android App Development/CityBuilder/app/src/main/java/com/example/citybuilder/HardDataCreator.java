/***************************************
 * Hard data creator, creates
 * data that doesn't need to be stored.
 ***************************************/


package com.example.citybuilder;

import java.util.ArrayList;

public class HardDataCreator {

    private ArrayList<Structure> roadList;
    private ArrayList<Structure> residentialList;
    private ArrayList<Structure> commercialList;
    private Bulldozer bulldozer;
    private Detail detail;

    public HardDataCreator(Settings settings){

        // retrieve all costs of structures from settings.
        int roadCost = settings.getRoadCost();
        int residentialCost = settings.getHouseCost();
        int commercialCost = settings.getCommercialCost();
        bulldozer = new Bulldozer(0, 0, null);
        detail = new Detail(0,0,"Detail");
        // create road list for road linear layout in road fragment
        // masks used to set and remove buildable in map
        roadList = new ArrayList<>();

        int[][] mask = getRoadMask(R.drawable.ic_road_e);
        Road myRoad = new Road(R.drawable.ic_road_e, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_ew);
        myRoad = new Road(R.drawable.ic_road_ew, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_n);
        myRoad = new Road(R.drawable.ic_road_n, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_ne);
        myRoad = new Road(R.drawable.ic_road_ne, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_new);
        myRoad = new Road(R.drawable.ic_road_new, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_ns);
        myRoad = new Road(R.drawable.ic_road_ns, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_nse);
        myRoad = new Road(R.drawable.ic_road_nse, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_nsew);
        myRoad = new Road(R.drawable.ic_road_nsew, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_nsw);
        myRoad = new Road(R.drawable.ic_road_nsw, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_nw);
        myRoad = new Road(R.drawable.ic_road_nw, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_s);
        myRoad = new Road(R.drawable.ic_road_s, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_se);
        myRoad = new Road(R.drawable.ic_road_se, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_sew);
        myRoad = new Road(R.drawable.ic_road_sew, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_sw);
        myRoad = new Road(R.drawable.ic_road_sw, roadCost, mask);
        roadList.add(myRoad);

        mask = getRoadMask(R.drawable.ic_road_w);
        myRoad = new Road(R.drawable.ic_road_w, roadCost, mask);
        roadList.add(myRoad);


        // create residential list for residential linear layout
        residentialList = new ArrayList<>();

        Residential myResidential = new Residential(R.drawable.ic_building1, residentialCost);
        residentialList.add(myResidential);
        myResidential = new Residential(R.drawable.ic_building2, residentialCost);
        residentialList.add(myResidential);
        myResidential = new Residential(R.drawable.ic_building3, residentialCost);
        residentialList.add(myResidential);
        myResidential = new Residential(R.drawable.ic_building4, residentialCost);
        residentialList.add(myResidential);

        // create commercial list for commercial linear layout
        commercialList = new ArrayList<>();

        Commercial myCommercial = new Commercial(R.drawable.ic_building5, commercialCost);
        commercialList.add(myCommercial);
        myCommercial = new Commercial(R.drawable.ic_building6, commercialCost);
        commercialList.add(myCommercial);
        myCommercial = new Commercial(R.drawable.ic_building7, commercialCost);
        commercialList.add(myCommercial);
        myCommercial = new Commercial(R.drawable.ic_building8, commercialCost);
        commercialList.add(myCommercial);

    }


    /****************************
     * Getters and Setters
     ***************************/

    public void setBulldozer(Bulldozer bulldozer) {
        this.bulldozer = bulldozer;
    }

    public Detail getDetail() {
        return detail;
    }

    public void setDetail(Detail detail) {
        this.detail = detail;
    }
    public Bulldozer getBulldozer(){return this.bulldozer;}
    public ArrayList<Structure> getRoadList() {
        return roadList;
    }

    public void setRoadList(ArrayList<Structure> roadList) {
        this.roadList = roadList;
    }

    public ArrayList<Structure> getResidentialList() {
        return residentialList;
    }

    public void setResidentialList(ArrayList<Structure> residentialList) {
        this.residentialList = residentialList;
    }

    public ArrayList<Structure> getCommercialList() {
        return commercialList;
    }

    public void setCommercialList(ArrayList<Structure> commercialList) {
        this.commercialList = commercialList;
    }

    public int[][] getRoadMask(int inDrawableRef){
        int [][] mask;

            switch(inDrawableRef){
                case R.drawable.ic_road_e:
                    mask = new int[][]{
                            {0, 1, 0},
                            {1, 1, 0},
                            {0, 1, 0}};
                    break;

                case R.drawable.ic_road_ew:
                    mask = new int[][]{
                            {0, 1, 0},
                            {0, 1, 0},
                            {0, 1, 0}};
                    break;
                case R.drawable.ic_road_n:
                    mask = new int[][]{
                            {0, 0, 0},
                            {1, 1, 1},
                            {0, 1, 0}};
                    break;
                case R.drawable.ic_road_ne:
                    mask = new int[][]{
                            {0, 0, 0},
                            {1, 1, 0},
                            {0, 1, 0}};
                    break;
                case R.drawable.ic_road_new:
                    mask = new int[][]{
                            {0, 0, 0},
                            {0, 1, 0},
                            {0, 1, 0}};
                    break;
                case R.drawable.ic_road_ns:
                    mask = new int[][]{
                            {0, 0, 0},
                            {1, 1, 1},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_nse:
                    mask = new int[][]{
                            {0, 0, 0},
                            {1, 1, 0},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_nsew:
                    mask = new int[][]{
                            {0, 0, 0},
                            {0, 1, 0},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_nsw:
                    mask = new int[][]{
                            {0, 0, 0},
                            {0, 1, 1},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_nw:
                    mask = new int[][]{
                            {0, 0, 0},
                            {0, 1, 1},
                            {0, 1, 0}};
                    break;
                case R.drawable.ic_road_s:
                    mask = new int[][]{
                            {0, 1, 0},
                            {1, 1, 1},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_se:
                    mask = new int[][]{
                            {0, 1, 0},
                            {1, 1, 0},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_sew:
                    mask = new int[][]{
                            {0, 1, 0},
                            {0, 1, 0},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_sw:
                    mask = new int[][]{
                            {0, 1, 0},
                            {0, 1, 1},
                            {0, 0, 0}};
                    break;
                case R.drawable.ic_road_w:
                    mask = new int[][]{
                            {0, 1, 0},
                            {0, 1, 1},
                            {0, 1, 0}};
                    break;
                default:
                    mask = null;
                    break;
            }

        return mask;
    }

}
