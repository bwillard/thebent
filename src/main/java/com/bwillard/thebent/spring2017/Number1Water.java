package com.bwillard.thebent.spring2017;

import com.bwillard.thebent.common.Problem;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Problem:
 * You start with 2 10L jugs full of water and a 4L and 5L jug that are empty.
 *
 * you want to be able get two 2L full jugs of water.
 *
 * no water can spill and there anc be no partial pours.
 */
final class Number1Water implements Problem {

  private List<State> getPath() {
    State initialState = new State(new int[]{10, 10, 0 ,0});
    Set<State> seenStates = new HashSet<>();
    seenStates.add(initialState);
    List<List<State>> liveStates = new ArrayList<>();
    List<State> firstState = new ArrayList<>();
    firstState.add(initialState);
    liveStates.add(firstState);

    // I found a solution by hand in 11 steps so we know 11 will solve it
    for (int i =0; i< 40; i++) {
      List<List<State>> newLiveStates = new ArrayList<>();
      for (List<State> aPath : liveStates) {
        List<State> possibleNewState = generateAllNewStates(aPath.get(aPath.size() - 1), seenStates);
        for (State newState : possibleNewState) {
          List<State> newPath = new ArrayList<>(aPath);
          newPath.add(newState);
          if (newState.isDone()) {
            return newPath;
          }
          newLiveStates.add(newPath);
        }
      }
      liveStates = newLiveStates;
    }
    return null;
  }

  private List<State> generateAllNewStates(State state, Set<State> seenStates) {
    List<State> results = new ArrayList<>();

    for (Jug from : Jug.values()) {
      for (Jug to : Jug.values()) {
        if (!from.equals(to)) {
          State newState = state.pour(from, to);
          if (seenStates.add(newState)) {
            results.add(newState);
          }
        }
      }
    }

    return results;
  }

  @Override
  public String solve() {
    StringBuilder sb = new StringBuilder();

    for (State state : getPath()) {
      sb.append(state);
      sb.append("\n");
    }

    return sb.toString();
  }

  @Override
  public String getTitle() {
    return "Problem 1";
  }

  private static class State {
    private final static int[] CAPACITIES = new int[]{10, 10, 5, 4};
    private final int[] amounts;

    State(int[] ammounts) {
      this.amounts = ammounts;
    }

    State pour(Jug from, Jug to) {
      if (from.equals(to)) {
        throw new IllegalArgumentException();
      }

      int pourAmount = Math.min(
          amounts[from.getIndex()],
          CAPACITIES[to.getIndex()] - amounts[to.getIndex()]);

      int[] newAmmounts = amounts.clone();
      newAmmounts[from.getIndex()] -= pourAmount;
      newAmmounts[to.getIndex()] += pourAmount;

      return new State(newAmmounts);
    }

    boolean isDone() {
      return amounts[2] == 2 && amounts[3] == 2;
    }

    @Override
    public boolean equals(Object o) {
      return o instanceof State && Arrays.equals(amounts, ((State) o).amounts);

    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(amounts);
    }

    @Override
    public String toString() {
      return String.format("%d %d %d %d", amounts[0], amounts[1], amounts[2], amounts[3]);
    }
  }

  private enum Jug {
    Jug10a(0),
    Jug10b(1),
    Jug5(2),
    Jug4(3);

    private final int index;

    Jug(int index) {
      this.index = index;
    }

    public int getIndex() {
      return index;
    }
  }
}
