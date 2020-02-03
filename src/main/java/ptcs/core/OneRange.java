package ptcs.core;

import java.util.*;

import ptcs.pcs.PcsI;

class OneRange {
    // we need to add a comp or analysis object, like
    // what is purpose of this class?
    int col;
    int row;
    Comp section;
    Map<PcsI,Double> scores;


    public OneRange(int col, int row, 
        Comp section, Map<PcsI,Double> scores) {

        this.col = col;
        this.row = row;
        this.section = section;
        this.scores = scores;
    }
}