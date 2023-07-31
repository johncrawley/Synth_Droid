//
// Created by john on 28/07/2023.
//

#include "Tremolo.h"
#include "Oscillator.h"


void Tremolo::setEnabled(bool isEnabled) {
    isEnabled_ = isEnabled;
}


void Tremolo::setRate(int rate){
    rate_ = rate;
}


void Tremolo::update(Oscillator &oscillator) {
    if(!isEnabled_){
        return;
    }
    tremoloCounter_++;
    if (tremoloCounter_ > rate_) {
        tremoloCounter_ = 0;
        adjustCurrentAmplitude();
        oscillator.setAmplitude(currentAmplitude);
    }
}