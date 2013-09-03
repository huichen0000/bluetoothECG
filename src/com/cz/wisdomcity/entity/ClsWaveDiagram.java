package com.cz.wisdomcity.entity;

import java.io.InputStream;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceView;
/**
 * ������ ������
 * @author Dean
 *
 */
public class ClsWaveDiagram {

	private boolean isRecording = false;// �߳̿��Ʊ��
	private InputStream btInput = null;// ��������������
	/**
	 * X����С�ı���
	 */
	public int rateX = 1; 
	/**
	 * Y����С�ı���
	 */
	public int rateY = 1;
	/**
	 * Y�����
	 */
	public int baseLine = 0;

	/**
	 * ��ʼ��
	 */
	public ClsWaveDiagram(InputStream btInput, int rateX, int rateY,
			int baseLine) {
		this.btInput = btInput;
		this.rateX = rateX;
		this.rateY = rateY;
		this.baseLine = baseLine;
	}

	/**
	 * ��ʼ
	 * 
	 * @param recBufSize
	 *            AudioRecord��MinBufferSize
	 */
	public void Start(SurfaceView sfv, Paint mPaint, int wait) {
		isRecording = true;
		new DrawThread(sfv, mPaint, wait).start();// ��ʼ�����߳�
	}

	/**
	 * ֹͣ
	 */
	public void Stop() {
		isRecording = false;
	}

	/**
	 * �������inBuf�е�����
	 * 
	 * @author GV
	 * 
	 */
	class DrawThread extends Thread {

		private int oldX = 0;// �ϴλ��Ƶ�X����
		private int oldY = 0;// �ϴλ��Ƶ�Y����
		private SurfaceView sfv;// ����
		private int X_index = 0;// ��ǰ��ͼ������ĻX�������
		private Paint mPaint;// ����
		private int wait = 50;// �̵߳ȴ�ʱ��

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
						SimpleDraw(X_index, btBuf, rateX, rateY, baseLine);// �ѻ��������ݻ�����
						X_index = X_index + (btBuf.length/rateX) - 1;// ����-1���Լ��ٿ�϶
						if (X_index > sfv.getHeight()) {
							X_index = 0;
						}
					}
					Thread.sleep(wait);// ��ʱһ��ʱ�仺������
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}

		/**
		 * ����ָ������
		 * 
		 * @param start
		 *            X�Ὺʼ��λ��(ȫ��)
		 * @param inputBuf
		 *            ������
		 * @param rateX
		 *            X��������С�ı���
		 * @param rateY
		 *            Y��������С�ı���
		 * @param baseLine
		 *            Y�����
		 */
		void SimpleDraw(int start, byte[] inputBuf, int rateX, int rateY,
				int baseLine) {
			if (start == 0)
				oldX = 0;
			// ������Ҫ��СX�����
			byte[] buffer = new byte[inputBuf.length / rateX];
			for (int i = 0, ii = 0; i < buffer.length; i++, ii = i * rateX)
				buffer[i] = inputBuf[ii];

			Canvas canvas = sfv.getHolder().lockCanvas(
					new Rect(0, start, sfv.getWidth(), start + buffer.length));// �ؼ�:��ȡ����
			canvas.drawColor(Color.BLACK);// �������

			for (int i = 0; i < buffer.length; i++) {// �ж��ٻ�����
				int y = i + start;
				int x = (0xFF - (buffer[i] & 0xFF))// 0xFF- ���ڷ�ת��
													// &0xFF�ð�byte���͵ĸ���ȡֵתΪ��
						/ rateY + baseLine;// ������С���������ڻ�׼��
				canvas.drawLine(oldX, oldY, x, y, mPaint);
				oldX = x;
				oldY = y;
			}
			sfv.getHolder().unlockCanvasAndPost(canvas);// �����������ύ���õ�ͼ��
		}
	}
}
