package service.copy_credentials;

/**
 * TimeInputData used to pass information from controller to interactor.
 */
public class TimeInputData {
    private int time;

    public TimeInputData(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
