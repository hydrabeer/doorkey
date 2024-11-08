package password_validation.entropy;

public class StatisticalEntropyCheck implements EntropyCheck {

    private double entropyThreshold;

    public StatisticalEntropyCheck(double entropyThreshold) {
        this.entropyThreshold = entropyThreshold;
    }

    @Override
    public double calculateEntropy(String password) {
        // Incomplete
        return 0;
    }
}
