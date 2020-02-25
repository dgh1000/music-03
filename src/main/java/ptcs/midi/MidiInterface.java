
package ptcs.midi;

import java.util.*;
import javax.sound.midi.*;
import ptcs.core.*;

public class MidiInterface 
{
    static MidiDevice midiDevice;

    static public void openMidiDevice(String midiInputName) throws Exception
    {

        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        MidiDevice.Info theOne = null;
        for (MidiDevice.Info i : infos) {
            if (i.getName().equals(midiInputName)) {
                theOne = i;
            }
        }
        if (theOne == null)
            throw new Exception("No such input MIDI device:" + midiInputName);
        try {
            midiDevice = MidiSystem.getMidiDevice(theOne);
            if (!midiDevice.isOpen())
                midiDevice.open();
            else
                System.out.println("Strange, MIDI device was already open");
            
        } catch (MidiUnavailableException e) {
            System.out.println("midi unavailable exception");
        }

    }

    static public void closeMidiDevice() 
    {
        midiDevice.close();
    }

    static public void playNotes(List<Note> notes, List<Raw> other, long offset) 
    {
        List<Raw> notesL = new ArrayList<>();

        for (Note n: notes) 
        {
            Raw[] mns = n.toRaw(offset);
            notesL.add(mns[0]);
            notesL.add(mns[1]);
        }
        notesL.addAll(other);
        Collections.sort(notesL);

        try {
            Receiver r = midiDevice.getReceiver();
            long currentT = 0;
            for (Raw n : notesL) 
            {
                // what does -1 mean in send? send now?
                // why are we sending now? why are we only creating
                // 1 message? where is sending whole comp? Have
                // we tested that yet?
                long t = n.timestamp;
                int ms = (int) ((double) (t - currentT) / 1000.0);
                if (ms <= 0) {
                    r.send(n.msg, -1);
                } else {
                    Thread.sleep(ms);
                    r.send(n.msg, -1);
                    currentT = t;
                }
            }
        } catch (MidiUnavailableException e) 
        {
            System.out.println("Couldn't play notes due to MidiUnavailableException");
        } catch (InterruptedException e) 
        {
            System.out.println("Playback interrupted");
        }
    }
    static public void inspectDevices() 
    {
        System.out.println("Inspect the existing MIDI interfaces:");
        MidiDevice.Info[] infos = MidiSystem.getMidiDeviceInfo();
        for (MidiDevice.Info i : infos) {
            System.out.println(i.getName());

        }
    }
}