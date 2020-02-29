package ptcs.core;

import javax.sound.midi.*;

public class Note implements Comparable<Note> {
    public int tOn;
    public int tOff;
    public int pitch;
    public String instr;
    //private boolean hasBeenEliminated;
    
    public Note(int tOn, int tOff, int pitch) {
        this.tOn = tOn;
        this.tOff = tOff;
        this.pitch = pitch;
        //hasBeenEliminated = false;
    }

    public boolean overlaps(Note other) {
        return ! (other.tOff <= this.tOn || this.tOff <= other.tOn);
    }

    public int[] onOffTimestamps(int offset) {
        int[] out = new int[2];
        out[0] = tOn + offset;
        out[1] = tOff + offset;
        return out;
    }

    @Override
    public int compareTo(Note other) {
        return Integer.compare(this.tOn, other.tOn);
    }

    public Raw[] toRaw(int offset) 
    {
        int idx = 0;
        Raw[] msgs = new Raw[2];

        try 
        {
            int[] ts = onOffTimestamps(offset);
            ShortMessage m1 = new ShortMessage();
            m1.setMessage(0x90, 1, this.pitch, 64);
            Raw n1 = new Raw(m1, ts[0]);
            ShortMessage m2 = new ShortMessage();
            m2.setMessage(0x80, 1, this.pitch, 64);
            Raw n2 = new Raw(m2, ts[1]);
            // make note on and note off
            msgs[idx++] = n1;
            msgs[idx++] = n2;

        } catch(InvalidMidiDataException e)
        {
            System.out.println("Strange, InvalidMidiException");
        }

        return msgs;
    }

    @Override
    public String toString() {
        return String.format("on: %8.3f off: %8.3f pit:%d", tOn/1000, tOff/1000, pitch);
    }

}