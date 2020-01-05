package ptcs;

import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

import ptcs.core.*;
import ptcs.util.*;

public class MyTest {


    static public void testNumberSets() {
        List<Integer> items = Arrays.asList(1, 2, 3, 4, 5, 6);
        List<List<Integer>> result = NumberSets.allSubsets(items);
        for (List<Integer> r: result) {
            System.out.println(r);
        }

    }

    static public void testRandomComp() {
        Random r = new Random();
        Comp c2 = Comp.randomComp(10, 30, 80, 0, 10, r);
        System.out.println(c2.toUsefulString());
    }

    static public void testGenAll() throws IOException {
        List<PcsL> p = PcsL.genAllWithN(3
        );
        Path path = Paths.get("out.txt");
        String accum = "";
        for (PcsL x : p) {
            String s = x.toString();
            accum += s + "\n";
        }
        byte[] bytes = accum.getBytes();
        Files.write(path, bytes);
    }

    static public void testPcsL() {
        int[] pitches = { 0, 2, 5 };
        PcsL p1 = new PcsL(pitches);
        System.out.println(p1);
    }

    static public void testPcsI() {
        int[] pitches = { 14, 15, 18, 14 };
        PcsL p1 = new PcsL(pitches);
        System.out.println(p1);
        PcsI p2 = new PcsI(p1);
        PcsL p3 = p2.toPcsL();
        System.out.println(p3);
    }

    public void testNormTable() throws Exception {
        NormTable t = new NormTable(1, 5);
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            PcsI p = new PcsI(PcsL.random(4, r));
            System.out.println(p);
            System.out.println(t.lookupNorm(p));
        }
    } 
           
    static void testRotate() {
        int[] pitches = { 0, 1, 3 };
        PcsL p1 = new PcsL(pitches);
        PcsI p2 = new PcsI(p1);
        List<PcsI> p3 = p2.rotateAll();
        List<PcsL> p4 = p3.stream().map(x -> x.toPcsL()).collect(Collectors.toList());
        System.out.println(p4);
    }
}