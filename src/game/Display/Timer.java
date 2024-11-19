package game.Display;

public class Timer {
    private double lastLoopTime;
    private float timeCount;
    private int fpsCount, upsCount, fps, ups;

    private double getTime() {
        return System.nanoTime() / 1000000000.0;
    }

    public void Init() {
        lastLoopTime = getTime();
    }

    public float getDelta() {
        double time = getTime();
        float delta = (float) (time - lastLoopTime);
        lastLoopTime = time;
        timeCount += delta;
        return delta;
    }

    public void updateFPS() {
        fpsCount++;
    }

    public void updateUPS() {
        upsCount++;
    }

    public void update() {
        if (timeCount > 1f) {
            fps = fpsCount;
            fpsCount = 0;

            ups = upsCount;
            upsCount = 0;

            timeCount -= 1f;
        }
    }

    public int getUPS() {
        return ups > 0 ? ups : upsCount;
    }

    public int getFPS() {
        return fps > 0 ? fps : fpsCount;
    }

    public double getLastLoopTime() {
        return lastLoopTime;
    }
}