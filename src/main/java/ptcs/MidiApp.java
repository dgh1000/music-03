package ptcs;

import java.util.*;
import ptcs.core.*;
import ptcs.midi.*;
import ptcs.gen.RandomNotes;
import ptcs.midi.MidiInterface;

import org.apache.commons.math3.fraction.*;

public class MidiApp {


    public static void test2() throws Exception
    {
        Map<String, String> env = System.getenv();
        String midiInputName = env.get("MMIDI");
        if (midiInputName == null)
            throw new Exception("the MMIDI env variable isn't defined");
        List<Integer> pitches = 
            new ArrayList<>(Arrays.asList(60, 64, 67, 72, 76, 79, 84, 88, 91, 96));
        List<Note> notes = RandomNotes.genRandomNotes(1, 200, 300, pitches);
        MidiInterface.openMidiDevice(midiInputName);
        MidiInterface.playNotes(notes, new ArrayList<Raw>(), 1000);
        MidiInterface.closeMidiDevice();

    }

   
}