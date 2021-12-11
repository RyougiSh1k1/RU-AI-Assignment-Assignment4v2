import DataStructure.*;
import Map.*;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.PriorityQueue;

/*
 * Different kind of update policy should have different functions to implementation.
 *
 * ENTER: current start position, map of knowledge, (goal position)
 * */
public class AStar {

//    /*current position when A Star algorithm start*/
//    private LocationNode current;

    /*agent's knowledge of map*/
    private Knowledge knowledge;
    private int[] dimension;
    /*if node popped from priority queue, then it should be set visited*/
    private int[][] visited;

    /*a priority queue using MinHeap*/
    private PriorityQueue<LocationNode> pq = null;

    /*Heuristic functions choice*/
    private Hfunction H_f;

    /*initially start point*/
    private int[] startpoint;
    private int backtrackLen;

    public AStar(int[] startpoint, Hfunction H, int[] dimension) {

        pq = new PriorityQueue<LocationNode>(1, new Comparator<LocationNode>() {
            @Override
            public int compare(LocationNode n1, LocationNode n2) {
                if ((n2.getF_n() - n1.getF_n()) == 0) {
                    return 0;
                } else if ((n2.getF_n() - n1.getF_n()) > 0) {
                    return -1;
                } else {
                    return 1;
                }
            }
        });
        H_f = H;
        this.startpoint = startpoint;
        this.dimension = dimension;
        backtrackLen = 0;
    }

    /* DESCRIPTION
     * 1. go over A* algorithm from variable current.
     * 2. If the search start from an internal position, so we just consider a subset of the map, which is a smaller map.
     * In this map, the current point should be the most top-left point, and the goal should be the most bottom-right point.
     * 3. If find unsolvable, then go backward to the first ancestor which has more than one children [FOR QUESTION 8],
     * and go over A* algorithm again
     * 4. repeat it until we find the path or we can not find any parent any more.
     * */

    /*implemented according to A* algorithm*/
    public Path execute(LocationNode current, int[] g, Knowledge k, boolean ResetVisited, boolean backtrack) {
        if (ResetVisited) {
            visited = new int[dimension[0]][dimension[1]];
        }
        /*data structures*/
        int[] goal = g;
        LocationNode tmp = null, newone = null;
        int[] tmppoint, up, down, right, left;


        pq.clear();
        /*add start position in to priority queue*/
        pq.add(current);

        while (!pq.isEmpty() && !Arrays.equals(pq.peek().getCurrentPosition(), goal)) {
            tmp = pq.remove();
            setVisited(tmp.getCurrentPosition());
            tmppoint = tmp.getCurrentPosition();

            /*check 4 directions. if satisfied, add them into priority queue*/
            if ((tmppoint[0] - 1) >= current.getCurrentPosition()[0]) {
                up = new int[]{tmppoint[0] - 1, tmppoint[1]};
                newone = new LocationNode(tmp.getG_n() + 1, H_f.getHValue(up, goal), tmp, up);
                if (!checkVisited(newone.getCurrentPosition()) && !k.checkBlocked(up) && remove_larger_F(newone)) {
                    if (remove_larger_F(newone))
                        pq.add(newone);
                }
            }
            if ((tmppoint[0] + 1) <= goal[0]) {
                down = new int[]{tmppoint[0] + 1, tmppoint[1]};
                newone = new LocationNode(tmp.getG_n() + 1, H_f.getHValue(down, goal), tmp, down);
                if (!checkVisited(newone.getCurrentPosition()) && !k.checkBlocked(down) && remove_larger_F(newone)) {
                    pq.add(newone);
                }
            }
            if ((tmppoint[1] + 1) <= goal[1]) {
                right = new int[]{tmppoint[0], tmppoint[1] + 1};
                newone = new LocationNode(tmp.getG_n() + 1, H_f.getHValue(right, goal), tmp, right);
                if (!checkVisited(newone.getCurrentPosition()) && !k.checkBlocked(right) && remove_larger_F(newone)) {
                    pq.add(newone);
                }
            }

            if ((tmppoint[1] - 1) >= current.getCurrentPosition()[1]) {
                left = new int[]{tmppoint[0], tmppoint[1] - 1};
                newone = new LocationNode(tmp.getG_n() + 1, H_f.getHValue(left, goal), tmp, left);
                if (!checkVisited(newone.getCurrentPosition()) && !k.checkBlocked(left) && remove_larger_F(newone)) {
                    pq.add(newone);
                }
            }
        }

        /*If priority queue is empty, so from this start position, we can not find path. we should go check its ancestors for path*/
        if (pq.isEmpty()) {
            if (Arrays.equals(current.getCurrentPosition(), startpoint)) {
                /*if we already backward to the start point of the whole map, we can say that it is not solvable*/
                return null;
            } else {
                /*we should keep on search its ancestors*/
                /*这里有个问题，祖先节点还是会把搜索过的部分再搜索一遍, 这样会降低效率*/
                //for Q8
                if (backtrack) {
                    while (current.getParent() != null) {
                        /*check 4 directions. if there are more than two, then set this ancestor as start point and go into A* algorithm*/
                        current = current.getParent();
                        backtrackLen++;
                        //backtrack len++  means move back count+1
                        tmppoint = current.getCurrentPosition();
                        int count = 0;
                        if ((tmppoint[0] - 1) >= startpoint[0]) {
                            up = new int[]{tmppoint[0] - 1, tmppoint[1]};
                            if (!checkVisited(up) && !k.checkBlocked(up)) {
                                count++;
                            }
                        }
                        if ((tmppoint[0] + 1) <= goal[0]) {
                            down = new int[]{tmppoint[0] + 1, tmppoint[1]};
                            if (!checkVisited(down) && !k.checkBlocked(down)) {
                                count++;
                            }
                        }
                        if ((tmppoint[1] + 1) <= goal[1]) {
                            right = new int[]{tmppoint[0], tmppoint[1] + 1};
                            if (!checkVisited(right) && !k.checkBlocked(right)) {
                                count++;
                            }
                        }

                        if ((tmppoint[1] - 1) >= startpoint[1]) {
                            left = new int[]{tmppoint[0], tmppoint[1] - 1};
                            if (!checkVisited(left) && !k.checkBlocked(left)) {
                                count++;
                            }
                        }

                        if (count >= 2) {
                            break;
                        }
//                        System.out.println("!!!!!backtrack");

                    }
                }
                //for none Q8
                else {
                    current = current.getParent();
                    backtrackLen++;
                }
                return execute(current, goal, k, true, backtrack);
            }
        } else {
            /*if we find the solution, then build the path and return*/
            return new Path(pq.remove());
        }

    }

    public boolean remove_larger_F(LocationNode n) {
        for (Iterator<LocationNode> iterator = pq.iterator(); iterator.hasNext(); ) {
            LocationNode tmp = iterator.next();
            if (Arrays.equals(tmp.getCurrentPosition(), n.getCurrentPosition())) {
                if (n.getF_n() < tmp.getF_n()) {
                    iterator.remove();
                    return true;
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkVisited(int[] point) {
        return visited[point[0]][point[1]] == 1;
    }

    public void setVisited(int[] point) {
        visited[point[0]][point[1]] = 1;
    }

    public int getBacktrackLen() {
        return backtrackLen;
    }
}
