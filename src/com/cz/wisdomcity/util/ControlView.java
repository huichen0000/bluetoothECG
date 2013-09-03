package com.cz.wisdomcity.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.cz.wisdomcity.ui.WaveActivity;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;

public class ControlView {

	private int countX = 8; // x轴列数
	// private int unitSize = 40; // 列长度
	private int unitSize_x;
	private int unitSize_y;
	private int xLenght; // x轴长度
	private int startX;
	private int yLenght; // y轴长度
	private int countY = 9;
	private float[] xLine;
	private int countSpeed;
	private int startY = 40;
	private float[] yLine;
	// private int speed = 2; // x轴间距比例 ，越大间距越大
	private SurfaceView sfv;
	private Paint mPaint;

	public static boolean flag = true;

	/*********方格点 *********/
	private List<Float> xLXpoint = new ArrayList<Float>();
	private List<Float> xLYpoint = new ArrayList<Float>();
	private List<Float> yLXpoint = new ArrayList<Float>();
	private List<Float> yLYpoint = new ArrayList<Float>();
	
	private WaveActivity activity;
	private DrawThread drawThread;

	private InputStream btInput = null;// 蓝牙数据输入流
	private boolean isNeed = false; // 是否需要处理数据 只有发送FF时需
	private byte lostByte; // 遗失的byte
	private boolean isLost = false; // 是否有遗失
	public static boolean isStop = true; //控制线程

	public ControlView(WaveActivity activity, SurfaceView sfv, int screenWidth,
			int screenHeight, InputStream btInput) {
		// TODO Auto-generated constructor stub
		flag = true;
		this.sfv = sfv;
		this.btInput = btInput;
		this.activity = activity;
		mPaint = new Paint();
		mPaint.setColor(Color.rgb(0, 128, 64));
		this.xLenght = ((screenWidth - 60) / countX) * countX;
		unitSize_x = this.xLenght / countX;
		this.startX = (screenWidth - this.xLenght >> 1);
		this.yLenght = (screenHeight * 2 / 3 - 40) / countY * countY;
		unitSize_y = yLenght / countY;

		this.xLine = new float[3 + this.countY << 2];

		this.countSpeed = 300 * countX;

		for (int i = 0; i < -8 + this.xLine.length; i += 4) {

			this.xLine[(i + 0)] = this.startX;

			this.xLine[(i + 1)] = (i / 4 * this.unitSize_y + this.startY);

			this.xLine[(i + 2)] = (this.startX + this.xLenght);

			this.xLine[(i + 3)] = (i / 4 * this.unitSize_y + this.startY);

			xLXpoint.add(this.xLine[(i + 0)] - 20);
			xLYpoint.add(this.xLine[(i + 1)] + 5);

		}

		this.xLine[(-8 + this.xLine.length)] = this.startX;

		this.xLine[(-7 + this.xLine.length)] = this.startY;

		this.xLine[(-6 + this.xLine.length)] = this.startX;

		this.xLine[(-5 + this.xLine.length)] = (this.startY + this.yLenght);

		this.xLine[(-4 + this.xLine.length)] = (this.xLenght + this.startX);

		this.xLine[(-3 + this.xLine.length)] = this.startY;

		this.xLine[(-2 + this.xLine.length)] = (this.xLenght + this.startX);

		this.xLine[(-1 + this.xLine.length)] = (this.startY + this.yLenght);

		this.yLine = new float[4 * (1 + this.countX)];

		for (int j = 0; j < this.yLine.length; j += 4) {

			this.yLine[(j + 0)] = (j / 4 * this.unitSize_x + this.startX);

			this.yLine[(j + 1)] = this.startY;

			this.yLine[(j + 2)] = (j / 4 * this.unitSize_x + this.startX);

			this.yLine[(j + 3)] = (this.startY + this.yLenght);

			yLXpoint.add(this.yLine[(j + 2)]);
			yLYpoint.add(this.yLine[(j + 3)] + 15);

		}
		drawThread = new DrawThread();
		drawThread.start();

	}

	public void start() {
		isNeed = true;
		isStop = false;
	}

	public void stop() {
		isStop = true;
	}

	

	/**
	 * 负责绘制data中的数据
	 * 
	 * @author DC
	 * 
	 */
	class DrawThread extends Thread {

		private List<Integer> freelist = new ArrayList<Integer>();
		private List<Integer> usedlist = new ArrayList<Integer>();
		private float[] description_Position;
		private Canvas canvas;
		private long wait = 100;

		@Override
		public void run() {

			byte[] buffer = new byte[1024];
			int bytes = 0;

			while (flag) {
				try {
						byte[] readBuf = null;
						int count = btInput.available();

						if (count == 0) {
							//System.out.println("可读长度=================" + count);
							if (isStop) {
								isNeed = false;
							}
						} else {
							bytes = btInput.read(buffer);
							readBuf = new byte[bytes];
							System.arraycopy(buffer, 0, readBuf, 0,
									readBuf.length);

							System.out.println("长度=================" + bytes);

							if (isNeed) { // 需要处理
								System.out.println("READ 原始:  "
										+ Utils.printHexString(readBuf));

								byte[] tempByte; // 加上前次遗失
								if (isLost) { // 前次有遗失 添加遗失到开头
									tempByte = Utils.insert(readBuf, lostByte);
								} else {
									tempByte = readBuf;
								}
								byte[] btBuf; // 计算此次是否有遗失
								getTheLostByte(tempByte);
								if (isLost && tempByte.length > 1) { // 有遗失
									btBuf = new byte[tempByte.length - 1];
									System.arraycopy(tempByte, 0, btBuf, 0,
											btBuf.length);
									System.out.println("READ:  "
											+ Utils.printHexString(btBuf));
								} else {
									btBuf = tempByte;
									System.out.println("READ:  "
											+ Utils.printHexString(btBuf));
								}
								String str16 = Utils.printHexString(btBuf);
								String[] arrStr16 = str16.split("[ ]");
								StringBuffer sb = new StringBuffer();
								int[] drawValue = new int[arrStr16.length];
								for (int i = 0; i < arrStr16.length; i++) {
									String temp16 = arrStr16[i];
									String str2 = Utils
											.hexStringToBinary(temp16);
									drawValue[i] = Utils.binaryToAlgorism(str2);
									sb.append(drawValue[i] + " ");
								}
								draw(drawValue);
								System.out.println("READ 2:  " + sb.toString());
							} else {
								System.out.println("NO NEED:  "
										+ Utils.printHexString(readBuf));
							}
						}
					Thread.sleep(wait);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		private void draw(int[] value) {
			try {
				canvas = sfv.getHolder().lockCanvas();// 关键:获取画布
				if (canvas != null) {

					for (int singleValue : value) {
						drawLine(canvas);
						drawValue(singleValue, canvas);
						
						/********** 画xy标识 **********/
						mPaint.setColor(Color.rgb(255, 0, 0));
						canvas.drawText("3.3V", xLXpoint.get(0), xLYpoint.get(0),
								mPaint);
						canvas.drawText("0V", xLXpoint.get(xLXpoint.size() - 1),
								xLYpoint.get(xLXpoint.size() - 1), mPaint);
						for (int m = 0, time = 0; m < yLXpoint.size(); m++, time += 500) {
							canvas.drawText(time + "", yLXpoint.get(m),
									yLYpoint.get(m), mPaint);
						}
					}

					
				}
			} catch (Exception e) {
				// TODO: handle exception
			} finally {
				if (canvas != null) {
					sfv.getHolder().unlockCanvasAndPost(canvas);// 解锁画布，提交画好的图像
				}
			}
		}

		private void drawValue(int value, Canvas canvas) {
			/********** 画心电图线 **********/
			mPaint.setColor(Color.rgb(0, 255, 0));
			// int i = (int) (100F * (Constants.data[index++] - 2.5)) / 45; //
			// 获取相对坐标
			int i = (int) (100F * value) / 1023; // 获取相对坐标

			i = i * yLenght / 100;
			// freelist.add(0,
			// Integer.valueOf(yLenght + startY - yLenght / 2 - i));
			freelist.add(0, Integer.valueOf(yLenght + startY - i));
			if (freelist.size() > countSpeed)
				freelist.remove(countSpeed);
			usedlist.add(0, Integer.valueOf(startY + yLenght - (yLenght - i)));
			if (usedlist.size() > countSpeed)
				usedlist.remove(countSpeed);
			description_Position = new float[2 * freelist.size()];
			for (i = 0; i < description_Position.length; i += 2) {
				description_Position[i] = (xLenght + startX - i * unitSize_x
						/ 600);
				description_Position[(i + 1)] = freelist.get(i / 2).intValue();
			}
			canvas.drawLines(description_Position, mPaint);
			if (description_Position.length > 4) {
				canvas.drawLines(description_Position, 2, -2
						+ description_Position.length, mPaint);
			}

		}

		private void drawLine(Canvas canvas) {
			/********** 画网格 **********/
			canvas.drawColor(Color.BLACK);// 清除背景
			canvas.drawColor(Color.rgb(46, 48, 48));
			mPaint.setColor(Color.rgb(0, 128, 64));
			canvas.drawLines(xLine, mPaint);
			canvas.drawLines(yLine, mPaint);
		}
	}

	/**
	 * 判断是否有遗漏
	 * 
	 * @param b
	 */
	private void getTheLostByte(byte[] b) {
		if (b.length < 1) {
			isLost = false;
		} else {
			if (b.length % 2 == 0) {
				isLost = false;
			} else {
				lostByte = b[b.length - 1];
				isLost = true;
			}
		}
	}

}
