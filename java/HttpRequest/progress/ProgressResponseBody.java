package httprequest.progress;


import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.*;

import java.io.IOException;

/**
 * 用于实现下载进度的拦截
 */
public class ProgressResponseBody extends ResponseBody {

    private final ResponseBody responseBody;
    private final DownloadProgressListener progressListener;
    private BufferedSource bufferedSource;

    public ProgressResponseBody(ResponseBody responseBody, DownloadProgressListener progressListener) {
        this.responseBody = responseBody;
        this.progressListener = progressListener;
    }

    @Override public MediaType contentType() {
        return responseBody.contentType();
    }

    @Override public long contentLength(){
        return responseBody.contentLength();
    }

    @Override public BufferedSource source(){
        if (bufferedSource == null) {
            bufferedSource = Okio.buffer(source(responseBody.source()));
        }
        return bufferedSource;
    }

    private Source source(Source source) {
        return new ForwardingSource(source) {
            long totalBytesRead = 0L;
            @Override public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                // read() returns the number of bytes read, or -1 if this source is exhausted.
                totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                progressListener.onDownloadProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                return bytesRead;
            }
        };
    }

    public interface DownloadProgressListener {
        void onDownloadProgress(long bytesRead, long contentLength, boolean done);
    }
}




