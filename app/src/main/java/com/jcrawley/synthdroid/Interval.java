package com.jcrawley.synthdroid;

public enum Interval {
    MINOR_SECOND(1),
    MAJOR_SECOND(2),
    MINOR_THIRD(3),
    MAJOR_THIRD(4),
    PERFECT_FOURTH(5),
    DIMINISHED_FIFTH(6),
    PERFECT_FIFTH(7),
    MINOR_SIXTH(8),
    MAJOR_SIXTH(9),
    MINOR_SEVENTH(10),
    MAJOR_SEVENTH(11),
    OCTAVE(12);

    final int value;

    Interval(int value){
        this.value = value;
    }
}
