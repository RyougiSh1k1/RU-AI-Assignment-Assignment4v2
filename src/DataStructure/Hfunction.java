package DataStructure;

public class Hfunction {
    private String type = null;
    private double[] pointX, pointY;

    public Hfunction(String type) {
        this.type = type;
    }

    public double getHValue(int[] pointX, int[] pointY) {
        this.pointX = new double[]{(double) pointX[0], (double) pointX[1]};
        this.pointY = new double[]{(double) pointY[0], (double) pointY[1]};
        switch (type) {
            case "Euclidean":
                return Euclidean();
            case "Manhattan":
                return Manhattan();
            case "Chebyshev":
                return Chebyshev();
            default:
                return -1;
        }
    }

    private double Euclidean() {
        return Math.sqrt(Math.pow((pointX[0] - pointY[0]), 2) + Math.pow((pointX[1] - pointY[1]), 2));
    }

    private double Manhattan() {
        return Math.abs(pointX[0] - pointY[0]) + Math.abs(pointX[1] - pointY[1]);
    }

    private double Chebyshev() {
        return Math.max(Math.abs(pointX[0] - pointY[0]), Math.abs(pointX[1] - pointY[1]));
    }

}
