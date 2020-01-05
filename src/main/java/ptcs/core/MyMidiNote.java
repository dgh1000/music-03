package ptcs.core;

import javax.sound.midi.*;

public class MyMidiNote implements Comparable<MyMidiNote> {
    public ShortMessage msg;
    public long timestamp;
    public MyMidiNote(ShortMessage msg, long timestamp) {
        this.msg = msg;
        this.timestamp = timestamp;
    }

    @Override
    public int compareTo(MyMidiNote other) {
        if (this.timestamp < other.timestamp) {
            return -1;
        } else if (this.timestamp > other.timestamp) {
            return 1;
        } else {
            return 0;
        }
    }
}