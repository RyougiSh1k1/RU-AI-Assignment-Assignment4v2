package DataStructure;

import Map.Knowledge;

public class LocationNode {
    private double g_n = Integer.MAX_VALUE, h_n = 0;
    private double f_n = 0;
    private LocationNode parent = null;
    private int[] currentPosition = new int[2];
    private Knowledge weightMap = null;

    public LocationNode(double g_n, double h_n, LocationNode parent, int[] current) {
        this.g_n = g_n;
        this.h_n = h_n;
        this.parent = parent;
        this.currentPosition = current;
        this.f_n = g_n + h_n;
    }

    public void setWeightMap(Knowledge map){
        weightMap = map;
    }

    public Knowledge getWeightMap(){
        return weightMap;
    }
    public double getH_n() {
        return h_n;
    }

    public double getG_n() {
        return g_n;
    }

    public double getF_n() {
        return f_n;
    }

    public LocationNode getParent() {
        return parent;
    }

    public int[] getCurrentPosition() {
        return currentPosition;
    }


}
