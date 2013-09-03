package com.cz.wisdomcity.view;

import java.util.ArrayList;

import java.util.List;

import android.app.Activity;

import android.content.Context;

import android.graphics.Bitmap;

import android.graphics.Canvas;

import android.graphics.Color;

import android.graphics.Paint;

import android.os.Handler;

import android.os.Message;

import android.util.AttributeSet;

import android.util.DisplayMetrics;

import android.widget.ImageView;

/**
 * 测试用 已无用
 * @author Dean
 *
 */
public class ECGView extends ImageView {

	private int countX; // x轴列数

	private int screenWidth; // 屏幕宽度

	private int unitSize = 40; // 列长度

	private int xLenght; // x轴长度

	private int startX;

	private int yLenght; // y轴长度

	private int countY = 5;

	private float[] xLine;

	private int countSpeed;

	private int startY = 8;

	private float[] yLine;

	private Bitmap bitmap;

	private Canvas canvas;

	private Paint paint;

	private int speed = 2; // x轴间距比例 ，越大间距越大

	private float[] description_Position;

	private List<Integer> freelist = new ArrayList<Integer>();

	private List<Integer> usedlist = new ArrayList<Integer>();

	private static boolean ISRUN = false;

	public static byte mode = 0;

	private Context mContext;

	private int[] data = new int[] { 0, 13, 15, 17, 20, 25, 3, 1, 2, 3, 0, 0,
			-2, -3, -10, -15, 0, 0, 0, 5, 0 };

	private int dataMax = 45; // 数值最大值
	private int index = 0;

	public ECGView(Context context) {

		super(context);

		this.mContext = context;

		init();

	}

	public ECGView(Context context, AttributeSet attrs) {

		super(context, attrs);

		this.mContext = context;
		
		init();

	}

	private void init() {
		DisplayMetrics localDisplayMetrics = new DisplayMetrics();
		((Activity) mContext).getWindowManager().getDefaultDisplay()
				.getMetrics(localDisplayMetrics);
		screenWidth = localDisplayMetrics.heightPixels;

		this.countX = ((-40 + screenWidth) / this.unitSize);

		this.xLenght = (this.countX * this.unitSize);

		this.startX = (screenWidth - this.xLenght >> 1);

		this.yLenght = (this.unitSize * this.countY);

		this.xLine = new float[3 + this.countY << 2];

		this.countSpeed = (this.xLenght >> 1);

		for (int i = 0; i < -8 + this.xLine.length; i += 4) {

			this.xLine[(i + 0)] = this.startX;

			this.xLine[(i + 1)] = (i / 4 * this.unitSize + this.startY);

			this.xLine[(i + 2)] = (this.startX + this.xLenght);

			this.xLine[(i + 3)] = (i / 4 * this.unitSize + this.startY);

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

			this.yLine[(j + 0)] = (j / 4 * this.unitSize + this.startX);

			this.yLine[(j + 1)] = this.startY;

			this.yLine[(j + 2)] = (j / 4 * this.unitSize + this.startX);

			this.yLine[(j + 3)] = (this.startY + this.yLenght);

		}

		bitmap = Bitmap.createBitmap(screenWidth,
				(2 * this.startY + this.yLenght), Bitmap.Config.ARGB_8888);

		canvas = new Canvas(bitmap);

		paint = new Paint();

	}

	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message paramMessage) {

			int i = (int) (100F * data[index++ % 20] / dataMax);
			// int i = (int) (100.0F * (float) outMemory.availMem / (float)
			// totleMemory);

			i = i * yLenght / 100;

			freelist.add(0, Integer.valueOf(yLenght + startY - yLenght / 2 - i));

			if (freelist.size() > countSpeed)

				freelist.remove(countSpeed);

			usedlist.add(0, Integer.valueOf(startY + yLenght - (yLenght - i)));

			if (usedlist.size() > countSpeed)

				usedlist.remove(countSpeed);

			for (i = 0; i < yLine.length; i += 4) {

				float[] arrayOfFloat3 = yLine;

				int j = i + 2;

				float[] arrayOfFloat1 = yLine;

				int m = i + 0;

				float f1 = arrayOfFloat1[m] - speed;

				arrayOfFloat1[m] = f1;

				arrayOfFloat3[j] = f1;

				if (yLine[(i + 0)] >= startX)

					continue;

				arrayOfFloat1 = yLine;

				int k = i + 2;

				float[] arrayOfFloat2 = yLine;

				j = i + 0;

				float f2 = xLenght + yLine[(i + 0)];

				arrayOfFloat2[j] = f2;

				arrayOfFloat1[k] = f2;

			}

			canvas.drawColor(Color.rgb(46, 48, 48));

			paint.setColor(Color.rgb(0, 128, 64));

			canvas.drawLines(xLine, paint);

			canvas.drawLines(yLine, paint);

			if (mode != 0) {

				paint.setColor(Color.rgb(255, 0, 0));

				description_Position = new float[2 * freelist.size()];

				for (i = 0; i < description_Position.length; i += 2) {

					description_Position[i] = (xLenght + startX - i / 2 * speed);

					description_Position[(i + 1)] = usedlist
							.get(i / 2).intValue();

				}

			}

			paint.setColor(Color.rgb(0, 255, 0));

			description_Position = new float[2 * freelist.size()];

			for (i = 0; i < description_Position.length; i += 2) {

				description_Position[i] = (xLenght + startX - i / 2 * speed);

				description_Position[(i + 1)] = freelist.get(i / 2)
						.intValue();

			}

			canvas.drawLines(description_Position, paint);

			if (description_Position.length > 4)

				canvas.drawLines(description_Position, 2, -2
						+ description_Position.length, paint);

			setImageBitmap(bitmap);

		}

	};

	public void sartRefView() {

		if (!ISRUN)

			new Thread() {

				@Override
				public void run() {

					ISRUN = true;

					while (true) {

						if (!ISRUN)

							return;

						try {

							handler.sendMessage(new Message());

							Thread.sleep(100);

						}

						catch (Exception localException) {

						}

					}

				}

			}.start();

	}

	public void stopRdfView() {

		ECGView.ISRUN = false;

	}

}
