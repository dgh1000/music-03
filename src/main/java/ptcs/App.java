package ptcs;
import ptcs.core.*;
import ptcs.pcs.*;
import java.util.*;
import ptcs.gen.*;
import java.util.Map.*;
import java.util.AbstractMap.*;
import java.awt.Color;
import org.apache.commons.math3.fraction.Fraction;
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
 * 
 *   We're not going to analyze comp any more. We're going to have rhythm generator, 
 *   pitch generator, etc. all put together. interfaces
 * 
 *  Generate... comp? or generate list of pitches. is there anything a comp needs
 *  besides notes? 
 * 
 * So there's a generator function Beat -> a, which can be used for dynamics, 
 * selection of instruments. One of them is Beat -> next beat. measure map not 
 * needed initially.
 * 
 */
public class App 
{
    public static void main(String[] args) throws Exception {
        // Fraction f = new Fraction(1, 3);
        // Timing t = new Timing(new Fraction(1,3), new Fraction(4, 3));
        // Fraction f2 = new Fraction(10);
        // System.out.println(f.add(f2));
        MidiApp.test2();
    }

    public static void testCompPlay() throws Exception
    {
        Random rand = new Random();
        Comp c = RandomComp.randomComp(
            10, 20, 90, 0, 10 * 1000, rand
        );

        System.out.println(c.toUsefulString());
        // MidiApp.runWithComp(c);
        // TestPcs.testAnalyze();
        System.exit(0);
    }

    public static void testGraphics() {
        // testNormTable();
        System.out.println("can be Swing app or text test");
        // MyTest.testRandomComp();
        MyPanel p = GsApp.createAndShowGUI();
        Random rand = new Random(100);
        Comp c = RandomComp.randomComp(
            10, 30, 70, 0, 10 * 1000, rand
        );
        double rangeT0 = 0;
        double rangeT1 = 5;
        /*
        Comp c2 = c.extractRange(
            50, 70, new Fraction(rangeT0, 0.001, 100), new Fraction(rangeT1, 0.001, 100)
        );
        List<Entry<Comp,Color>> ls = new ArrayList<>();
        ls.add(new SimpleEntry<>(c, Color.red));
        ls.add(new SimpleEntry<>(c2, Color.black));
        p.setCompList(ls, -1, 11);
        */
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
