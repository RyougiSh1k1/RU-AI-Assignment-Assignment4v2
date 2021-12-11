package Map;

import DataStructure.LocationNode;
import DataStructure.Path;

import java.util.ArrayList;
import java.util.Random;

/*child class storing real world map information*/
public class RealWorld extends Map {

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

    /*the probability of blocked*/
    private double probability;

    public RealWorld(int[] dim, int[] s, int[] g) {
        super(dim, s, g);
    }

    public void generate(double probability) {
        this.probability = probability;
        Random rand = new Random();
        for (int i = 0; i < dimension[0]; i++) {
            for (int j = 0; j < dimension[1]; j++) {
                if ((i == start[0] && j == start[1]) || (i == goal[0] && j == goal[1])) {
                    data[i][j] = 10; // start node and goal
                    continue;
                }

                if (rand.nextDouble() <= probability) {
                    data[i][j] = -10;// blocked cell
                } else {
                    data[i][j] = 10;// unblocked cell
                }
            }
        }
    }

    public void insertPath(Path p) {
        ArrayList<LocationNode> Path_list = p.getPath();
        int[] tmp;
        for (int i = 0; i < Path_list.size(); i++) {
            tmp = Path_list.get(i).getCurrentPosition();
            data[tmp[0]][tmp[1]] = 8;
        }

    }

    public int[][] getData() {
        return data;
    }

    public void setmap(int btmap[][]) {
        data = btmap;
    }

    public double getProbability() {
        return probability;
    }
}
