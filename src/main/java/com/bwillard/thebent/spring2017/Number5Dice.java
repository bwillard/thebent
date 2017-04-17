package com.bwillard.thebent.spring2017;

import com.bwillard.thebent.common.Problem;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A standard die is tossed until the total of the numbers thrown exceeds 12,
 * what is the most likely final total?
 */
final class Number5Dice implements Problem {
  private static Random RANDOM = new Random();

  private int byRandom() {
    List<Integer> count = new ArrayList<>(20);
    for (int i = 0; i < 20; i++) {
      count.add(0);
    }

    for (int i =0; i< 100000; i++) {
      int sum = 0;
      while (sum <= 12) {
        sum += rollDie();
      }
      count.set(sum, count.get(sum) + 1);
    }

    int max = 0;
    int maxIndex = 0;

    for (int i = 0; i< count.size(); i ++) {
      //System.out.println(i + " = " + count.get(i));
      if (count.get(i) > max) {
        max = count.get(i);
        maxIndex = i;
      }
    }
    return maxIndex;
  }

  private static int rollDie() {
    return RANDOM.nextInt(6) + 1;
  }

  @Override
  public String solve() {
    return Integer.toString(byRandom());
  }

  @Override
  public String getTitle() {
    return "Problem 5";
  }
}
