package io.devsecoops.topsecret.application.service.domain;

import com.lemmingapex.trilateration.NonLinearLeastSquaresSolver;
import com.lemmingapex.trilateration.TrilaterationFunction;
import org.apache.commons.math3.fitting.leastsquares.LeastSquaresOptimizer;
import org.apache.commons.math3.fitting.leastsquares.LevenbergMarquardtOptimizer;

public class LocationDecipher {

  float[][] positions;

  public LocationDecipher(float[][] positions) {
    this.positions = positions;
  }

  public float[] getLocation(float... distances) {
    double[][] positions = convert(this.positions);
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

  private static double[][] convert(float[][] floats) {
    double[][] doubles = new double[floats.length][];
    for (int i = 0; i < floats.length; i++) {
      doubles[i] = convert(floats[i]);
    }
    return doubles;
  }
}
