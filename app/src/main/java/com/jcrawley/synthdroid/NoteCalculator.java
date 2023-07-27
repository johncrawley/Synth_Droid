package com.jcrawley.synthdroid;


public class NoteCalculator {

    private SimpleNote[] notes = SimpleNote.values();
    private MusicNote[] musicNotes = MusicNote.values();

    public MusicNote getInterval(MusicNote baseNote, Interval interval){
        SimpleNote simpleNote = baseNote.getNote();
        int sum =( (simpleNote.ordinal() + interval.ordinal()) % 12) - 2;
        for(int i =0; i< musicNotes.length; i++){
            if(musicNotes[i] == baseNote){
                return musicNotes[i + sum];
            }
        }
        return baseNote;
    }
}
