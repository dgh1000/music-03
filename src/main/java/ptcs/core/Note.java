package ptcs.core;

import org.apache.commons.math3.fraction.*;

public class Note implements Comparable<Note> {
    public Fraction tOn;
    public Fraction tOff;
    public int pitch;
    public String instr;
    //private boolean hasBeenEliminated;
    
    public Note(Fraction tOn, Fraction tOff, int pitch) {
        this.tOn = tOn;
        this.tOff = tOff;
        this.pitch = pitch;
        //hasBeenEliminated = false;
    }

    public boolean overlaps(Note other) {
        int c1 = other.tOff.compareTo(this.tOn);
        int c2 = this.tOff.compareTo(other.tOn);
        return ! (c1 <= 0 || c2 <= 0);
    }

    public long[] onOffTimestamps(long offset) {
        long[] out = new long[2];
        out[0] = (long)(tOn.doubleValue() * 1000000) + offset;
        out[1] = (long)(tOff.doubleValue() * 1000000) + offset;
        return out;
    }

    @Override
    public int compareTo(Note other) {
        if (!this.tOn.equals(tOn)) {
            return this.tOn.compareTo(other.tOn);
        } else if (!this.tOff.equals(tOff)) {
            return this.tOff.compareTo(other.tOff);
        } else {
            return Integer.compare(this.pitch, other.pitch);
        }
    }

    @Override
    public String toString() {
        return String.format("on: %8.3f off: %8.3f pit:%d", tOn, tOff, pitch);
    }
}