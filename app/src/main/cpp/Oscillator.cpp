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

void Oscillator::setSampleRate(int32_t sampleRate) {
    phaseIncrement_ = (TWO_PI * frequency) / (double) sampleRate;
    savedSampleRate = sampleRate;
}


void Oscillator::setWaveOn(bool isWaveOn) {
    if(!isWaveOn){
        tremolo.decayAndStop();
        return;
    }
    isWaveOn_.store(isWaveOn);
    tremolo.cancelDecay();
}


void Oscillator::setAmplitude(float value){
    isAmplitudeChangeDue_ = true;
    adjustedAmplitude_ = value;
}


float Oscillator::getAmplitude(){
    return tremolo.getAmplitude();
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


void  Oscillator::setTremoloRate(int value){
    tremolo.setRate(value);
}



void  Oscillator::enableChorus(bool enabled){
    isChorusEnabled_ = enabled;
}


void  Oscillator::enableTremolo(bool enabled) {
    tremolo.setEnabled(enabled);
}


void Oscillator::render(float *audioData, int32_t numFrames) {

    resetPhaseIfWaveIsOff();
    for (int i = 0; i < numFrames; i++) {
        if (isWaveOn_.load()) {
            audioData[i] = calculateNextSampleValue();
            updateChorusComponent();
            updatePhase();
            updateAmplitude();
        } else {
            audioData[i] = outputSilence();
        }
    }
}