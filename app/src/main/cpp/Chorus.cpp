//
// Created by john on 23/08/2023.
//

#include "Chorus.h"


void Chorus::setEnabled(bool isEnabled) {
    isEnabled_ = isEnabled;
}


void Chorus::setDepth(float baseFrequency, float chorusDepth) {
    currentFrequency_ = baseFrequency + chorusDepth;
    phaseIncrement_ = (TWO_PI * currentFrequency_) / (double) sampleRate_;
}


void Chorus::setSampleRate(int32_t sampleRate){
    sampleRate_ = sampleRate;
}


float Chorus::getChorusComponent() {
    return chorusComponent_;
}