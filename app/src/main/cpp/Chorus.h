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




private:

    bool isEnabled_;
    float chorusComponent_;
    float chorusPhase_;
    float phaseIncrement_;


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
