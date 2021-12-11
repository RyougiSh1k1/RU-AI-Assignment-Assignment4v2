package Map;

/*
 * parent class for storing map information and update
 * 0 for unblocked, 1 for blocked
 */


import DataStructure.LocationNode;

public class Map{

    protected int[] dimension = new int[2];
    public int[][] data = null;
    protected int[] start = new int[2];
    protected int[] goal = new int[2];


    public Map(int[] dim, int[] s, int[] g) {
        dimension = dim;
        data = new int[dimension[0]][dimension[1]];
        start = s;
        goal = g;
    }

    /* if blocked, return true; in unblocked, return false */
    public boolean checkBlocked(int[] point) {
        return data[point[0]][point[1]] == -10;
    }

    public void updateUnBlock(int[] pos) {
        data[pos[0]][pos[1]] = 10;//unblocked cell
    }

    public int[] getDimension() {
        return dimension;
    }

    public void printMap() {
        System.out.println("------------------");
        for (int i = 0; i < dimension[0]; i++) {
            String line = "|";
            for (int j = 0; j < dimension[1]; j++) {
                line = line + "\t" + data[i][j];
            }
            line += "|";
            System.out.println(line);
        }
        System.out.println("------------------");
    }

    public void resetUnknown(){
        for(int i=0;i<this.dimension[0];i++){
            for(int j=0;j<this.dimension[1];j++){
                if(data[i][j] != 10 && data[i][j]  != -10){
                    data[i][j]  = 1;
                }
            }
        }
    }

    public void resetPosValue(LocationNode point, int value){
       int[] pos = point.getCurrentPosition();
       data[pos[0]][pos[1]] = value;
    }

    public int getValue(int i, int j){
        return data[i][j];
    }
}
