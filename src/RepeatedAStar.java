import DataStructure.*;
import Map.*;

import java.util.ArrayList;
import java.util.Arrays;

public class RepeatedAStar {

    private int[] mapSize = new int[2];

    /* real stands for the entire information of map, info stands for the agent's knowledge */
    private RealWorld real = null;
    private Knowledge knowledge = null;
    private Knowledge tempKnowledge = null;
    private int[] start = null, goal = null;
    private String updateStrategy;
    /*Heuristic functions choice*/
    private Hfunction H_f;

    /*data structures to save results*/
    private boolean solvable;
    private Path CorrectPath = null;
    private int cell;
    private boolean backtrack;
    private int backtrackcell;
    public RepeatedAStar(RealWorld m, Knowledge k, int[] s, int[] g, String HFunctionType, String updateStrategy, boolean backtrack) {
        real = m;
        knowledge = k;
        start = s;
        goal = g;
        H_f = new Hfunction(HFunctionType);
//        fringe = queue;
        solvable = false;
        this.updateStrategy = updateStrategy;
        this.backtrack = backtrack;
        cell = 0;
        backtrackcell = 0;
    }

    public void  solve(){
        LocationNode current = new LocationNode(0, H_f.getHValue(start, goal), null, start);
        Path path = null;
        AStar as = new AStar(start, H_f, real.getDimension());
        while (!Arrays.equals(current.getCurrentPosition(), goal)) {
            /*execute A* algorithm */
            path = as.execute(current, goal, knowledge, true, backtrack);

            /*if can not find a path*/
            if (path == null) {
                solvable = false;
                CorrectPath = null;
                return;
            }

            /*go over the path*/
            path.buildPath(current.getCurrentPosition());
            ArrayList<LocationNode> p = path.getPath();


            current = UpdateMap(updateStrategy, p, current);

            /*if the agent has reach the goal, "current" is actually the "goal", then finished this "while";
            If the agent met a block, then go into another round */
            if (p.isEmpty()) {
                solvable = true;
                CorrectPath = new Path(current);
            }
        }
        backtrackcell = as.getBacktrackLen();
    }

    private LocationNode UpdateMap(String updateStrategy, ArrayList<LocationNode> p, LocationNode current){
        switch (updateStrategy) {
            case "Passive":
                return PasUpdate(p, current);
            default:/*"Proactive" b defult*/
                return ProUpdate(p, current);
        }
    }

    private LocationNode PasUpdate(ArrayList<LocationNode> p, LocationNode current) {
        /*check if obstacle exist, and update map in the trip*/
        while (!p.isEmpty() && !real.checkBlocked(p.get(0).getCurrentPosition())) {
            /*if while break because p is empty, then the outer while will also break. Because current is at the goal point*/
            current = p.remove(0);
            cell++;
            /*while going along the path, we should update environment as well*/
        }

        if (!p.isEmpty()) {
            UpdatePoint(p.get(0).getCurrentPosition());
        }
        return current;
    }

    private LocationNode ProUpdate(ArrayList<LocationNode> p, LocationNode current){
        /*check if obstacle exist, and update map in the trip*/
        while (!p.isEmpty() && !real.checkBlocked(p.get(0).getCurrentPosition())) {
            /*if while break because p is empty, then the outer while will also break. Because current is at the goal point*/
            current = p.remove(0);
            cell++;
            /*while going along the path, we should update environment as well*/
            UpdatePoint(current.getCurrentPosition());

            tempKnowledge = new Knowledge(knowledge.getDimension(), knowledge.getStart(), knowledge.getGoal());
            for(int i=0;  i<knowledge.getDimension()[0];i++){
                for(int j=0;  j<knowledge.getDimension()[1];j++){
                    tempKnowledge.data[i][j] = knowledge.getData()[i][j];
                }
            }

            current.setWeightMap(tempKnowledge);//send the current knowledge to the current node to store
        }
        return current;
    }

    /*update proactively*/
    private void UpdatePoint(int[] point) {
        int[] up, down, right, left;

        /*fro passive update*/
        if (real.checkBlocked(point)) {
            knowledge.updateBlock(point);
        } else {
            /*for proactive update*/
            /*check 4 directions. update the block or not information*/
            if ((point[0] - 1) >= start[0]) {
                up = new int[]{point[0] - 1, point[1]};
                if (real.checkBlocked(up)) {
                    knowledge.updateBlock(up);
                }
                else{
                    knowledge.updateUnBlock(up);
                }
            }
            if ((point[0] + 1) <= goal[0]) {
                down = new int[]{point[0] + 1, point[1]};
                if (real.checkBlocked(down)) {
                    knowledge.updateBlock(down);
                }
                else{
                    knowledge.updateUnBlock(down);
                }
            }
            if ((point[1] + 1) <= goal[1]) {
                right = new int[]{point[0], point[1] + 1};
                if (real.checkBlocked(right)) {
                    knowledge.updateBlock(right);
                }
                else{
                    knowledge.updateUnBlock(right);
                }
            }

            if ((point[1] - 1) >= start[1]) {
                left = new int[]{point[0], point[1] - 1};
                if (real.checkBlocked(left)) {
                    knowledge.updateBlock(left);
                }
                else{
                    knowledge.updateUnBlock(left);
                }
            }
        }
    }

    public Path getCorrectPath() {
        if (solvable) {
            return CorrectPath;
        } else {
            return null;
        }

    }

    public boolean isSolvable() {
        return solvable;
    }

    public Knowledge getKnowledge() {
        return knowledge;
    }

    public int getCell() {
        return cell;
    }

    public int getBacktrackcell() {
        return backtrackcell;
    }
}
