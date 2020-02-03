package ptcs.gen;
import ptcs.core.*;
import java.util.*;
import org.apache.commons.math3.fraction.Fraction;

public class RandomComp {
    static public Comp randomComp(
        int nNotes, int minPitch, int maxPitch, 
        Fraction tMin, Fraction tMax, Random rand) 
    {

        Comp c = new Comp();
        Fraction diff = tMax.subtract(tMin);
        double minDur = diff.doubleValue() / 15;
        double maxDur = diff.doubleValue() / 4;

        for (int i = 0; i < nNotes; i++) {
            Note n = randomNote(
                tMin.doubleValue(), tMax.doubleValue(), minDur, maxDur, minPitch, maxPitch, rand);
            c.addNote(n);
        }
        return c;
    
    }

    static public Note randomNote(
        double tOnMin, double tOnMax, double minDur, 
        double maxDur, int minPit,
        int maxPit, Random rand) 
    {
        assert (tOnMax - tOnMin > 1);
        // assert(minDur < 0.5 * (tMax - tMin));
        double tDiff = tOnMax- tOnMin;
        double tOn = rand.nextDouble() * tDiff + tOnMin;
        double dur = rand.nextDouble() * (maxDur - minDur) + minDur;
        int pit = rand.nextInt(maxPit - minPit + 1) + minPit;

        return new Note(new Fraction(tOn, 1000), new Fraction(tOn + dur, 1000), pit);
    }

    
}
