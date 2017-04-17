package com.bwillard.thebent.spring2017;

import com.bwillard.thebent.common.ProblemSet;

import java.util.Arrays;

/**
 * Problems for the spring 2017 problem set.
 */
public final class SpringProblemSet extends ProblemSet {
    public SpringProblemSet() {
        super(Arrays.asList(
                new Number1Water(),
                new Number2Triangle(),
                new Number3Bricks(),
                new Number4Digits(),
                new Number5Dice(),
                new DoubleBonus()
        ));
    }
}
