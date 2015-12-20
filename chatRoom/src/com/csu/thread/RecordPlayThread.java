package com.csu.thread;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.util.Log;
import com.csu.constant.ContentFlag;
import com.csu.tool.SystemConstant;

public class RecordPlayThread extends Thread{
	private int bufferSize;
	private AudioTrack track;
	private String path;
	private boolean runFlag = false;
	public RecordPlayThread() {
		bufferSize = AudioRecord.getMinBufferSize(
				SystemConstant.SAMPLE_RATE_IN_HZ,
				SystemConstant.CHANNEL_CONFIG,
				SystemConstant.AUDIO_FORMAT);
		track = new AudioTrack(AudioManager.STREAM_MUSIC,
				SystemConstant.SAMPLE_RATE_IN_HZ,
				SystemConstant.CHANNEL_CONFIG,
				SystemConstant.AUDIO_FORMAT, bufferSize*2,
				AudioTrack.MODE_STREAM);
	}
	public void run() {
		try {
			BufferedInputStream dis = new BufferedInputStream(new FileInputStream(path));
			byte[] buffer = new byte[bufferSize];
			int length = 0;
			// 开始播放
			track.play();
			while ((length = dis.read(buffer)) != -1 && runFlag) {
				track.write(buffer, 0, length);
			}
			track.stop();
			track.release();
			track = null;
			Log.i(ContentFlag.TAG, "play is over");
			sleep(1000);
			runFlag = false;
			Log.i(ContentFlag.TAG, "thread is over");
			dis.close();
		} catch (Exception e) {
			Log.i(ContentFlag.TAG, "thread is closed");
			e.printStackTrace();
		}
	
	}
	public void setPath(String path) {
		this.path = path;
	}
	public boolean isRunFlag() {
		return runFlag;
	}
	public void setRunFlag(boolean runFlag) {
		this.runFlag = runFlag;
	}
}
