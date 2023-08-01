//
// Created by john on 28/07/2023.
//

#ifndef SYNTH_DROID_TREMOLO_H
#define SYNTH_DROID_TREMOLO_H

#include "Oscillator.h"

class Tremolo {

public:
    void setEnabled(bool isTremoloEnabled);

    void setRate(int rate);

    void update(Oscillator &oscillator);



private:
    int rate_ = 10000;
    int tremoloCounter_ = 0;
    int tremoloIncreaseCounter_ = 0;
    int tremoloIncreaseRate_ = 3000;
    bool isEnabled_ = false;
    float currentAmplitude = 0.5;
    bool isAmplitudeDecreasing_ = false;
    float amplitudeStep_ = 0.001;
    float maxAmplitude = 0.5;


    void adjustCurrentAmplitude(){
        if(isAmplitudeDecreasing_){
            currentAmplitude -= amplitudeStep_;
            if(currentAmplitude <= 0.005f) {
                switchTremoloDirection();
            }
            return;
        }
        increaseAmplitude();
        if(currentAmplitude >= maxAmplitude){
            switchTremoloDirection();
        }
    }

    void switchTremoloDirection(){
        isAmplitudeDecreasing_ = !isAmplitudeDecreasing_;
    }


    void increaseAmplitude(){
        if(tremoloIncreaseCounter_++ == tremoloIncreaseRate_){
            if(currentAmplitude < maxAmplitude){
                currentAmplitude += amplitudeStep_;
            }
            tremoloIncreaseCounter_ = 0;
        }
    }

};


#endif //SYNTH_DROID_TREMOLO_H
