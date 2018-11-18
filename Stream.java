package chess;

public class Stream {
    public long startTime;
    public long endTime;
    public long thisTime;

    public long getDuration() {
        return endTime - startTime;
    }
    // I  would add
    public void start() {
        startTime = System.currentTimeMillis();
    }
    public void stop() {
         endTime = System.currentTimeMillis();
     }
    
    public long current() {
    	thisTime = System.currentTimeMillis();
    	return thisTime - startTime;
    }
}
