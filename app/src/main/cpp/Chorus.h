//
// Created by john on 23/08/2023.
//

#ifndef SYNTH_DROID_CHORUS_H
#define SYNTH_DROID_CHORUS_H


#include <cmath>
#define TWO_PI (3.14159 * 2)

class Chorus {

public:
    void setEnabled(bool isChorusEnabled);

    void setRate(int rate);

    void setDepth(float baseFrequency, float chorusDepth);

    void setSampleRate(int32_t sampleRate);

    float getChorusComponent();


private:

    bool isEnabled_;
    float chorusComponent_;
    float chorusPhase_;
    double phaseIncrement_;
    float currentFrequency_ = 2;
    int32_t sampleRate_;


    void updateChorusComponent(float amplitude){
        if(isEnabled_ ){
            chorusComponent_ = (float) ((sin(chorusPhase_) * amplitude));
            chorusPhase_ += phaseIncrement_;
            if(chorusPhase_ > TWO_PI){
                chorusPhase_ = -TWO_PI;
            }
        }
    }

};


#endif //SYNTH_DROID_CHORUS_H
