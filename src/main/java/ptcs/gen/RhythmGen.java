package ptcs.core;

public interface RhythmGen {
    double next(TimeFramework frameWork, Beat currentBeat);
}