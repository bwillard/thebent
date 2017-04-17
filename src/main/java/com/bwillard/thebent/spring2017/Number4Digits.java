package com.bwillard.thebent.spring2017;


import com.bwillard.thebent.common.Primes;
import com.bwillard.thebent.common.Problem;

import java.util.ArrayList;
import java.util.List;

/**
 *  A, B, C, D represent four different digits that can be combined in 24 different numbers.
 *
 *  these 24 numbers have the following properties:
 *   - 4 are prime
 *   - 7 are the product of two different primes
 *   - 1 is the square of a prime
 *   - 8 are divisible by 2 but not 4
 *   - 2 are divisible by 4 btu not by 8
 *   - 1 is divisible by 8 but noy 16
 *   - 1 is divisible by 16
 *
 */
final class Number4Digits implements Problem {

  private int getNumber() {
    List<List<Integer>> possible = getAllMatchingCombinations();
    if (possible.size() != 1) {
      throw new IllegalStateException();
    }
    return possible.get(0).get(0);
  }

  private List<List<Integer>> getAllMatchingCombinations() {
    List<List<Integer>> combinations = new ArrayList<>();
    for (int a = 0; a < 10; a++) {
      for (int b = a + 1; b < 10; b++) {
        for (int c = b +1; c < 10; c++) {
          for (int d = c + 1; d < 10; d++) {
            List<Integer> digits = new ArrayList<>(4);
            digits.add(a);
            digits.add(b);
            digits.add(c);
            digits.add(d);
            List<Integer> set = getCombinations(digits);
            if (isCandidate(set)) {
              combinations.add(getCombinations(digits));
            }
          }
        }
      }
    }
    return combinations;
  }

  private static boolean isCandidate(List<Integer> numbers) {
    return 4 == getNumberPrime(numbers)
        && 1 == squareOfPrime(numbers)
        && 8 == divisibleBy2Not4(numbers)
        && 2 == divisibleBy4Not8(numbers)
        && 1 == divisibleBy8Not16(numbers)
        && 1 == divisibleBy16(numbers);
  }

  private static List<Integer> getCombinations(List<Integer> digits) {
    List<Integer> results = new ArrayList<Integer>();
    for (int index = 0; index < digits.size(); index++) {
      List<Integer> copy = new ArrayList<Integer>(digits);
      int digit = copy.remove(index);
      List<Integer> numbers = new ArrayList<Integer>();
      numbers.add(digit);
      results.addAll(getCombinations(numbers, copy));
    }
    return results;
  }

  private static List<Integer> getCombinations(List<Integer> numbers, List<Integer> digits) {
    if (digits.isEmpty()) {
      return numbers;
    }
    List<Integer> results = new ArrayList<Integer>(numbers.size() * digits.size());

    for (int digit : digits) {
      List<Integer> newDigits = new ArrayList<Integer>(digits);
      newDigits.remove((Integer) digit);
      List<Integer> newNumbers = new ArrayList<Integer>(numbers.size());
      for (int oldNumber : numbers) {
        int newNumber = oldNumber * 10 + digit;
        newNumbers.add(newNumber);
        results.addAll(getCombinations(newNumbers, newDigits));
      }
    }

    return results;
  }

  // Conditions

  private static long getNumberPrime(List<Integer> integers) {
    return integers.stream().filter(Primes::isPrime).count();
  }

  private static long divisibleBy2Not4(List<Integer> integers) {
    return integers.stream().filter(i -> (i % 2 == 0) && (i % 4 != 0)).count();
  }

  private static long divisibleBy4Not8(List<Integer> integers) {
    return integers.stream().filter(i -> (i % 4 == 0) && (i % 8 != 0)).count();
  }

  private static long divisibleBy8Not16(List<Integer> integers) {
    return integers.stream().filter(i -> (i % 8 == 0) && (i % 16 != 0)).count();
  }

  private static long divisibleBy16(List<Integer> integers) {
    return integers.stream().filter(i -> i % 16 == 0).count();
  }

  private static long squareOfPrime(List<Integer> integers) {
    return integers.stream().filter(i ->
        {
          double root = Math.sqrt(i);
          int intRoot = (int) root;
          return intRoot == root && Primes.isPrime(intRoot);
        }
    ).count();
  }

  @Override
  public String solve() {
    return Integer.toString(getNumber());
  }

  @Override
  public String getTitle() {
    return "Problem 4";
  }
}
