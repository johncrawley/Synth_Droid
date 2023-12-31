//
// Modified by j.crawley on 25/06/2023.
// Original source found at: https://github.com/android/codelab-android-wavemaker/
/*
#
# Copyright 2017 The Android Open Source Project
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#      http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#
# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

 */

#ifndef SYNTH_DROID_OSCILLATOR_H
#define SYNTH_DROID_OSCILLATOR_H

#include <atomic>
#include <stdint.h>
#include "Tremolo.h"
#include "Chorus.h"
#include <cmath>
#define TWO_PI (3.14159 * 2)

class Oscillator {
public:
    void setWaveOn(bool isWaveOn);

    void setSampleRate(int32_t sampleRate);

    void render(float *audioData, int32_t numFrames);

    void setFrequency(float freq);

    void setChorusDepth(float freq);

    void setTremoloRate(int value);

    void setAmplitude(float value);

    void setDefaultAmplitude();

    void enableChorus(bool enabled);

    void enableTremolo(bool enabled);

    float getAmplitude();


private:
    // We use an atomic bool to define isWaveOn_ because it is accessed from multiple threads.
    std::atomic<bool> isWaveOn_{false};
    double phase_ = 0.0;
    double chorusPhase_ = 0.0;
    double phaseIncrement_ = 0.0;
    double chorusPhaseIncrement_ = 0.0;
    float amplitude = 0.3;
    float frequency = 240;
    float chorusFrequency_ = 0;
    bool isChorusEnabled_;
    int32_t savedSampleRate = 100;
    bool isAmplitudeChangeDue_ = false;
    float adjustedAmplitude_ = 0.0;
    Tremolo tremolo;
    Chorus chorus;
    float chorusComponent_ = 0;


    void adjustAmplitude(){
        if(!isAmplitudeChangeDue_){
            return;
        }
        isAmplitudeChangeDue_ = false;
        amplitude = adjustedAmplitude_;
    }


    float calculateNextSampleValue(){
        return (float) ((sin(phase_) * amplitude)) + chorusComponent_;
    }


    void resetPhaseIfWaveIsOff(){
        // If the wave has been switched off then reset the phase to zero. Starting at a non-zero value
        // could result in an unwanted audible 'click'
        if (!isWaveOn_.load()){
            phase_ = 0;
            chorusPhase_ = 0;
        }
    }


    void updatePhase(){
        phase_ += phaseIncrement_;
        if (phase_ > TWO_PI){
            phase_ -= TWO_PI;
        }
    }


    void updateAmplitude(){
        tremolo.update();
        amplitude = tremolo.getAmplitude();
        if(tremolo.isDecayComplete()){
            isWaveOn_.store(false);
            tremolo.resetAmplitude();
        }
    }


    void updateChorusComponent(){
        if(isChorusEnabled_ ){
            chorusComponent_ = (float) ((sin(chorusPhase_) * amplitude));
            chorusPhase_ += chorusPhaseIncrement_;
            if(chorusPhase_ > TWO_PI){
                chorusPhase_ = -TWO_PI;
            }
        }
    }

    float outputSilence(){
        return 0;
    }
};


#endif //SYNTH_DROID_OSCILLATOR_H