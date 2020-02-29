package ptcs.core;

import javax.sound.midi.*;

public class Raw implements Comparable<Raw> {
    public ShortMessage msg;
    public int timestamp;
    public Raw(ShortMessage msg, int timestamp) {
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

    @Override
    public String toString() 
    {
        return String.valueOf(timestamp);
    }
}