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

#include "Oscillator.h"
#include <cmath>

#define TWO_PI (3.14159 * 2)
#define DEFAULT_AMPLITUDE 0.3
#define FREQUENCY 240.0

void Oscillator::setSampleRate(int32_t sampleRate) {
    phaseIncrement_ = (TWO_PI * frequency) / (double) sampleRate;
    saved_sample_rate = sampleRate;
}

void setSampleRate2(){
}



void Oscillator::setWaveOn(bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}

void Oscillator::reduceVolume(){
    if(amplitude > amplitude_lower_limit){
        amplitude -= 0.001;
    }
}


void  Oscillator::setFrequency(float freq){
    frequency = freq;
    phaseIncrement_ = (TWO_PI * frequency) / (double) saved_sample_rate;
}


void Oscillator::resetVolume(){
    amplitude = DEFAULT_AMPLITUDE;
}



void Oscillator::render(float *audioData, int32_t numFrames) {

    // If the wave has been switched off then reset the phase to zero. Starting at a non-zero value
    // could result in an unwanted audible 'click'
    if (!isWaveOn_.load()) phase_ = 0;
    float extra = 0;
    float limit = 0;

    for (int i = 0; i < numFrames; i++) {

        if (isWaveOn_.load()) {

            // Calculates the next sample value for the sine wave.
            audioData[i] = (float) ((sin(phase_) * amplitude)) + extra;
            extra++;
            if(extra > limit){
                extra = 0;
            }
            // Increments the phase, handling wrap around.
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI) phase_ -= TWO_PI;

        } else {
            // Outputs silence by setting sample value to zero.
            audioData[i] = 0;
        }
    }
}