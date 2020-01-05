package ptcs.core;

import java.util.*;
import java.lang.Integer;

public class PcsI implements Comparable<PcsI> {
    private int bits;

    public PcsI(PcsI in) {
        this.bits = in.bits;
    }

    public PcsI(PcsL p) {
        bits = PcsI.integersToBits(p.getPcs());
    }

    public PcsI(List<Integer> p) {
        PcsL pl = new PcsL(p);
        bits = PcsI.integersToBits(pl.getPcs());
    }

    public PcsI(int bs) {
        bits = bs;
    }

    static int integersToBits(Set<Integer> ints) {
        int accum = 0;
        for (int n : ints) {
            accum |= 1 << n;
        }
        return accum;
    }

    public PcsL toPcsL() {
        ArrayList<Integer> pitches = new ArrayList<>();
        for (int i = 0; i < 12; ++i) {
            int n = 1 << i;
            if ((n & bits) > 0) {
                pitches.add(i);
            }
        }
        return new PcsL(pitches);
    }

    /**
     * Returns 
     */
    PcsI rotateLeft() {
        int bitsR = bits << 1;
        int bitsR1;
        if ((0x1000 & bitsR) > 0) {
            bitsR1 = (bitsR & 0xfff) | 0x01;
        } else {
            bitsR1 = bitsR & 0xfff;
        }
        return new PcsI(bitsR1);
    }

    public List<PcsI> rotateAll() {
        PcsI buffer = new PcsI(this);
        List<PcsI> out = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            out.add(buffer);
            buffer = buffer.rotateLeft();
        }
        return out;
    }

    public PcsI normalize() {
        List<PcsI> l = this.rotateAll();
        return PcsI.minimumOfList(l);
    }

    private static PcsI minimumOfList(List<PcsI> l) {
        Optional<PcsI> p = l.stream()
            .sorted((x, y) -> Integer.compare(x.bits, y.bits)).findFirst();
        return p.get();
    }

    @Override
    public int compareTo(PcsI other) {
        return Integer.compare(this.bits, other.bits);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PcsI))
            return false;
        PcsI p = (PcsI)o;
        return this.bits == p.bits;
    }

    @Override
    public String toString() {
        PcsL l = this.toPcsL();
        return "PcsI: " + l.toString();
    }
}
