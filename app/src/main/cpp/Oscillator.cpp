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
#include <android/log.h>

#define TWO_PI (3.14159 * 2)
#define DEFAULT_AMPLITUDE 0.3
#define FREQUENCY 240.0

void Oscillator::setSampleRate(int32_t sampleRate) {
    phaseIncrement_ = (TWO_PI * frequency) / (double) sampleRate;
    savedSampleRate = sampleRate;
}


void Oscillator::setWaveOn(bool isWaveOn) {
    isWaveOn_.store(isWaveOn);
}


void Oscillator::setAmplitude(float value){
    isAmplitudeChangeDue_ = true;
    adjustedAmplitude_ = value;
}


void Oscillator::setDefaultAmplitude(){
    amplitude = 0.3;
}


void  Oscillator::setFrequency(float freq){
    frequency = freq;
    phaseIncrement_ = (TWO_PI * frequency) / (double) savedSampleRate;
}


void  Oscillator::setChorusFrequency(float freq){
    chorusFrequency_ = frequency + freq;
    chorusPhaseIncrement_ = (TWO_PI * chorusFrequency_) / (double) savedSampleRate;
}


void  Oscillator::enableChorus(bool enabled){
    isChorusEnabled_ = enabled;
}


void  Oscillator::enableTremolo(bool enabled) {
    tremolo.setEnabled(enabled);
}


void Oscillator::render(float *audioData, int32_t numFrames) {

    // If the wave has been switched off then reset the phase to zero. Starting at a non-zero value
    // could result in an unwanted audible 'click'
    if (!isWaveOn_.load()){
        phase_ = 0;
        chorusPhase_ = 0;
    }

    for (int i = 0; i < numFrames; i++) {

        if (isWaveOn_.load()) {
            float chorusComponent = 0;
            if(isChorusEnabled_ ){
                chorusComponent = (float) ((sin(chorusPhase_) * amplitude));
                chorusPhase_ += chorusPhaseIncrement_;
                if(chorusPhase_ > TWO_PI){
                    chorusPhase_ = -TWO_PI;
                }
            }
            // Calculates the next sample value for the sine wave.
            audioData[i] = (float) ((sin(phase_) * amplitude)) + chorusComponent;

            // Increments the phase, handling wrap around.
            phase_ += phaseIncrement_;
            if (phase_ > TWO_PI){
                phase_ -= TWO_PI;
                adjustAmplitude();
                tremolo.update(this);
            }
        } else {
            // Outputs silence by setting sample value to zero.
            audioData[i] = 0;
        }
    }
}