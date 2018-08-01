
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.util.Log;

import java.io.File;
import java.io.IOException;

/**
 * 录音(aac)
 */
public class AudioRecordHelper {

    private static final String LOG_TAG = "AudioRecordHelper";

    private static final int MAX_VU_SIZE = 100;

    private File mFile;

    private MediaRecorder mRecorder = null;

    public AudioRecordHelper(@NonNull File file) {
        mFile = file;
    }

    /**
     * 开始录音
     */
    public void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFile(mFile.getPath());
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.AAC_ADTS);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    /**
     * 暂停录音
     */
    public synchronized void stopRecording() {
        if (mRecorder != null){
            mRecorder.stop();
            mRecorder.release();
            mRecorder = null;
        }
    }

    /**
     * 获取音量等级（0~100）
     */
    public int getMaxVuSize(){
        if (mRecorder != null) {
            return MAX_VU_SIZE * mRecorder.getMaxAmplitude() / 32768;
        }
        return 0;
    }

    /**
     * 获取音频文件
     */
    public File getFile(){
        return mFile;
    }

}
