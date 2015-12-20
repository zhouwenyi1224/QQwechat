package com.csu.service;

import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import com.csu.chatroom.R;
import com.csu.thread.RecordPlayThread;

public class RecordPlayService {
		private AnimationDrawable animation = null;
		private ImageView ivPlay = null;
		private int type;
		private RecordPlayThread thread;
		//开始播放
		public void play(String path, AnimationDrawable animation, ImageView ivPlay, int type) {
			if(null!= thread && thread.isRunFlag()){
				thread.setRunFlag(false);
				stopAnimatin();
			}
			this.animation = animation;
			this.ivPlay = ivPlay;
			this.type = type;
			thread = new RecordPlayThread();
			animation.start();
			thread.setPath(path);
			thread.setRunFlag(true);
			thread.start();
		}
		//停止播放
		public void stop(){
			if(thread.isAlive()){
				thread.setRunFlag(false);
				stopAnimatin();
			}
		}
		//停止动画
		public void stopAnimatin(){
			if(type == 0){
				ivPlay.setBackgroundResource(R.drawable.chatto_voice_playing);
			}else{
				ivPlay.setBackgroundResource(R.drawable.chatfrom_voice_playing);
			}
			if(null!= animation){
				animation.stop();
				animation = null;
			}
		}
		
		public boolean ifThreadRun(){
			return thread.isRunFlag();
		}
}
