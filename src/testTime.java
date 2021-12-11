//import DataStructure.Hfunction;
//import DataStructure.LocationNode;
//import DataStructure.Path;
//import Map.Knowledge;
//import Map.RealWorld;
//
//
//import DataStructure.*;
//import Map.*;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//
//
///**
// * input args: dim, probability
// */
//public class testTime {
//    private static Knowledge knowledge;
//    private static int loop_count = 100;
//    private static Hfunction hf_euclidean = new Hfunction("Euclidean");
//    private static Hfunction hf_manhattan = new Hfunction("Manhattan");
//    private static Hfunction hf_chebyshev = new Hfunction("Chebyshev");
//    private static Path path_euclidean = null;
//    private static Path path_manhattan = null;
//    private static Path path_chebyshev = null;
//    private static LocationNode current_euclidean, current_manhattan, current_chebyshev;
//    private static AStar as_euclidean, as_manhattan, as_chebyshev;
//    private static long start_euclidean, end_euclidean, total_euclidean = 0,
//            start_manhattan, end_manhattan, total_manhattan = 0,
//            start_chebyshev, end_chebyshev, total_chebyshev = 0,
//            t_euclidean, t_manhattan, t_chebyshev,
//            average_euclidean, average_manhattan, average_chebyshev,
//            smallest;
//    private static int count_euclidean = 0, count_manhattan = 0, count_chebyshev = 0;
//
//    public static void main(String[] args) {
//
//        int dimension = Integer.parseInt(args[0]);
//        int[] start = new int[]{0, 0}, goal = new int[]{dimension - 1, dimension - 1};
//        boolean backtrack = Boolean.parseBoolean(args[4]);
//        /*generate map randomly with probability p as input*/
//        RealWorld RW = new RealWorld(new int[]{dimension, dimension}, start, goal);
//        for (int i = 0; i < loop_count; i++) {
//            //Show the counter
//            System.out.println(i);
//
//            RW.generate(Double.parseDouble(args[1]));
//            //        RW.printMap();
//
//            knowledge = new Knowledge(new int[]{dimension, dimension}, start, goal);
//            /* find if it is solvable*/
//            //        RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, "Euclidean", "Procative");
//            RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, args[2], args[3], backtrack);
//            Task.solve();
//
//            /* print knowledge map*/
//            /*knowledge = Task.getKnowledge();
//            knowledge.printMap();*/
//
//            if (Task.isSolvable()) {
//                /*print real world map and the same one but with the path in it*/
//                /*Path correctPath = Task.getCorrectPath();
//                correctPath.buildPath(start);
//
//                RW.insertPath(correctPath);
//                RW.printMap();*/
//                knowledge.CopyFromReal(RW);
//
//                as_euclidean = new AStar(start, hf_euclidean, RW.getDimension());
//                current_euclidean = new LocationNode(0, hf_euclidean.getHValue(start, goal), null, start);
//
//                start_euclidean = System.nanoTime();
//                path_euclidean = as_euclidean.execute(current_euclidean, goal, knowledge, true, backtrack);
//                end_euclidean = System.nanoTime();
//
//                t_euclidean = (end_euclidean - start_euclidean);
//                total_euclidean += t_euclidean;
//                /*********************************************/
//
//                as_manhattan = new AStar(start, hf_manhattan, RW.getDimension());
//                current_manhattan = new LocationNode(0, hf_manhattan.getHValue(start, goal), null, start);
//
//                start_manhattan = System.nanoTime();
//                path_manhattan = as_manhattan.execute(current_manhattan, goal, knowledge, true, backtrack);
//                end_manhattan = System.nanoTime();
//
//                t_manhattan = (end_manhattan - start_manhattan);
//                total_manhattan += t_manhattan;
//                /*********************************************/
//
//                as_chebyshev = new AStar(start, hf_chebyshev, RW.getDimension());
//                current_chebyshev = new LocationNode(0, hf_chebyshev.getHValue(start, goal), null, start);
//
//                start_chebyshev = System.nanoTime();
//                path_chebyshev = as_chebyshev.execute(current_chebyshev, goal, knowledge, true, backtrack);
//                end_chebyshev = System.nanoTime();
//
//                t_chebyshev = (end_chebyshev - start_chebyshev);
//                total_chebyshev += t_chebyshev;
//                /*********************************************/
//
//                smallest = t_euclidean < t_manhattan ?
//                        (t_euclidean < t_chebyshev ? t_euclidean : t_chebyshev) : (t_manhattan < t_chebyshev ? t_manhattan : t_euclidean);
//
//                if (smallest == t_euclidean) {
//                    count_euclidean++;
//                    average_euclidean += t_euclidean;
//                }
//                if (smallest == t_manhattan) {
//                    count_manhattan++;
//                    average_manhattan += t_manhattan;
//                }
//                if (smallest == t_chebyshev) {
//                    count_chebyshev++;
//                    average_chebyshev += t_chebyshev;
//                }
//
////                System.out.println("Finished");
//            } else {
////                System.out.println("Unsolvable");
//
//            }
//        }
//        if (count_euclidean != 0) average_euclidean = average_euclidean / count_euclidean;
//        if (count_manhattan != 0) average_manhattan = average_manhattan / count_manhattan;
//        if (count_chebyshev != 0) average_chebyshev = average_chebyshev / count_chebyshev;
//
//        System.out.println(
//                "Euclidean count: " + count_euclidean + "\t" + " Total time: " + total_euclidean + " Average time for best option: " + average_euclidean + "\n"
//                        + "Manhattan count: " + count_manhattan + "\t" + " Total time: " + total_manhattan + " Average time for best option: " + average_manhattan + "\n"
//                        + "Chebyshev count: " + count_chebyshev + "\t" + " Total time: " + total_chebyshev + " Average time for best option: " + average_chebyshev + "\n");
//
////        RealWorld test = new RealWorld(new int[]{5, 5}, new int[]{0, 0}, new int[]{4, 4});
////        test.generate(0.3);
////        test.printMap();
//    }
//}
