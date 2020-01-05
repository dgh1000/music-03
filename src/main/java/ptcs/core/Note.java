package ptcs.core;

import java.util.*;

public class Note implements Comparable<Note> {
    public double tOn;
    public double tOff;
    public int pitch;
    //private boolean hasBeenEliminated;
    
    public Note(double tOn, double tOff, int pitch) {
        this.tOn = tOn;
        this.tOff = tOff;
        this.pitch = pitch;
        //hasBeenEliminated = false;
    }

    static public Note random(
        double tOnMin, double tOnMax, double minDur, double maxDur,
        int minPit, int maxPit, Random rand) {
            
        assert(tOnMax - tOnMin > 1);
        // assert(minDur < 0.5 * (tMax - tMin));
        double tDiff = tOnMax - tOnMin;
        double tOn = rand.nextDouble() * tDiff + tOnMin;
        double dur = rand.nextDouble() * (maxDur - minDur) + minDur;
        int pit = rand.nextInt(maxPit - minPit +1) + minPit;
        
        return new Note(tOn, tOn+dur, pit);
    }

    public boolean overlaps(Note other) {
        return ! (other.tOff <= this.tOn || this.tOff < other.tOn);
    }

    public long[] onOffTimestamps(long offset) {
        long[] out = new long[2];
        out[0] = (long)(tOn * 1000000) + offset;
        out[1] = (long)(tOff * 1000000) + offset;
        return out;
    }

    @Override
    public int compareTo(Note other) {
        if (this.tOn != other.tOn) {
            return Double.compare(this.tOn, other.tOn);
        } else if (this.tOff != other.tOff) {
            return Double.compare(this.tOff, other.tOff);
        } else {
            return Integer.compare(this.pitch, other.pitch);
        }
    }

    @Override
    public String toString() {
        return String.format("on: %8.3f off: %8.3f pit:%d", tOn, tOff, pitch);
    }
}