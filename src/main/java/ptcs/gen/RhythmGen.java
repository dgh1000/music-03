package ptcs.gen;

import ptcs.core.*;

public interface RhythmGen {
    double next(MeasureMap map, Beat currentBeat);
}