package ptcs.pcs;
public class PcsScore {

    private PcsI normPcs;
    private int nRepetitions;

    public PcsScore(PcsI normPcs, int nRepetitions) {
        this.normPcs = normPcs;
        this.nRepetitions = nRepetitions;
    }

    public PcsI getNormPcs() {
        return normPcs;
    }

    public int getNRepetitions() {
        return nRepetitions;
    }
}