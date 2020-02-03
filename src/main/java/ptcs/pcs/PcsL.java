package ptcs.pcs;

import java.util.*;
import java.util.stream.Collectors;
// Pitch class set that maintains a list of pitches (internally, a set).
// Can be constructed from int[] or List<Integer>. Cannot be modified
// once constructed. Input values are taken mod 12.
public class PcsL {

    protected Set<Integer> pcs;

    public PcsL(int[] pitchClasses) {
        pcs = new TreeSet<>();
        for (int p: pitchClasses) {
            pcs.add(p % 12);
        }
    }

    public PcsL(List<Integer> pitches) {
        pcs = new TreeSet<>();
        for (int p: pitches) {
            pcs.add(p % 12);
        }
    }

    Set<Integer> getPcs() {
        return pcs;
    }

    static public List<PcsL> genAllWithN(int n) {
        List<List<Integer>> l = genAllHelp(n, -1, 0, new ArrayList<Integer>());
        return l.stream().map(x -> new PcsL(x)).collect(Collectors.toList());
    }

    // method name: genAllHelp
    //
    // description: Generate all Pcsl with n bits, given partial Pcsl 
    //              with ni bits, with current maximum at m.
    static List<List<Integer>> genAllHelp(
        int n, int m, int ni, List<Integer> in) {

        List<List<Integer>> accum = new ArrayList<List<Integer>>();
        for (int i = m+1; i < 12; ++i) {
            List<Integer> copy = new ArrayList<Integer>(in);
            copy.add(i);
            if (ni == n - 1) {
                accum.add(copy);
            } else {
                accum.addAll(genAllHelp(n, i, ni+1, copy));
            }
        }
        return accum;
    }

    static public PcsL random(int n, Random r) {
        List<Integer> l = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            l.add(r.nextInt(12));
        }
        return new PcsL(l);
    }

    @Override
    public String toString() {
        return "PcsL: " + pcs.toString();
    }
}