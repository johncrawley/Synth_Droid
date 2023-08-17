package com.jcrawley.synthdroid;


public class NoteCalculator {

    private final MusicNote[] musicNotes = MusicNote.values();

    public MusicNote addIntervalUp(MusicNote baseNote, Interval interval){
        int intervalNumber = interval.ordinal() + 1;
        for(int i =0; i< musicNotes.length; i++){
            System.out.print(" "  + musicNotes[i] + " ");
            if(musicNotes[i] == baseNote){
                return musicNotes[i + intervalNumber];
            }
        }
        return baseNote;
    }
}
