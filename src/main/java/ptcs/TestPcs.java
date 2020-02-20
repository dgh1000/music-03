package ptcs;

import java.util.*;
import ptcs.core.*;
import ptcs.pcs.*;
import ptcs.util.*;
import java.util.stream.*;
import org.apache.commons.math3.fraction.*;

public class TestPcs {
    public static void testAnalyze() {
        // okay so here we have to create a comp with certain
        // pitches. maybe take 
        int[] ps = {0, 1, 3, 6, 7};
        List<Note> ls = Arrays.stream(ps)
            .mapToObj(i -> new Note(new Fraction(0), new Fraction(1), i))
            .collect(Collectors.toList());
        Comp c = new Comp(ls);
        PcsLibrary pl = new PcsLibrary(6);
        Map<PcsI,Double> m = AnalyzeComp.analyze(c, pl);
        // oh yeah we can't have static 
        FormatMap<PcsI,Double> fm = new FormatMap<>();
        fm.writeMap(m);
    }

    public static Note mkTestNote(int pitch) {
        return new Note(new Fraction(0), new Fraction(1), pitch);
    }
}