package io.devsecoops.topsecret.decipher;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

public class LocationDecipher {

  public static float[] getLocation(float[] distances) {
    double[][] positions = new double[][]{{-500, -200}, {100f, -100f}, {500f, 100f}};
    double[] distancesInDouble = convert(distances);

    NonLinearLeastSquaresSolver solver = new NonLinearLeastSquaresSolver(new TrilaterationFunction(positions, distancesInDouble), new LevenbergMarquardtOptimizer());
    LeastSquaresOptimizer.Optimum optimum = solver.solve();
    double[] centroid = optimum.getPoint().toArray();

    return new float[]{(float) centroid[0], (float) centroid[1]};
  }

  private static double[] convert(float[] floats) {
    double[] doubles = new double[floats.length];
    for (int i = 0; i < floats.length; i++) {
      doubles[i] = floats[i];
    }
    return doubles;
  }

}
