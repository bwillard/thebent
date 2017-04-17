package com.bwillard.thebent.common;

/**
 * A single problem.
 */
public interface Problem {
    /** Returns the solution to the problem **/
    String solve();

    String getTitle();
}
