//
// Created by john on 28/07/2023.
//

#ifndef SYNTH_DROID_TREMOLO_H
#define SYNTH_DROID_TREMOLO_H


class Tremolo {

public:
    void setEnabled(bool isTremoloEnabled);

    void setRate(int rate);

    void update();

    float getAmplitude();

    void decayAndStop();

    void cancelDecay();

    bool isDecayComplete();

    void resetAmplitude();



private:
    int decayRate_ = 50;
    int decayCounter_ = 0;
    int tremoloDecreaseRate_ = 100;
    int tremoloDecreaseCounter_ = 0;
    int tremoloIncreaseCounter_ = 0;
    int tremoloIncreaseRate_ = 2000;
    bool isEnabled_ = false;
    float defaultAmplitude_ = 0.5f;
    float currentAmplitude_ = 0.5f;
    bool isAmplitudeDecreasing_ = true;
    float amplitudeStep_ = 0.002;
    float maxAmplitude_ = 0.5;
    bool isDecaying_ = false;


    void adjustCurrentAmplitude(){
        if(isAmplitudeDecreasing_){
            decreaseAmplitude();
            return;
        }
        increaseAmplitude();
    }


    void decreaseAmplitude(){
        tremoloDecreaseCounter_++;
        if (tremoloDecreaseCounter_ >= tremoloDecreaseRate_) {
            tremoloDecreaseCounter_ = 0;
            currentAmplitude_ -= amplitudeStep_;
            if(currentAmplitude_ <= 0.005f) {
                switchTremoloDirection();
            }
        }
    }


    void increaseAmplitude(){
        tremoloIncreaseCounter_++;
        if(tremoloIncreaseCounter_ >= tremoloIncreaseRate_){
            currentAmplitude_ += amplitudeStep_;
            tremoloIncreaseCounter_ = 0;
        }
        if(currentAmplitude_ >= maxAmplitude_){
            currentAmplitude_ = maxAmplitude_;
            switchTremoloDirection();
        }
    }


    void decay(){
        decayCounter_++;
        if(decayCounter_ >= decayRate_){
            currentAmplitude_ -= amplitudeStep_;
            decayCounter_ = 0;
        }
    }


    void switchTremoloDirection(){
        isAmplitudeDecreasing_ ^= true;
    }
};


#endif //SYNTH_DROID_TREMOLO_H
