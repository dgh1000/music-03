package ptcs.core;

import org.apache.commons.math3.fraction.Fraction;

public class Beat {
    int measure;
    Fraction beat;
    public Beat (int measure, Fraction beat) {
        this.measure = measure;
        this.beat = beat;
    }
}