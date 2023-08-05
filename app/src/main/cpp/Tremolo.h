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



private:
    int tremoloDecreaseRate_ = 200;
    int tremoloDecreaseCounter_ = 0;
    int tremoloIncreaseCounter_ = 0;
    int tremoloIncreaseRate_ = 2000;
    bool isEnabled_ = false;
    float defaultAmplitude_ = 0.5f;
    float currentAmplitude = 0.5f;
    bool isAmplitudeDecreasing_ = true;
    float amplitudeStep_ = 0.005;
    float maxAmplitude_ = 0.5;


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
            currentAmplitude -= amplitudeStep_;
            if(currentAmplitude <= 0.005f) {
                switchTremoloDirection();
            }
        }
    }


    void increaseAmplitude(){
        tremoloIncreaseCounter_++;
        if(tremoloIncreaseCounter_ >= tremoloIncreaseRate_){
            currentAmplitude += amplitudeStep_;
            tremoloIncreaseCounter_ = 0;
        }
        if(currentAmplitude >= maxAmplitude_){
            currentAmplitude = maxAmplitude_;
            switchTremoloDirection();
        }
    }


    void switchTremoloDirection(){
        isAmplitudeDecreasing_ ^= true;
    }
};


#endif //SYNTH_DROID_TREMOLO_H
