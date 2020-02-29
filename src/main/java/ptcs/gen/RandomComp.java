package ptcs.gen;
import ptcs.core.*;
import java.util.*;
import org.apache.commons.math3.fraction.Fraction;

public class RandomComp {
    static public Comp randomComp(
        int nNotes, int minPitch, int maxPitch, 
        int tMin, int tMax, Random rand) 
    {

        Comp c = new Comp();
        int diff = tMax - tMin;
        int minDur = diff / 15;
        int maxDur = diff / 4;

        for (int i = 0; i < nNotes; i++) {
            Note n = randomNote(
                tMin, tMax, minDur, maxDur, minPitch, maxPitch, rand);
            c.addNote(n);
        }
        return c;
    
    }

    static public Note randomNote(
        int tOnMin, int tOnMax, int minDur, 
        int maxDur, int minPit,
        int maxPit, Random rand) 
    {
        assert (tOnMax - tOnMin > 1);
        // assert(minDur < 0.5 * (tMax - tMin));
        int tDiff = tOnMax- tOnMin;
        int tOn = rand.nextInt() * tDiff + tOnMin;
        int dur = rand.nextInt() * (maxDur - minDur) + minDur;
        int pit = rand.nextInt(maxPit - minPit + 1) + minPit;

        return new Note(tOn, tOn + dur, pit);
    }

    
}
