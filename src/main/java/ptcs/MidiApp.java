package ptcs;

import javax.sound.midi.*;
import ptcs.core.Comp;
import ptcs.core.MyMidiNote;
import java.util.Collections;
import java.util.*;

public class MidiApp {

    /**
     * this chooses a midi device Info corresponding to MidiPipe Input 5
     * (note : midi pipe input is an output)
     * then gets MidiDevice. opens it, gets receiver. 
     */
    public static void runTest() {

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info theOne = null;
        for (MidiDevice.Info i: infos) {
            if(i.getName().equals("MidiPipe Input 5")) {
                theOne = i;
            }
        }
        System.out.println(theOne);
        try {
            MidiDevice d = MidiSystem.getMidiDevice(theOne);
            if (!d.isOpen()) {
                d.open();
                Receiver r = d.getReceiver();
                ShortMessage[] msgs = createMsgs();
                for (ShortMessage m: msgs) {
                    // what does -1 mean in send? send now?
                    // why are we sending now? why are we only creating
                    // 1 message? where is sending whole comp? Have
                    // we tested that yet?
                    r.send(m, -1);
                }
                d.close();
            }
        } catch (MidiUnavailableException e) {
            System.out.println("midi unavailable xception");
        }
    }

    public static void runWithComp(Comp c) 
        throws InvalidMidiDataException, InterruptedException {
        MyMidiNote[] notes = c.toMidi2(0);
        List<MyMidiNote> notesL = Arrays.asList(notes);
        Collections.sort(notesL);

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info theOne = null;
        for (MidiDevice.Info i: infos) {
            if(i.getName().equals("MidiPipe Input 3")) {
                theOne = i;
            }
        }
        System.out.println(theOne);
        try {
            MidiDevice d = MidiSystem.getMidiDevice(theOne);
            if (!d.isOpen()) {
                d.open();
                Receiver r = d.getReceiver();
                long currentT = 0;
                for (MyMidiNote n: notesL) {
                    // what does -1 mean in send? send now?
                    // why are we sending now? why are we only creating
                    // 1 message? where is sending whole comp? Have
                    // we tested that yet?
                    long t = n.timestamp;
                    int ms = (int)((double)(t - currentT)/1000.0);
                    if (ms <= 0) {
                        r.send(n.msg, -1);
                    } else {
                        Thread.sleep(ms);
                        r.send(n.msg, -1);
                        currentT = t;
                    }
                }
                d.close();
            }
        } catch (MidiUnavailableException e) {
            System.out.println("midi unavailable xception");
        }
    }

    public static ShortMessage[] createMsgs() {
        ShortMessage[] arr = new ShortMessage[1];
        try {
            arr[0] = new ShortMessage();
            arr[0].setMessage(0x90, 1, 60, 64);
        } catch (InvalidMidiDataException e) {
            System.out.println("invalid midi data exception");
        }
        return arr;
    }
}