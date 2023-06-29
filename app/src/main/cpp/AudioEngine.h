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

#ifndef SYNTH_DROID_AUDIOENGINE_H
#define SYNTH_DROID_AUDIOENGINE_H

#include <aaudio/AAudio.h>
#include "Oscillator.h"

class AudioEngine {
public:
    bool start();

    void stop();

    void restart();

    void setToneOn(bool isToneOn);

    void setFrequency(int freq);

    void enableTremolo(bool isEnabled);

    void setTremoloRate(int rate);

    void updateTremoloAmplitude();

private:
    Oscillator oscillator_;
    AAudioStream *stream_;
};


#endif //SYNTH_DROID_AUDIOENGINE_H