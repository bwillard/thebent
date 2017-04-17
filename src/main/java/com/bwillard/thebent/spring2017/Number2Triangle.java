package com.bwillard.thebent.spring2017;

import com.bwillard.thebent.common.Problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * A triangle number is of the form x = n * (n +1)/2.
 *
 * Using a simple substitution:
 * ONE
 * THREE
 * SIX
 * TEN
 *
 * are all triangle numbers find their values
 */
final class Number2Triangle implements Problem {
  private final Set<Integer> triangleNumbers = new HashSet<>();

  Number2Triangle() {
    int index = 1;
    int lastTriangleNumber = 0;
    do {
      lastTriangleNumber = index * (index + 1) / 2;
      triangleNumbers.add(lastTriangleNumber);
      index++;
    } while (lastTriangleNumber < 100000);
  }

  /** Returns the numbers ONE, THREE, SIX and TEN in order*/
  private List<Integer> getNumbers() {
    for (int o = 1; o <=9; o++) {
      for (int n = 0; n <= 9; n++) {
        if (o != n) {
          for (int e = 0; e<= 9; e++) {
            if (e != o && e != n) {
              int one = o * 100 + n * 10 + e;
              if (triangleNumbers.contains(one)) {
                for (int t = 1; t <= 9; t++) {
                  if (t != o && t != n && t != e) {
                    int ten = t * 100 + e * 10 + n;
                    if (triangleNumbers.contains(ten)) {
                      for (int h = 0; h <= 9; h++) {
                        if (h != o && h != n && h != e && h != t) {
                          for (int r = 0; r <= 9; r++) {
                            if (r != o && r != n && r != e && r != t && r != h) {
                              int three = t * 10000 + h * 1000 + r * 100 + e * 10 + e;
                              if (triangleNumbers.contains(three)) {
                                for (int s = 1; s <= 9; s++) {
                                  if (s != o && s != n && s != e && s != t && s != n && s != t && s != h) {
                                    for (int i = 0; i <= 9; i++) {
                                      if (i != o && i != n && i != e && i != t && i != n && i != t && i != h && i != s) {
                                        for (int x = 0; x <= 9; x++) {
                                          if (x != o && x != n && x != e && x != t && x != n && x != t && x != h && x != s && x != i) {
                                            int six = s * 100 + i * 10 + x;
                                            if (triangleNumbers.contains(six)) {
                                              List<Integer> results = new ArrayList<>(4);
                                              results.add(one);
                                              results.add(three);
                                              results.add(six);
                                              results.add(ten);
                                              return results;
                                            }
                                          }
                                        }
                                      }
                                    }
                                  }
                                }
                              }
                            }
                          }
                        }
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
    }
    throw new IllegalStateException("Didn't find an answer");
  }

  @Override
  public String solve() {
    StringBuilder sb = new StringBuilder();

    for(Integer i : getNumbers()) {
      sb.append(i);
      sb.append("\n");
    }

    return sb.toString();
  }

  @Override
  public String getTitle() {
    return "Problem 2";
  }
}
