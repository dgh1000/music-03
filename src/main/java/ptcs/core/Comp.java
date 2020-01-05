package ptcs.core;

import java.util.*;
import java.util.stream.Collectors;
import javax.sound.midi.*;


/** should we go to processing now. extract notes covering a 
 * certain range of pitch and time.  */
 

public class Comp {
    
    public Set<Note> notes;

    public Comp() {
        notes = new TreeSet<>();
    }

    public Comp(Set<Note> notes) {
        this.notes = notes;
    }

    public boolean containsNote(Note n) {
        return notes.contains(n);
    }

    public void addNote(Note n) {
        /*
        if (notes.contains(n)) {
            throw new Exception("Comp already contains note");
        }
        */
        notes.add(n);
    }

    public Comp extractRange(
        int minPit, int maxPit, double minTime, double maxTime) {
        
        Set<Note> out = new TreeSet<>();
        for (Note n: notes) {
            if (Comp.noteMatchesRange(
                n, minPit, maxPit, minTime, maxTime)) {
                out.add(n);
            }
        }
        return new Comp(out);
    }

    // public Ranges extractRanges() {

    // }

    private static boolean noteMatchesRange(
        //  |          |
        // |   |
        //         |      |
        //     | |
        Note n, int minPit, int maxPit, double minTime, double maxTime) {
        boolean beginInRange = minTime < n.tOn && n.tOn < maxTime;
        boolean endInRange = minTime < n.tOff && n.tOff < maxTime;
        return n.pitch >= minPit && n.pitch <= maxPit
            && (beginInRange || endInRange);
    }

    static public Comp randomComp(
        int nNotes, int minPitch, int maxPitch, double tMin, double tMax,
        Random rand) {

        Comp c = new Comp();
        double minDur = (tMax - tMin)/15.0;
        double maxDur = (tMax - tMin)/4.0;

        for (int i = 0; i < nNotes; i++) {
            Note n = Note.random(
                tMin, tMax, minDur, maxDur, minPitch, maxPitch, rand);
            c.addNote(n);
        }
        return c;
    }

    public String toUsefulString() {
        String s = "";
        for (Note n: notes) {
            s += n.toString() + "\n";
        }
        return s;
    }

    // Determine if the given note overlaps one in the composition
    // and return a list of them.
    protected List<Note> overlappingList(Note n) {
        List<Note> out = new ArrayList<>();
        for (Note p: notes) {
            if (notesOverlap(p, n)) {
                out.add(p);
            }
        }
        return out;
    }

    static protected boolean noteOverlapsSet(Note n, Set<Note> s) {
        for (Note nt: s) {
            if (n.overlaps(nt)) {
                return true;
            }
        }
        return false;
    }

    static protected boolean notesOverlap(Note n1, Note n2) {
        if (n1.pitch != n2.pitch) {
            return false;
        } else if (n1.tOff <= n2.tOn || n2.tOff <= n1.tOn) {
            return false;
        }
        return true;
    }

    static protected Set<Note> removeOverlaps(Note n, Set<Note> s) {
        Set<Note> ns = new TreeSet<>();
        if (s.contains(n)) {
            ns = s.stream().filter(x -> Comp.notesOverlap(n, x)).collect(Collectors.toSet());
            ns.add(n);
            return ns;
        }
        return s;        
    }

    public void elimOlap() {
        List<Note> ns = new ArrayList<>();
        ns.addAll(notes);
        Set<Note> accum = new TreeSet<>();
        for (Note n: ns) {
            if (!Comp.noteOverlapsSet(n, accum)) {
                accum.add(n);
            }
        }
        notes = new TreeSet<>(ns);
    }

    public double tOnMin() {
        double t = Double.POSITIVE_INFINITY;
        for (Note n: notes) {
            if (n.tOn < t)
                t = n.tOn;
        }
        return t;
    }

    public double tOffMax() {
        double t = Double.NEGATIVE_INFINITY;
        for (Note n: notes) {
            if (n.tOff > t)
                t = n.tOff;
        }
        return t;
    }

    public PcsL toPcsL() {
        List<Note> ns = new ArrayList<Note>(notes);
        List<Integer> ls = ns.stream().map(n -> n.pitch).collect(Collectors.toList());
        return new PcsL(ls);
        // return new PcsL(new ArrayList<Note>(notes));
    }

    public   ShortMessage[] toMidi(long offset) throws InvalidMidiDataException {
        int s = notes.size();
        ShortMessage[] msgs = new ShortMessage[2 * s];
        int idx = 0;
        for (Note n: notes) {
            ShortMessage m1 = new ShortMessage();
            m1.setMessage(0x90, 1, n.pitch, 64);
            ShortMessage m2 = new ShortMessage();
            m2.setMessage(0x80, 1, n.pitch, 64);
            // make note on and note off
            msgs[idx++] = m1;
            msgs[idx++] = m2;
        }
        return msgs;
    }

    // how should I test this?
    public MyMidiNote[] toMidi2(long offset) throws InvalidMidiDataException {
        int s = notes.size();
        MyMidiNote[] msgs = new MyMidiNote[2 * s];
        int idx = 0;
        for (Note n: notes) {
            long[] ts = n.onOffTimestamps(offset);
            ShortMessage m1 = new ShortMessage();
            m1.setMessage(0x90, 1, n.pitch, 64);
            MyMidiNote n1 = new MyMidiNote(m1, ts[0]);
            ShortMessage m2 = new ShortMessage();
            m2.setMessage(0x80, 1, n.pitch, 64);
            MyMidiNote n2 = new MyMidiNote(m2, ts[1]);
            // make note on and note off
            msgs[idx++] = n1;
            msgs[idx++] = n2;
        }
        return msgs;
    }
}