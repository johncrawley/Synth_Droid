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

class Oscillator {
public:
    void setWaveOn(bool isWaveOn);

    void setSampleRate(int32_t sampleRate);

    void render(float *audioData, int32_t numFrames);

    void setFrequency(float freq);

    void enableTremolo(bool isEnabled);

    void setTremoloRate(int rate);

    void updateTremoloAmplitude();


private:
    // We use an atomic bool to define isWaveOn_ because it is accessed from multiple threads.
    std::atomic<bool> isWaveOn_{false};
    double phase_ = 0.0;
    double phaseIncrement_ = 0.0;
    float amplitude = 0.3;
    float amplitudeUpperLimit = 0.3;
    float amplitudeLowerLimit = 0.002;
    float frequency = 240;
    int32_t savedSampleRate = 100;
    bool isTremoloEnabled = false;
    bool isTremoloAmplitudeDecreasing = true;
    float tremoloStep = 0.01f;


    void reduceAmplitude(double reductionRate){
        if(amplitude > amplitudeLowerLimit){
            amplitude -= reductionRate;
        }
    }


    void increaseAmplitude(double incValue){
        if(amplitude < amplitudeUpperLimit){
            amplitude += incValue;
        }
    }


    void switchTremoloDirection(){
        isTremoloAmplitudeDecreasing = !isTremoloAmplitudeDecreasing;
    }


    void adjustAmplitude(){
        if(!isTremoloEnabled) {
            return;
        }
        if(isTremoloAmplitudeDecreasing){
            reduceAmplitude(tremoloStep);
            if(amplitude <= amplitudeLowerLimit){
                switchTremoloDirection();
            }
            return;
        }
        increaseAmplitude(tremoloStep);
        if(amplitude >= amplitudeUpperLimit){
            switchTremoloDirection();
        }
    }


};


#endif //SYNTH_DROID_OSCILLATOR_H