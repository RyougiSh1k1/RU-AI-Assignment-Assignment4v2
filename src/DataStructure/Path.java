package DataStructure;

import java.util.ArrayList;
import java.util.Arrays;

public class Path {
    private ArrayList<LocationNode> path = null;
    private LocationNode goal = null;

    public Path(LocationNode g) {
        path = new ArrayList<LocationNode>();
        goal = g;
    }

    //go backwards to start point
    public void buildPath(int[] start) {
        while (!(goal.getParent() == null) && !Arrays.equals(goal.getCurrentPosition(), start)) {
            path.add(0, goal);
            goal = goal.getParent();
        }
        path.add(0, goal);
    }

    public ArrayList<LocationNode> getPath() {
        return path;
    }
}
