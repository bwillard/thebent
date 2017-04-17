package com.bwillard.thebent.common;

/**
 * A set of problems.
 */
public abstract class ProblemSet {
    private final Iterable<Problem> problems;

    /** Adds a problem to the problem set. **/
    protected ProblemSet(Iterable<Problem> problems) {
        this.problems = problems;
    }

    /** Solves all the problems in the problem set**/
    public final String solve() {
        StringBuilder solutions = new StringBuilder();
        for (Problem p : problems) {
            solutions.append("Problem " + p.getTitle() + ": ");
            solutions.append("\n");
            solutions.append(p.solve());
            solutions.append("\n");
          solutions.append("\n");
        }
        return solutions.toString();
    }
}
