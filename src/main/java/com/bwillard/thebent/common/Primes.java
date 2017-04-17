package com.bwillard.thebent.common;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Primes implements Iterator<Integer>{
  private static List<Integer> primes = new ArrayList<>();
  private int index = 0;

  public static boolean isPrime(int number) {
    if (number == 2) {
      return true;
    }
    double sqrt = Math.sqrt(number);
    Iterator<Integer> primes = new Primes();
    while(primes.hasNext()) {
      int factor = primes.next();
      if (number % factor == 0) {
        return false;
      }
      if (factor > sqrt) {
        return true;
      }
    }
    return true;
  }

  @Override
  public boolean hasNext() {
    return true;
  }

  @Override
  public synchronized  Integer next() {
    if (index < primes.size()) {
      int prime = primes.get(index);
      index++;
      return prime;
    }

    if (primes.isEmpty()) {
      primes.add(2);
      primes.add(3);
      primes.add(5);
      index++;
      return 2;
    }

    int guess = primes.get(primes.size() - 1) + 2;

    while (true) {
      if (isPrime(guess)) {
        primes.add(guess);
        index++;
        return guess;
      }
      guess += 2;
    }

  }
}
