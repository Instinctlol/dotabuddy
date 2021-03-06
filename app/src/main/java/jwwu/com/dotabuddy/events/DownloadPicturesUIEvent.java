package jwwu.com.dotabuddy.events;

/**
 * Created by Instinctlol on 20.03.2016.
 */
public class DownloadPicturesUIEvent {
    public final int progress, total;
    public final String message;
    public final boolean isError, isFinished;

    public DownloadPicturesUIEvent(int progress, int total, String message, boolean isError) {
        this.progress = progress;
        this.total = total;
        this.message=message;
        this.isError = isError;
        this.isFinished = total == progress;
    }
}
