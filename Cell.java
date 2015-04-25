/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dungeondisplay;

/**
 *
 * @author lispascal
 */
class Cell {
    int terrain = 8;
    int object = 0;
    int effect = 0;
    int roomNum = 0;
    void changeTerrain(int newTerrain) {
        terrain = newTerrain;
//        resetToolTipText();
    }

    void changeObject(int newObject) {
        object = newObject;
//        resetToolTipText();
    }

    void changeEffect(int newEffect) {
        effect = newEffect;
//        resetToolTipText();
    }
}
