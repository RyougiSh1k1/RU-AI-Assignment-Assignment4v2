import DataStructure.*;
import Map.*;

import java.util.ArrayList;

public class test {
    public static void main() {
//        testforP0(101, 100, 0.39);
//        testHtime(101,0.16,100);
//        testQ6Shortestpath(101, 100, 0.21,false,"Procative");
        testP8(101, 100, 0.12);
//        testbt(1, 0);
    }

    public static void testforP0(int dim, int loop, double probability) throws CloneNotSupportedException {

        for (double pro = probability; pro > 0; pro = pro - 0.01) {
            double passcount = 0;
            for (int i = 0; i < loop; i++) {
                int dimension = dim;
                int[] start = new int[]{0, 0}, goal = new int[]{dimension - 1, dimension - 1};

                /*generate map randomly with probability p as input*/
                RealWorld RW = new RealWorld(new int[]{dimension, dimension}, start, goal);
                RW.generate(pro);


                Knowledge knowledge = new Knowledge(new int[]{dimension, dimension}, start, goal);
                /* find if it is solvable*/
//                   /RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, "Chebyshev");
                RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, "Euclidean", "Procative", true);

                Task.solve();

                /* print knowledge map*/
//                knowledge = Task.getKnowledge();
                //knowledge.printMap();

                if (Task.isSolvable()) {

//                    System.out.println("Finished");
                    passcount++;
                } else {
//                    System.out.println("Unsolvable");

                }

            }
            System.out.println("p= " + pro + " dim: " + dim + " pass: " + passcount + " loop: " + loop);
            System.out.println("pass probablity: " + passcount / loop);
        }

    }

    public static void testHtime(int dim, double probability, int loop) {
        long EuclideanTime = 0;
        long ChebyshevTime = 0;
        long ManhattanTime = 0;
        int count = 0;
        for (int i = 0; i < loop; i++) {
            int dimension = dim;
            int[] start = new int[]{0, 0}, goal = new int[]{dimension - 1, dimension - 1};

            /*generate map randomly with probability p as input*/
            RealWorld RW = new RealWorld(new int[]{dimension, dimension}, start, goal);
            RW.generate(probability);
//            RW.printMap();

            //test for Euclidean
            Hfunction H_f = new Hfunction("Euclidean");
            AStar as = new AStar(start, H_f, RW.getDimension());
            Knowledge knowledge = new Knowledge(new int[]{dimension, dimension}, start, goal);
            /* find if it is solvable*/
            knowledge.CopyFromReal(RW);
            Path path = null;
            LocationNode current = new LocationNode(0, H_f.getHValue(start, goal), null, start);
            long starttime = System.nanoTime();
            path = as.execute(current, goal, knowledge, true, true);
            long endtime = System.nanoTime();
            EuclideanTime += (endtime - starttime);
            System.out.println("runtime for " + "Euclidean" + (endtime - starttime) + " ns");

            //test for Manhattan
            Hfunction H_f2 = new Hfunction("Manhattan");
            AStar as2 = new AStar(start, H_f2, RW.getDimension());
            Knowledge knowledge2 = new Knowledge(new int[]{dimension, dimension}, start, goal);
            knowledge2.CopyFromReal(RW);
            Path path2 = null;
            LocationNode current2 = new LocationNode(0, H_f2.getHValue(start, goal), null, start);
            long starttime2 = System.nanoTime();
            path2 = as2.execute(current2, goal, knowledge2, true, true);
            long endtime2 = System.nanoTime();
            ManhattanTime += (endtime2 - starttime2);
            System.out.println("runtime for " + "Manhattan" + (endtime2 - starttime2) + " ns");


            //test for Chebyshev
            Hfunction H_f3 = new Hfunction("Chebyshev");
            AStar as3 = new AStar(start, H_f3, RW.getDimension());
            Knowledge knowledge3 = new Knowledge(new int[]{dimension, dimension}, start, goal);
            knowledge3.CopyFromReal(RW);
            Path path3 = null;
            LocationNode current3 = new LocationNode(0, H_f3.getHValue(start, goal), null, start);
            long starttime3 = System.nanoTime();
            path3 = as3.execute(current3, goal, knowledge3, true, true);
            long endtime3 = System.nanoTime();
            ChebyshevTime += (endtime3 - starttime3);
            System.out.println("runtime for " + "Chebyshev" + (endtime3 - starttime3) + " ns");


            RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, "Euclidean", "Procative", true);
            Task.solve();

            /* print knowledge map*/
            knowledge = Task.getKnowledge();
            //knowledge.printMap();

            if (Task.isSolvable()) {
                /*print real world map and the same one but with the path in it*/
                Path correctPath = Task.getCorrectPath();
                correctPath.buildPath(start);

                RW.insertPath(correctPath);
//                RW.printMap();
                count++;
                System.out.println("Finished");
            } else {
                System.out.println("Unsolvable");

            }

        }
        System.out.println("Ec time" + EuclideanTime / count);
        System.out.println("ma time" + ManhattanTime / count);

        System.out.println("CH time" + ChebyshevTime / count);

    }


    public static void testQ6Shortestpath(int dim, int loop, double probability, boolean backtrack, String UpdateS) {

        for (double pro = probability; pro >= 0.17; pro = pro - 0.01) {
            int passcount = 0;
            double average = 0;
            double averageTrajectoryLength = 0;
            double averageCell = 0;
            //average len of Final /FUll
            double averageFinalDFull = 0;
            //average len of trajectory /Final
            double averageTraDFinal = 0;

            for (int i = 0; i < loop; i++) {
                double shortestPathFull = 0;
                double shortestPathFinal = 0;
                double TrajectoryLength = 0;
                double cell = 0;
                double FinalDivideFull = 0;
                double TraDivideFinal = 0;
                int dimension = dim;
                int[] start = new int[]{0, 0}, goal = new int[]{dimension - 1, dimension - 1};

                /*generate map randomly with probability p as input*/
                RealWorld RW = new RealWorld(new int[]{dimension, dimension}, start, goal);
                RW.generate(pro);
//                    RW.printMap();

                Knowledge knowledge = new Knowledge(new int[]{dimension, dimension}, start, goal);
                /* find if it is solvable*/
                RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, "Manhattan", UpdateS, backtrack);

                Hfunction H_f1 = new Hfunction("Manhattan");
                AStar as1 = new AStar(start, H_f1, RW.getDimension());
                Path path1 = null;
                LocationNode current1 = new LocationNode(0, H_f1.getHValue(start, goal), null, start);

                Hfunction H_f2 = new Hfunction("Manhattan");
                AStar as2 = new AStar(start, H_f2, RW.getDimension());
                Knowledge knowledge2 = new Knowledge(new int[]{dimension, dimension}, start, goal);
                LocationNode current2 = new LocationNode(0, H_f2.getHValue(start, goal), null, start);
                knowledge2.CopyFromReal(RW);
                Path path2 = null;

                Task.solve();
                /* print knowledge map*/
                knowledge = Task.getKnowledge();
                //knowledge.printMap();

                //path in Final
                RepeatedAStar Task1 = new RepeatedAStar(RW, knowledge, start, goal, "Manhattan", UpdateS, backtrack);
                Task1.solve();
                path1 = Task1.getCorrectPath();
//
                if (path1 != null) {
                    path1.buildPath(start);
                    shortestPathFinal = path1.getPath().size();
//                        System.out.println("shortestPathFinal " + shortestPathFinal);
                }

                //shortest path in Full
                RepeatedAStar Task2 = new RepeatedAStar(RW, knowledge2, start, goal, "Manhattan", UpdateS, backtrack);
                Task2.solve();
                path2 = Task2.getCorrectPath();
//
                if (path2 != null) {
                    path2.buildPath(start);
                    shortestPathFull = path2.getPath().size();
//                        System.out.println("shortestPathFull "+ shortestPathFull);

                }


                if (Task.isSolvable()) {
                    /*print real world map and the same one but with the path in it*/
                    Path correctPath = Task.getCorrectPath();
                    correctPath.buildPath(start);
                    //get Trajectory len

                    TrajectoryLength = correctPath.getPath().size() + 2 * Task.getBacktrackcell();
                    averageTrajectoryLength += TrajectoryLength;
//                  System.out.println("TrajectoryLength "+ TrajectoryLength);
                    //get the num of cell processed by repeat a*
                    cell = Task.getCell();
                    averageCell += cell;
//                  System.out.println("cell "+ cell);
                    TraDivideFinal = TrajectoryLength / shortestPathFinal;
                    FinalDivideFull = shortestPathFinal / shortestPathFull;
                    averageFinalDFull += FinalDivideFull;
                    averageTraDFinal += TraDivideFinal;

//                        RW.insertPath(correctPath);
//                        RW.printMap();
//                        System.out.println("Finished");
                    passcount++;
                } else {
//                    System.out.println("Unsolvable");

                }

            }
            averageCell = averageCell / passcount;
            averageTrajectoryLength = averageTrajectoryLength / passcount;
            averageFinalDFull = averageFinalDFull / passcount;
            averageTraDFinal = averageTraDFinal / passcount;
            System.out.println("*******");
            System.out.println("density " + pro);
            System.out.println("pass " + passcount + " loop " + loop);
            System.out.println("avg cell " + averageCell);
            System.out.println("avg TrajectoryLength " + averageTrajectoryLength);

            System.out.println("avg Trajectory/Final " + averageTraDFinal);
            System.out.println("avg Final/Full       " + averageFinalDFull);
        }

    }

    public static void testP8(int dim, int loop, double probability) {
        for (double pro = probability; pro >= probability; pro = pro - 0.01) {
            int passcount = 0;

            double averageTrajectoryLength = 0;
            double averageCell = 0;
            double averageTrajectoryLength2 = 0;
            double averageCell2 = 0;
            double averagebacktrack = 0;
            int BtfasterCount = 0;

            for (int i = 0; i < loop; i++) {

                double TrajectoryLength = 0;
                double cell = 0;

                double TrajectoryLength2 = 0;
                double cell2 = 0;

                int dimension = dim;
                int[] start = new int[]{0, 0}, goal = new int[]{dimension - 1, dimension - 1};

                /*generate map randomly with probability p as input*/
                RealWorld RW = new RealWorld(new int[]{dimension, dimension}, start, goal);
                RW.generate(pro);
//                    RW.printMap();

                Knowledge knowledge = new Knowledge(new int[]{dimension, dimension}, start, goal);
                Knowledge knowledge2 = new Knowledge(new int[]{dimension, dimension}, start, goal);
                /* find if it is solvable*/
                RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, "Manhattan", "Procative", true);
                RepeatedAStar Task2 = new RepeatedAStar(RW, knowledge2, start, goal, "Manhattan", "Procative", false);
                long starttime = System.nanoTime();
                Task.solve();
                long endtime = System.nanoTime();
                System.out.println("runtime for " + "   backtrack" + (endtime - starttime) + " ns");
                long starttime2 = System.nanoTime();
                Task2.solve();
                long endtime2 = System.nanoTime();
                System.out.println("runtime for " + "no backtrack" + (endtime2 - starttime2) + " ns");

                /* print knowledge map*/
                knowledge = Task.getKnowledge();
                //knowledge.printMap();


                if (Task.isSolvable()) {
                    /*print real world map and the same one but with the path in it*/
                    Path correctPath = Task.getCorrectPath();
                    correctPath.buildPath(start);
                    Path correctPath2 = Task2.getCorrectPath();
                    correctPath2.buildPath(start);
                    //get Trajectory len
//                    TrajectoryLength = correctPath.getPath().size();
                    TrajectoryLength = correctPath.getPath().size() + Task.getBacktrackcell() * 2;
                    TrajectoryLength2 = correctPath2.getPath().size() + Task2.getBacktrackcell() * 2;
                    averageTrajectoryLength += TrajectoryLength;
                    averageTrajectoryLength2 += TrajectoryLength2;
                    if ((endtime - starttime) < (endtime2 - starttime2))
                        BtfasterCount++;
                    System.out.println("TrajectoryLength with backtrack " + TrajectoryLength);
                    System.out.println("TrajectoryLength " + TrajectoryLength2);

                    System.out.println("backtrack  len: " + Task.getBacktrackcell());
                    averagebacktrack += Task.getBacktrackcell();
                    System.out.println("nobacktracklen: " + Task2.getBacktrackcell());
                    //get the num of cell processed by repeat a*
                    cell = Task.getCell();
                    averageCell += cell;
                    cell2 = Task2.getCell();
                    averageCell2 += cell2;
                    System.out.println("cell with backtrack " + cell);
                    System.out.println("cell " + cell2);


//                        RW.insertPath(correctPath);
//                        RW.printMap();
//                        System.out.println("Finished");
                    passcount++;
                } else {
                    System.out.println("Unsolvable");

                }


            }
            averageCell = averageCell / passcount;
            averageCell2 = averageCell2 / passcount;
            averageTrajectoryLength = averageTrajectoryLength / passcount;
            averageTrajectoryLength2 = averageTrajectoryLength2 / passcount;
            averagebacktrack = averagebacktrack / passcount;
            System.out.println("density " + pro);

            System.out.println("pass " + passcount + " loop " + loop);
            System.out.println("backtrack win " + BtfasterCount);
            System.out.println("avg cell with backtrack" + averageCell);
            System.out.println("avg TrajectoryLength with backtrack" + averageTrajectoryLength);
            System.out.println("avg cell " + averageCell2);
            System.out.println("avg TrajectoryLength  " + averageTrajectoryLength2);
            System.out.println("avgback " + averagebacktrack);
        }
    }

    //test for a small map to debug
    public static void testbt(int loop, double probability) {

        int passcount = 0;

        double averageTrajectoryLength = 0;
        double averageCell = 0;
        double averageTrajectoryLength2 = 0;
        double averageCell2 = 0;
        double averagebacktrack = 0;

        for (int i = 0; i < loop; i++) {

            double TrajectoryLength = 0;
            double cell = 0;

            double TrajectoryLength2 = 0;
            double cell2 = 0;

            int dimension = 5;
            int[] start = new int[]{0, 0}, goal = new int[]{dimension - 1, dimension - 1};

            /*generate map randomly with probability p as input*/
            RealWorld RW = new RealWorld(new int[]{dimension, dimension}, start, goal);
            int btmap[][] = {
                    {0, 1, 1, 1, 1},
                    {0, 0, 0, 0, 0},
                    {0, 1, 0, 1, 1},
                    {0, 1, 0, 1, 1},
                    {0, 1, 0, 0, 0},};

            RW.setmap(btmap);
//                RW.generate(pro);
//                    RW.printMap();

            Knowledge knowledge = new Knowledge(new int[]{dimension, dimension}, start, goal);
            Knowledge knowledge2 = new Knowledge(new int[]{dimension, dimension}, start, goal);
            /* find if it is solvable*/
            RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, "Manhattan", "Procative", true);
            RepeatedAStar Task2 = new RepeatedAStar(RW, knowledge2, start, goal, "Manhattan", "Procative", false);
            long starttime = System.nanoTime();
            Task.solve();
            long endtime = System.nanoTime();
            System.out.println("runtime for " + "   backtrack" + (endtime - starttime) + " ns");
            long starttime2 = System.nanoTime();
            Task2.solve();
            long endtime2 = System.nanoTime();
            System.out.println("runtime for " + "no backtrack" + (endtime2 - starttime2) + " ns");

            /* print knowledge map*/
            knowledge = Task.getKnowledge();
            //knowledge.printMap();


            if (Task.isSolvable()) {
                /*print real world map and the same one but with the path in it*/
                Path correctPath = Task.getCorrectPath();
                correctPath.buildPath(start);
                Path correctPath2 = Task2.getCorrectPath();
                correctPath2.buildPath(start);
                //get Trajectory len
                TrajectoryLength = correctPath.getPath().size();
//
                TrajectoryLength2 = correctPath2.getPath().size();
                averageTrajectoryLength += TrajectoryLength;
                averageTrajectoryLength2 += TrajectoryLength2;
                System.out.println("correctPathLength with backtrack " + TrajectoryLength);
                System.out.println("correctPathLength " + TrajectoryLength2);

                System.out.println("backtrack  len: " + Task.getBacktrackcell());
                averagebacktrack += Task.getBacktrackcell();
                System.out.println("nobacktracklen: " + Task2.getBacktrackcell());
                //get the num of cell processed by repeat a*
                cell = Task.getCell();
                averageCell += cell;
                cell2 = Task2.getCell();
                averageCell2 += cell2;
                System.out.println("cell with backtrack " + cell);
                System.out.println("cell " + cell2);


                RW.insertPath(correctPath);
                RW.printMap();
//                        System.out.println("Finished");
                passcount++;
            } else {
                System.out.println("Unsolvable");

            }


        }
        averageCell = averageCell / passcount;
        averageCell2 = averageCell2 / passcount;
        averageTrajectoryLength = averageTrajectoryLength / passcount;
        averageTrajectoryLength2 = averageTrajectoryLength2 / passcount;
//            System.out.println("density " + pro);
        System.out.println("pass " + passcount + " loop " + loop);
        System.out.println("avg cell with backtrack" + averageCell);
        System.out.println("avg TrajectoryLength with backtrack" + averageTrajectoryLength);
        System.out.println("avg cell " + averageCell2);
        System.out.println("avg TrajectoryLength  " + averageTrajectoryLength2);
        System.out.println("avgback " + averagebacktrack);
    }


}

