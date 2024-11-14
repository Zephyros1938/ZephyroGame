package src.lib.TimeStamp;

public class TimeStamp {

    private long Time;
    private String Task;

    public void Start(String tsk) {
        Time = System.currentTimeMillis();
        Task = tsk;
    }

    public void End() {
        System.out.println("Completed " + Task + " In " + (System.currentTimeMillis() - Time) + "ms");
    }
}