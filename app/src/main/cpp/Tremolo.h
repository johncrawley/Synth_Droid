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



private:
    int rate_ = 10000;
    int tremoloCounter_ = 0;
    bool isEnabled_ = false;



};


#endif //SYNTH_DROID_TREMOLO_H
