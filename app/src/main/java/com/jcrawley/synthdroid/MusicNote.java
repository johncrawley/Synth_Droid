package com.jcrawley.synthdroid;

import static com.jcrawley.synthdroid.SimpleNote.A;
import static com.jcrawley.synthdroid.SimpleNote.A_SHARP;
import static com.jcrawley.synthdroid.SimpleNote.B;
import static com.jcrawley.synthdroid.SimpleNote.C;
import static com.jcrawley.synthdroid.SimpleNote.C_SHARP;
import static com.jcrawley.synthdroid.SimpleNote.D;
import static com.jcrawley.synthdroid.SimpleNote.D_SHARP;
import static com.jcrawley.synthdroid.SimpleNote.E;
import static com.jcrawley.synthdroid.SimpleNote.F;
import static com.jcrawley.synthdroid.SimpleNote.F_SHARP;
import static com.jcrawley.synthdroid.SimpleNote.G;
import static com.jcrawley.synthdroid.SimpleNote.G_SHARP;

public enum MusicNote {
    C_0(16.35f, "C0", C),
    C_SHARP_0(17.32f, "C#0", C_SHARP),
    D_0(18.35f, "D0", D),
    D_SHARP_0(19.45f, "D#0", D_SHARP),
    E_0(20.60f, "E0", E),
    F_0(21.83f, "F0", F),
    F_SHARP_0(23.12f, "F#0", F_SHARP),
    G_0(24.50f, "G0", G),
    G_SHARP_0(25.96f, "G#0", G_SHARP),
    A_0(27.50f, "A0", A),
    A_SHARP_0(29.14f, "A#0", A_SHARP),
    B_0(30.87f, "B0", B),
    C_1(32.70f, "C1", C),
    C_SHARP_1(34.65f, "C#1", C_SHARP),
    D_1(36.71f, "D1", D),
    D_SHARP_1(38.89f, "D#1", D_SHARP),
    E_1(41.20f, "E1", E),
    F_1(43.65f, "F1", F),
    F_SHARP_1(46.25f, "F#1", F_SHARP),
    G_1(49.00f, "G1", G),
    G_SHARP_1(51.91f, "G#1", G_SHARP),
    A_1(55.00f, "A1", A),
    A_SHARP_1(58.27f, "A#1", A_SHARP),
    B_1(61.74f, "B1", B),
    C_2(65.41f, "C2", C),
    C_SHARP_2(69.30f, "C#2", C_SHARP),
    D_2(73.42f, "D2", D),
    D_SHARP_2(77.78f, "D#2", D_SHARP),
    E_2(82.41f, "E2", E),
    F_2(87.31f, "F2", F),
    F_SHARP_2(92.50f, "F#2", F_SHARP),
    G_2(98.00f, "G2", G),
    G_SHARP_2(103.83f, "G#2", G_SHARP),
    A_2(110.00f, "A2", A),
    A_SHARP_2(116.54f, "A#2", A_SHARP),
    B_2(123.47f, "B2", B),
    C_3(130.81f, "C3", C),
    C_SHARP_3(138.59f, "C#3", C_SHARP),
    D_3(146.83f, "D3", D),
    D_SHARP_3(155.56f, "D#3", D_SHARP),
    E_3(164.81f, "E3", E),
    F_3(174.61f, "F3", F),
    F_SHARP_3(185.00f, "F#3", F_SHARP),
    G_3(196.00f, "G3", G),
    G_SHARP_3(207.65f, "G#3", G_SHARP),
    A_3(220.00f, "A3", A),
    A_SHARP_3(233.08f, "A#3", A_SHARP),
    B_3(246.94f, "B3", B),
    C_4(261.63f, "C4", C),
    C_SHARP_4(277.18f, "C#4", C_SHARP),
    D_4(293.66f, "D4", D),
    D_SHARP_4(311.13f, "D#4", D_SHARP),
    E_4(329.63f, "E4", E),
    F_4(349.23f, "F4", F),
    F_SHARP_4(369.99f, "F#4", F_SHARP),
    G_4(392.00f, "G4", G),
    G_SHARP_4(415.30f, "G#4", G_SHARP),
    A_4(440.00f, "A4", A),
    A_SHARP_4(466.16f, "A#4", A_SHARP),
    B_4(493.88f, "B4", B),
    C_5(523.25f, "C5", C),
    C_SHARP_5(554.37f, "C#5", C_SHARP),
    D_5(587.33f, "D5", D),
    D_SHARP_5(622.25f, "D#5", D_SHARP),
    E_5(659.25f, "E5", E),
    F_5(698.46f, "F5", F),
    F_SHARP_5(739.99f, "F#5", F_SHARP),
    G_5(783.99f, "G5", G),
    G_SHARP_5(830.61f, "G#5", G_SHARP),
    A_5(880.00f, "A5", A),
    A_SHARP_5(932.33f, "A#5", A_SHARP),
    B_5(987.77f, "B5", B),
    C_6(1046.50f, "C6", C),
    C_SHARP_6(1108.73f, "C#6", C_SHARP),
    D_6(1174.66f, "D6", D),
    D_SHARP_6(1244.51f, "D#6", D_SHARP),
    E_6(1318.51f, "E6", E),
    F_6(1396.91f, "F6", F),
    F_SHARP_6(1479.98f, "F#6", F_SHARP),
    G_6(1567.98f, "G6", G),
    G_SHARP_6(1661.22f, "G#6", G_SHARP),
    A_6(1760.00f, "A6", A),
    A_SHARP_6(1864.66f, "A#6", A_SHARP),
    B_6(1975.53f, "B6", B),
    C_7(2093.00f, "C7", C),
    C_SHARP_7(2217.46f, "C#7", C_SHARP),
    D_7(2349.32f, "D7", D),
    D_SHARP_7(2489.02f, "D#7", D_SHARP),
    E_7(2637.02f, "E7", E),
    F_7(2793.83f, "F7", F),
    F_SHARP_7(2959.96f, "F#7", F_SHARP),
    G_7(3135.96f, "G7", G),
    G_SHARP_7(3322.44f, "G#7", G_SHARP),
    A_7(3520.00f, "A7", A),
    A_SHARP_7(3729.31f, "A#7", A_SHARP),
    B_7(3951.07f, "B7", B),
    C_8(4186.01f, "C8", C),
    C_SHARP_8(4434.92f, "C#8", C_SHARP),
    D_8(4698.63f, "D8", D),
    D_SHARP_8(4978.03f, "D#8", D_SHARP),
    E_8(5274.04f, "E8", E),
    F_8(5587.65f, "F8", F),
    F_SHARP_8(5919.91f, "F#8", F_SHARP),
    G_8(6271.93f, "G8", G),
    G_SHARP_8(6644.88f, "G#8", G_SHARP),
    A_8(7040.00f, "A8", A),
    A_SHARP_8(7458.62f, "A#8", A_SHARP),
    B_8(7902.13f, "B8", B);

    private final float frequency;
    private final String displayName;
    private final SimpleNote note;

    MusicNote(float frequency, String displayName, SimpleNote note){
        this.frequency = frequency;
        this.displayName = displayName;
        this.note = note;
    }


    public float getFrequency(){
        return frequency;
    }


    public String getDisplayName(){
        return displayName;
    }


    public SimpleNote getNote(){return note;}
}
