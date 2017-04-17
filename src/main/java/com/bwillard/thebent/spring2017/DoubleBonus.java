package com.bwillard.thebent.spring2017;

import com.bwillard.thebent.common.Problem;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * With a standard set of double 6 dominos lay them out in a 7 domino square
 * (with a half domino overlap on each end) so that the sum of the pips (7.5 dominos) one
 * each side is equal.
 */
final class DoubleBonus implements Problem {

  private DominoLine getAcceptableLine() {
    Set<Domino> set = getStandardSet();
    for (Domino domino : set) {
      Set<Domino> newSet1 = new HashSet<>(set);
      newSet1.remove(domino);

      DominoLine line = new DominoLine();
      line.add(domino.clone());
      DominoLine possibleLine = getLine(line, newSet1);
      if (possibleLine != null) {
        return possibleLine;
      }

      if (domino.getPip1() != domino.getPip2()) {
        DominoLine lineFlipped = new DominoLine();
        Domino cloned = domino.clone();
        cloned.flip();
        lineFlipped.add(cloned);
        Set<Domino> newSet2 = new HashSet<>(set);
        newSet2.remove(domino);
        possibleLine = getLine(line, newSet2);
        if (possibleLine != null) {
          return possibleLine;
        }
      }
    }

    return null;
  }

  private DominoLine getLine(DominoLine line, Set<Domino> dominos) {
    if (dominos.isEmpty()) {
      return line;
    }
    for(Domino domino : dominos) {
      if (line.willFit(domino)) {
        DominoLine newLine = line.clone();
        Domino newDomino = domino.clone();
        newLine.add(newDomino);
        if (newLine.isValid()) {
          Set<Domino> newSet = new HashSet<>(dominos);
          newSet.remove(domino);
          DominoLine possibleLine = getLine(newLine, newSet);
          if (possibleLine != null) {
            return possibleLine;
          }
        }
      }
    }

    return null;
  }

  private static Set<Domino> getStandardSet() {
    Set<Domino> set = new HashSet<>();
    for (int i =0; i< 7; i++) {
      for (int j = i; j< 7; j++) {
        set.add(new Domino(i, j));
      }
    }

    return set;
  }

  @Override
  public String solve() {
    return getAcceptableLine().toString();
  }

  @Override
  public String getTitle() {
    return "Double bonus:";
  }

  private static class DominoLine {
    private final List<Domino> dominoList = new ArrayList<>();

    private int getExposed() {
      if (dominoList.isEmpty()) {
        throw new IllegalStateException("Can't get exposed of empty list");
      }
      return dominoList.get(dominoList.size() - 1).getPip2();
    }

    private int getFirst() {
      if (dominoList.isEmpty()) {
        throw new IllegalStateException("Can't get first of empty list");
      }
      return dominoList.get(0).getPip1();
    }

    boolean isValid() {
      if (dominoList.size() < 9) {
        return true;
      }
      int firstSide = getSideSize(0);

      if (firstSide < 42 || firstSide > 48) {
        return false;
      }

      if (dominoList.size() > 15) {
        int sideTwo = getSideSize(1);
        if (sideTwo != firstSide) {
          return false;
        }
      }

      if (dominoList.size() > 22) {
        int sideThree = getSideSize(2);
        if (sideThree != firstSide) {
          return false;
        }
      }

      if (dominoList.size() == 28) {
        int sideFour = getSideSize(3);
        if (sideFour != firstSide) {
          return false;
        }
        return getExposed() == getFirst();
      }

      return true;
    }

    private int getSideSize(int side) {
      int sum = 0;
      for (int i = side * 7; i < (side + 1) * 7; i++) {
        sum += dominoList.get(i).getSum();
      }
      if (side == 3){
        sum += dominoList.get(0).getPip1();
      } else {
        sum += dominoList.get((side + 1) * 7).getPip1();
      }

      return sum;
    }

    private boolean willFit(Domino domino) {
      if (dominoList.isEmpty()) {
        return true;
      }

      return domino.getPip1() == getExposed() || domino.getPip2() == getExposed();
    }

    private void add(Domino domino) {
      if (!willFit(domino)) {
        throw new IllegalArgumentException("Domino won't fit");
      }

      if (!dominoList.isEmpty()) {
        if (domino.getPip1() != getExposed()) {
          domino.flip();
        }
      }

      dominoList.add(domino);
    }

    public DominoLine clone() {
      DominoLine newLine = new DominoLine();
      newLine.dominoList.addAll(dominoList);
      return newLine;
    }

    @Override
    public String toString() {
      StringBuilder sb = new StringBuilder();

      int index = 0;
      for (Domino d : dominoList) {
        index++;
        sb.append(d.toString());
        sb.append("|");
        if (index % 7 == 0 ) {
          if (index == 28) {
            sb.append(dominoList.get(0).getPip1());
          } else {
            sb.append(dominoList.get(index).getPip1());
          }
          //sb.append(" (" + getSideSize((index / 7) -1) + ")");
          sb.append("\n");
        }

      }

      return sb.toString();
    }
  }

  private static class Domino {
    private boolean flipped = false;
    private final int pip1;
    private final int pip2;

    Domino(int pip1, int pip2) {
      this.pip1 = pip1;
      this.pip2 = pip2;
    }

    int getPip1() {
      return flipped ? pip2 : pip1;
    }

    int getPip2() {
      return flipped ? pip1 : pip2;
    }

    int getSum() {
      return pip1 + pip2;
    }

    void flip() {
      this.flipped ^= true;
    }

    @Override
    public String toString() {
      return flipped ? (pip2 + "-" + pip1) : (pip1 + "-" + pip2);
    }

    public Domino clone() {
      return new Domino(flipped ? pip2 : pip1, flipped ? pip1 :pip2);
    }
  }
}
