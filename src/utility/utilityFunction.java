package utility;

import DataStructure.LocationNode;

public class utilityFunction {

    public static String comparePos(LocationNode former, LocationNode later){
        int[] formerPos = former.getCurrentPosition();
        int[] laterPos = later.getCurrentPosition();

        if(formerPos[0] == laterPos[0]-1 )return "DOWN";
        else if(formerPos[0] == laterPos[0]+1 )return "UP";
        else if(formerPos[1] == laterPos[1] - 1 ) return "RIGHT";
        else if(formerPos[1] == laterPos[1] + 1 ) return "LEFT";
        else return "NULL";
    }

}
