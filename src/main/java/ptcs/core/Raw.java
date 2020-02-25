package ptcs.core;

import javax.sound.midi.*;

public class Raw implements Comparable<Raw> {
    public ShortMessage msg;
    public long timestamp;
    public Raw(ShortMessage msg, long timestamp) {
        this.msg = msg;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(Raw other) {
        if (this.timestamp < other.timestamp) {
            return -1;
        } else if (this.timestamp > other.timestamp) {
            return 1;
        } else {
            return 0;
        }
    }
}