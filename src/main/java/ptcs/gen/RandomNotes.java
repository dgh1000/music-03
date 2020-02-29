package ptcs.gen;

import java.util.*;
import ptcs.core.*;

import org.apache.commons.math3.fraction.*;

public class RandomNotes
{
    public static List<Note> genRandomNotes(
        int n, int span, int dur, List<Integer> pitches)
    {
        List<Note> out = new ArrayList<>();
        Random rand = new Random();
        int size = pitches.size();
        int t = 0;
        // int lastPitch = 0;
        for (int i = 0; i < n; i++) 
        {
            int nextPitch = pitches.get(rand.nextInt(size));
            // while (nextPitch == lastPitch)
            // {
            //    nextPitch = pitches.get(rand.nextInt(size));
            // }

            out.add(
                new Note(t, t + dur, nextPitch)
            );
            // lastPitch = nextPitch;
            t += span;
        }
        return out;
    }
    //   |------|
    //            |-------|
    //                       |---------|
    public static List<Note> gen02(
        int n, double span, double dur, List<Integer> pitchesSource
    )
    {
        return new ArrayList<>();
    }
}