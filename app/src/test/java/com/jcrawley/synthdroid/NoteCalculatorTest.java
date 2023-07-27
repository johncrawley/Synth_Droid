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
    public void returnsInterval(){

        assertNote(MusicNote.C_0, Interval.MINOR_SECOND, MusicNote.C_SHARP_0);
        assertNote(MusicNote.B_0, Interval.MINOR_SECOND, MusicNote.C_0);

    }


    private void assertNote(MusicNote baseNote, Interval interval, MusicNote expectedNote){
        MusicNote resultNote = noteCalculator.getInterval(baseNote, interval);
        assertEquals(expectedNote, resultNote);
    }


}
