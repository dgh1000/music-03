package ptcs.util;

import java.util.*;

public class FormatMap<K,V> {

    public void writeMap(Map<K,V> m) {
        Set<K> ks = m.keySet();
        for (K k: ks) {
            System.out.println(k + "            " + m.get(k));

        }
    }
}
