package com.cz.wisdomcity.entity;

import java.io.InputStream;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceView;
/**
 * 测试用 已无用
 * @author Dean
 *
 */
public class ClsWaveDiagram {

	private boolean isRecording = false;// 线程控制标记
	private InputStream btInput = null;// 蓝牙数据输入流
	/**
	 * X轴缩小的比例
	 */
	public int rateX = 1; 
	/**
	 * Y轴缩小的比例
	 */
	public int rateY = 1;
	/**
	 * Y轴基线
	 */
	public int baseLine = 0;

	/**
	 * 初始化
	 */
	public ClsWaveDiagram(InputStream btInput, int rateX, int rateY,
			int baseLine) {
		this.btInput = btInput;
		this.rateX = rateX;
		this.rateY = rateY;
		this.baseLine = baseLine;
	}

	/**
	 * 开始
	 * 
	 * @param recBufSize
	 *            AudioRecord的MinBufferSize
	 */
	public void Start(SurfaceView sfv, Paint mPaint, int wait) {
		isRecording = true;
		new DrawThread(sfv, mPaint, wait).start();// 开始绘制线程
	}

	/**
	 * 停止
	 */
	public void Stop() {
		isRecording = false;
	}

	/**
	 * 负责绘制inBuf中的数据
	 * 
	 * @author GV
	 * 
	 */
	class DrawThread extends Thread {

		private int oldX = 0;// 上次绘制的X坐标
		private int oldY = 0;// 上次绘制的Y坐标
		private SurfaceView sfv;// 画板
		private int X_index = 0;// 当前画图所在屏幕X轴的坐标
		private Paint mPaint;// 画笔
		private int wait = 50;// 线程等待时间

		public DrawThread(SurfaceView sfv, Paint mPaint, int wait) {
			this.sfv = sfv;
			this.mPaint = mPaint;
			this.wait = wait;
		}

		@Override
		public void run() {
			while (isRecording) {
				try {

					byte[] temp = new byte[1024];

					int len = btInput.read(temp);
					Log.e("available", String.valueOf(len));
					if (len > 0) {
						byte[] btBuf = new byte[len];
						System.arraycopy(temp, 0, btBuf, 0, btBuf.length);
						SimpleDraw(X_index, btBuf, rateX, rateY, baseLine);// 把缓冲区数据画出来
						X_index = X_index + (btBuf.length/rateX) - 1;// 这里-1可以减少空隙
						if (X_index > sfv.getHeight()) {
							X_index = 0;
						}
					}
					Thread.sleep(wait);// 延时一定时间缓冲数据
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		/**
		 * 绘制指定区域
		 * 
		 * @param start
		 *            X轴开始的位置(全屏)
		 * @param inputBuf
		 *            缓冲区
		 * @param rateX
		 *            X轴数据缩小的比例
		 * @param rateY
		 *            Y轴数据缩小的比例
		 * @param baseLine
		 *            Y轴基线
		 */
		void SimpleDraw(int start, byte[] inputBuf, int rateX, int rateY,
				int baseLine) {
			if (start == 0)
				oldX = 0;
			// 根据需要缩小X轴比例
			byte[] buffer = new byte[inputBuf.length / rateX];
			for (int i = 0, ii = 0; i < buffer.length; i++, ii = i * rateX)
				buffer[i] = inputBuf[ii];

			Canvas canvas = sfv.getHolder().lockCanvas(
					new Rect(0, start, sfv.getWidth(), start + buffer.length));// 关键:获取画布
			canvas.drawColor(Color.BLACK);// 清除背景

			for (int i = 0; i < buffer.length; i++) {// 有多少画多少
				int y = i + start;
				int x = (0xFF - (buffer[i] & 0xFF))// 0xFF- 用于翻转，
													// &0xFF用把byte类型的负数取值转为正
						/ rateY + baseLine;// 调节缩小比例，调节基准线
				canvas.drawLine(oldX, oldY, x, y, mPaint);
				oldX = x;
				oldY = y;
			}
			sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
		}
	}
}
