import DataStructure.*;
import Map.*;
import utility.utilityFunction;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


/**
 * input args: dim, probability, HfunctionType, UpdateStrategy
 */
public class Main {

    public static void main(String[] args) throws CloneNotSupportedException, IOException {

        String path = "D:\\data.txt";

        int dimension = Integer.parseInt(args[0]);
        int[] start = new int[]{0, 0}, goal = new int[]{dimension - 1, dimension - 1};

        /*generate map randomly with probability p as input*/
        RealWorld RW = new RealWorld(new int[]{dimension, dimension}, start, goal);
        RW.generate(Double.parseDouble(args[1]));
//        RW.printMap();

        Knowledge knowledge = new Knowledge(new int[]{dimension, dimension}, start, goal);
        /* find if it is solvable*/
        RepeatedAStar Task = new RepeatedAStar(RW, knowledge, start, goal, args[2], args[3], true);
        Task.solve();

        /* print knowledge map*/
        knowledge = Task.getKnowledge();
        //knowledge.printMap();

        if (Task.isSolvable()) {
            /*print real world map and the same one but with the path in it*/
            Path correctPath = Task.getCorrectPath();
            correctPath.buildPath(start);
            ArrayList<LocationNode> nodes = correctPath.getPath();

            System.out.println("Final Path");
            for(int i=0; i< nodes.size()-1;i++){

                nodes.get(i).getWeightMap().resetUnknown();
                nodes.get(i).getWeightMap().resetPosValue(nodes.get(i), 100);

                if(i != nodes.size()-1){
                    nodes.get(i).getWeightMap().resetPosValue(nodes.get(i+1), 200);
                }

                nodes.get(i).getWeightMap().printMap();

                //Write into a txt file
                File file = new File(path);
                FileWriter out = new FileWriter(file, true);

                for( int j=0;j<nodes.get(i).getWeightMap().getDimension()[0];j++){
                    for(int k=0;k<nodes.get(i).getWeightMap().getDimension()[0];k++){
                        out.write(nodes.get(i).getWeightMap().getValue(j, k) +" ");
                    }
                    out.write("\n");
                }

                out.close();
            }

            System.out.println(nodes.size());
            RW.insertPath(correctPath);
            RW.printMap();
            System.out.println("Finished");
        } else {
            System.out.println("Unsolvable");

        }


//        RealWorld test = new RealWorld(new int[]{5, 5}, new int[]{0, 0}, new int[]{4, 4});
//        test.generate(0.3);
//        test.printMap();

//        test.main();
    }


}
