package lib.TimeStamp;

public class TimeStamp {

    private long Time;
    private String Task;

    public void Start(String tsk) {
        this.Time = System.currentTimeMillis();
        this.Task = tsk;
    }

    public void End() {
        System.out.println("Completed " + Task + " In " + (System.currentTimeMillis() - Time) + "ms");
        Time = 0;
        Task = new String();
    }
}