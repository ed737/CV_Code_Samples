package com.example.citybuilder;

import java.util.ArrayList;

public class StructureData {

    private ArrayList<Residential> residentialList;
    private ArrayList<Commercial> commercialList;
    private ArrayList<Road> roadList;
    StructureData currentStructures;

    public StructureData(ArrayList<Residential> residentialList, ArrayList<Commercial> commercialList, ArrayList<Road> roadList, StructureData currentStructures) {
        this.residentialList = residentialList;
        this.commercialList = commercialList;
        this.roadList = roadList;
        this.currentStructures = currentStructures;
    }

    public ArrayList<Residential> getResidentialList() {
        return residentialList;
    }

    public void setResidentialList(ArrayList<Residential> residentialList) {
        this.residentialList = residentialList;
    }

    public ArrayList<Commercial> getCommercialList() {
        return commercialList;
    }

    public void setCommercialList(ArrayList<Commercial> commercialList) {
        this.commercialList = commercialList;
    }

    public ArrayList<Road> getRoadList() {
        return roadList;
    }

    public void setRoadList(ArrayList<Road> roadList) {
        this.roadList = roadList;
    }

    public StructureData getCurrentStructures() {
        return currentStructures;
    }

    public void setCurrentStructures(StructureData currentStructures) {
        this.currentStructures = currentStructures;
    }
}
