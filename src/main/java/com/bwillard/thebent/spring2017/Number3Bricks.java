package com.bwillard.thebent.spring2017;

import com.bwillard.thebent.common.Problem;

import java.util.ArrayList;
import java.util.List;

/**
 * Find the set of all L, W, T, with L >= W >= T.
 *
 * such that 4 * (L + W + T) = (2 / 3) * L * W * T
 */
final class Number3Bricks implements Problem {

  List<Brick> getAcceptableBricks() {
    List<Brick> results = new ArrayList<>();
    for (int t = 1 ; t < 6; t++) {
      for (int w = t; w < 400; w++) {
        for (int l = w; l < 500; l++) {
          Brick b = new Brick(l, w, t);
          if (b.isAcceptable()) {
            results.add(b);
          }
        }
      }
    }
    return results;
  }

  @Override
  public String solve() {
    StringBuilder sb = new StringBuilder();

    for(Brick b : getAcceptableBricks()) {
      sb.append(b);
      sb.append("\n");
    }

    return sb.toString();
  }

  @Override
  public String getTitle() {
    return "Problem 3";
  }

  private static class Brick {
    private final int l;
    private final int w;
    private final int t;
    Brick (int l, int w, int t) {
      this.l = l;
      this.w = w;
      this.t = t;
    }

    int getVolume() {
      return l * w * t;
    }

    int getSumOfSides() {
      return 4 * ( l + w + t);
    }

    boolean isAcceptable () {
      return 2 * getVolume() == 3 * getSumOfSides();
    }

    @Override
    public String toString() {
      return "(" + l + ", " + w + ", " + t + ")";
    }
  }

}
