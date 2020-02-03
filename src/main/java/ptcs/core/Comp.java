package ptcs.core;

import ptcs.core.*;
import ptcs.pcs.*;
import java.util.*;
import java.util.stream.Collectors;
import javax.sound.midi.*;

import org.apache.commons.math3.fraction.Fraction;


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
        int minPit, int maxPit, Fraction minTime, Fraction maxTime) 
    {    
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
        Note n, int minPit, int maxPit, Fraction minTime, Fraction maxTime) 
    {
        int c1 = minTime.compareTo(n.tOn);
        int c2 = n.tOn.compareTo(maxTime);
        int c3 = minTime.compareTo(n.tOff);
        int c4 = n.tOff.compareTo(maxTime);
        boolean beginInRange = c1 < 0 && c2 < 0;
        boolean endInRange = c3 < 0 && c4 < 0;
        return n.pitch >= minPit && n.pitch <= maxPit
            && (beginInRange || endInRange);
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
        if (n1.pitch != n2.pitch) 
        {
            return false;
        } else if (n1.tOff.compareTo(n1.tOn) <= 0 || n2.tOff.compareTo(n1.tOn) <= 0) 
        {
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

    public Fraction tOnMin() {
        List<Note> ns = new ArrayList<>(notes);
        assert (ns.size() > 0);
        Fraction t = ns.get(0).tOn;
        for (int i = 1; i < ns.size(); i++) 
        {
            Note nt = ns.get(i);
            if (nt.tOn.compareTo(t) < 0)
                t = nt.tOn;
        }
        return t;
    }

    public Fraction tOffMax() {
        List<Note> ns = new ArrayList<>(notes);
        assert (ns.size() > 0);
        Fraction t = ns.get(0).tOff;
        for (int i = 1; i < ns.size(); i++) {
            Note nt = ns.get(i);
            if (nt.tOff.compareTo(t) > 0)
                t = nt.tOff;
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