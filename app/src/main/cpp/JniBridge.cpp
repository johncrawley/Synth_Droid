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

#include <jni.h>
#include <android/input.h>
#include "AudioEngine.h"

static AudioEngine *audioEngine = new AudioEngine();

extern "C" {

JNIEXPORT void JNICALL
*Java_com_jcrawley_synthdroid_MainActivity_touchEvent(JNIEnv *env, jobject obj, jint action) {
    switch (action) {
        case AMOTION_EVENT_ACTION_DOWN:
        audioEngine->setToneOn(true);
        break;
        case AMOTION_EVENT_ACTION_UP:
        audioEngine->setToneOn(false);
        break;
        default:
        break;
    }
    return 0;
}

JNIEXPORT void JNICALL
*Java_com_jcrawley_synthdroid_MainActivity_startEngine(JNIEnv *env, jobject /* this */) {
audioEngine->start();
    return 0;
}

JNIEXPORT void JNICALL
*Java_com_jcrawley_synthdroid_MainActivity_stopEngine(JNIEnv *env, jobject /* this */) {
audioEngine->stop();
    return 0;
}

}
