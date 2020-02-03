package ptcs.core; 

import org.apache.commons.math3.fraction.*;

public class Timing {

    private Fraction tOn;
    private Fraction tOff;

    public Timing(Fraction tOn, Fraction tOff) {
        this.tOn = tOn;
        this.tOff = tOff;
    }

    public Fraction getTOn() {
        return tOn;
    }

    public Fraction getTOff() {
        return tOff;
    }
}