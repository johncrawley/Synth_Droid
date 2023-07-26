package com.jcrawley.synthdroid;

import androidx.annotation.NonNull;

public enum SimpleNote {
    A("A"),
    A_SHARP("A#"),
    B("B"),
    C("C"),
    C_SHARP("C#"),
    D("D"),
    D_SHARP("D#"),
    E("E"),
    F("F"),
    F_SHARP("F#"),
    G("G"),
    G_SHARP("G#");

    private final String displayStr;


    SimpleNote(String displayStr){
        this.displayStr = displayStr;
    }


    @NonNull
    public String toString(){
        return displayStr;
    }
}
