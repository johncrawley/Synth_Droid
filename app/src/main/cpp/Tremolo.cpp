//
// Created by john on 28/07/2023.
//

#include "Tremolo.h"
#include <android/log.h>


void Tremolo::setEnabled(bool isEnabled) {
    isEnabled_ = isEnabled;
}


void Tremolo::setRate(int rate){
    tremoloIncreaseRate_ = rate;
}


float Tremolo::getAmplitude(){
    return currentAmplitude;
}


void Tremolo::update() {
    if(!isEnabled_){
        return;
    }
   adjustCurrentAmplitude();
}