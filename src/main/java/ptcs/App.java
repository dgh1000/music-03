package ptcs;
import ptcs.core.*;
import java.util.*;
import java.util.Map.*;
import java.util.AbstractMap.*;
import java.awt.Color;
/**
 * Hello world!
 * What now? Analyze a passage? export passage as MIDI? read a passage?
 * Well we need to generate passages to audition. Maybe compose a passage,
 * export as MIDI file? And export generated passages as MIDI
 * 
 * okay where to now. have we finished analayzing comp yet? have we tested that?
 * let'stest agai
 * 
 * NOTES ON ALL APPS AND TESTS AVAILABLE NOW
 *   App
 *     several test functions for Library
 *     testing playing a composition in midi
 *   MidiApp
 *     function that plays one MIDI note
 *     function that plays an entire composition
 *   GsApp
 *     simple GUI for displaying compositions
 *   MyTest
 *     various tests (PCs, random compositions)
 *   TestPcs
 *     test analysis
 */
public class App 
{
    public static void main(String[] args) throws Exception {
        testCompPlay();
    }

    public static void testCompPlay() throws Exception
    {
        Random rand = new Random();
        Comp c = Comp.randomComp(10, 20, 90, 0.0, 10.0, rand);
        System.out.println(c.toUsefulString());
        MidiApp.runWithComp(c);
        // TestPcs.testAnalyze();
        System.exit(0);
    }

    public static void testGraphics() {
        // testNormTable();
        System.out.println("can be Swing app or text test");
        // MyTest.testRandomComp();
        MyPanel p = GsApp.createAndShowGUI();
        Random rand = new Random(100);
        Comp c = Comp.randomComp(10, 30, 70, 0, 10, rand);
        double rangeT0 = 0;
        double rangeT1 = 5;
        Comp c2 = c.extractRange(50, 70, rangeT0, rangeT1);
        List<Entry<Comp,Color>> ls = new ArrayList<>();
        ls.add(new SimpleEntry<>(c, Color.red));
        ls.add(new SimpleEntry<>(c2, Color.black));
        p.setCompList(ls, -1, 11);
    }

    public static void testLibrary()  throws Exception {
        // PcsLibrary lib = new PcsLibrary(5);
        // System.out.println(lib);
        // Random rand = new Random();
        // PcsL r = PcsL.random(4, rand);
        // PcsI ri = new PcsI(r);
        // PcsI ni = ri.normalize();
        // System.out.println("Random PcsL: " + r);
        // System.out.println("To PcsI" + ri);
        // System.out.println("normalized: " + ni);
        // PcsI lookedUp = lib.lookup(ni);
        // System.out.println("looked up: " + lookedUp);
        TestPcs.testAnalyze();
    }

    public static void testLibrary2() throws Exception {
        PcsLibrary lib = new PcsLibrary(5);
        System.out.println(lib);
        Random rand = new Random();
        PcsL r = PcsL.random(3, rand);
        System.out.println(lib.containsKey(new PcsI(r)));
    }

    public static void testLib3() {
        Set<PcsI> s = PcsLibrary.createTestSet(6);
        Random rand = new Random();
        for (int i = 0; i < 100; i++) {
            PcsL r = PcsL.random(6, rand);
            System.out.println(s.contains(new PcsI(r)));
        }
    }
}
