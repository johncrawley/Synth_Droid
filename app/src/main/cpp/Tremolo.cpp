//
// Created by john on 28/07/2023.
//

#include "Tremolo.h"
#include <android/log.h>


void Tremolo::setEnabled(bool isEnabled) {
    isEnabled_ = isEnabled;
    if(!isEnabled_){
        currentAmplitude_ = defaultAmplitude_;
    }
}


void Tremolo::setRate(int rate){
    tremoloIncreaseRate_ = rate;
}


float Tremolo::getAmplitude() const{
    return currentAmplitude_;
}


void Tremolo::decayAndStop(){
    __android_log_print(ANDROID_LOG_INFO, "^^^ Tremolo", "entered decayAndStop()");
    isDecaying_ = true;
}


void Tremolo::cancelDecay(){
    isDecaying_ = false;
    currentAmplitude_ = defaultAmplitude_;
}


bool Tremolo::isDecayComplete(){
   bool isComplete =  currentAmplitude_ <= 0 && isDecaying_;
   if(isComplete){
       isDecaying_ = false;
   }
   return isComplete;
}


void Tremolo::resetAmplitude(){
    currentAmplitude_ = defaultAmplitude_;
}


void Tremolo::update() {
    if(isDecaying_){
        decay();
        return;
    }
    if(!isEnabled_){
        return;
    }
   adjustCurrentAmplitude();
}