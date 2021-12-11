package Map;

import DataStructure.LocationNode;

/*child class storing agent's knowledge so far*/
public class Knowledge extends Map{

    /* PARENT CLASS DATA STRUCTURE AND METHOD:

    protected int[] dimension = new int[2];
    protected int[][] data = null;
    protected int[] start = new int[2];
    protected int[] goal = new int[2];

    public boolean checkBlocked(int[] point) {
        return data[point[0]][point[1]] == 1;
    }

    public int[] getDimension() {
        return dimension;
    }

    public void printMap() {
        for (int i = 0; i < dimension[0]; i++) {
            String line = "|";
            for (int j = 0; j < dimension[1]; j++) {
                line = line + " " + data[i][j];
            }
            line += "|";
            System.out.println(line);
        }
    }
    */

    public Knowledge(int[] dim, int[] s, int[] g) {
        super(dim, s, g);
    }

    /*if it is blocked, then update*/
    public void updateBlock(int[] pos) {
        data[pos[0]][pos[1]] = -10;//blocked cell
    }
    public void updateUnBlock(int[] pos) {
        data[pos[0]][pos[1]] = 10;//unblocked cell
    }

    public int[][] getData(){
        return super.data;
    }

    public int[] getStart(){
        return super.start;
    }

    public int[] getGoal(){
        return super.goal;
    }

    public int[] getDimension(){
        return super.dimension;
    }

    public void CopyFromReal(RealWorld real) {
        data = real.getData();
    }

//    /*if blocked, return true; if not, return false*/
//    public int getInfo(LocationNode pos) {
//        return data[pos.getCurrentPosition()[0]][pos.getCurrentPosition()[1]];
//    }

//    public void printKnowledge() {
//        for (int i = 0; i < dimension[0]; i++) {
//            String line = "|";
//            for (int j = 0; j < dimension[1]; j++) {
//                line = line + " " + data[i][j];
//            }
//            line += "|";
//            System.out.println(line);
//        }
//    }

}
