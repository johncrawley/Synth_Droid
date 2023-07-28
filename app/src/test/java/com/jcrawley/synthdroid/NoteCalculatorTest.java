package com.jcrawley.synthdroid;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class NoteCalculatorTest {

    NoteCalculator noteCalculator;

    @Before
    public void setup(){
        noteCalculator = new NoteCalculator();
    }

    @Test
    public void returnsIntervalForMinorSecond(){
        assertNote(MusicNote.B_0, Interval.MINOR_SECOND, MusicNote.C_1);
        assertNote(MusicNote.C_0, Interval.MINOR_SECOND, MusicNote.C_SHARP_0);
        assertNote(MusicNote.G_SHARP_0, Interval.MINOR_SECOND, MusicNote.A_0);
    }


    @Test
    public void returnsCorrectIntervalForPerfectFifth(){
        assertNote(MusicNote.D_5, Interval.PERFECT_FIFTH, MusicNote.A_5);
        assertNote(MusicNote.E_5, Interval.PERFECT_FIFTH, MusicNote.B_5);
        assertNote(MusicNote.F_5, Interval.PERFECT_FIFTH, MusicNote.C_6);
    }


    @Test
    public void returnsCorrectIntervalForOctave(){
        assertNote(MusicNote.A_5, Interval.OCTAVE, MusicNote.A_6);
        assertNote(MusicNote.D_5, Interval.OCTAVE, MusicNote.D_6);
        assertNote(MusicNote.G_5, Interval.OCTAVE, MusicNote.G_6);
    }


    private void assertNote(MusicNote baseNote, Interval interval, MusicNote expectedNote){
        MusicNote resultNote = noteCalculator.addIntervalUp(baseNote, interval);
        assertEquals(expectedNote, resultNote);
    }


}
