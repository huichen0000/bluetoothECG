package com.cz.wisdomcity.view;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;

/**
 * 测试用 已无用
 * @author Dean
 *
 */
public class GraphView extends View implements Runnable {

	private List<Float> pointList;
	private int maxPoints;
	private String title;
	private Paint paint;
	private int pointCounter = 0;

	public Handler mHandler;

	public GraphView(Context context, String title, int maxPoints) {
		super(context);
		this.pointList = new ArrayList<Float>();
		this.maxPoints = maxPoints;
		this.title = title;
		this.paint = new Paint();
		addPointToGraph(0);
	}

	public void addPointToGraph(double point) {
		if (pointList.size() == maxPoints) {
			if (pointCounter == maxPoints)
				pointCounter = 0;
			pointList.remove(pointCounter);
			pointList.add(pointCounter, (float) point);
			pointCounter++;
		} else {
			pointList.add((float) point);
		}
	}

	@Override
	protected void onDraw(Canvas canvas) {
		drawGraph(canvas);
	}

	@Override
	public boolean onTouchEvent(MotionEvent e) {
		return false;
	}

	private void drawGraph(Canvas canvas) {
		float bottomspace = 70;
		float border = 0;
		float height = this.getHeight();
		float width = this.getWidth();
		float xAxisSize = width;// - border;
		float yAxisSize = height - 2 * border - bottomspace;
		float xScale = xAxisSize / maxPoints;
		float yScale = yAxisSize / 1; // getMax();

		paint.setColor(Color.CYAN);
		paint.setTextAlign(Align.CENTER);
		canvas.drawText(title, (width / 2) + border, 20, paint);
		paint.setColor(Color.LTGRAY);
		// canvas.drawLine(border, border, border, height - border -
		// bottomspace, paint);
		canvas.drawLine(border, height - border - bottomspace, width, height
				- border - bottomspace, paint);
		// paint.setTextAlign(Align.RIGHT);
		// paint.setColor(Color.LTGRAY);

		// canvas.drawText(Float.toString(getMax()),border, border, paint);
		// canvas.drawText("0", border, height -border - bottomspace, paint);
		// paint.setTextAlign(Align.LEFT);
		// canvas.drawText(Integer.toString(firstSample), border, height
		// -border/2 - bottomspace, paint);
		// canvas.drawText(Integer.toString(lastSample), width - border/2,
		// height -border/2 - bottomspace, paint);

		paint.setColor(Color.CYAN);

		for (int i = 0; i < pointList.size() - 1; i++) {
			float xa = border + (i * xScale);
			float ya = height
					- (border + bottomspace + (pointList.get(i) * yScale));
			float xb = border + ((i + 1) * xScale);
			float yb = height
					- (border + bottomspace + (pointList.get(i + 1) * yScale));
			canvas.drawLine(xa, ya, xb, yb, paint);
		}

	}

	private float getMax() {
		float max = pointList.get(0);
		for (int i = 0; i < pointList.size(); i++) {
			if (pointList.get(i) > max)
				max = pointList.get(i);
		}
		return max;
	}

	// private float getMin(){
	// float min = pointList.get(0);
	// for(int i=0;i<pointList.size();i++){
	// if(pointList.get(i) < min)
	// min = pointList.get(i);
	// }
	// return min;
	// }
	Handler handler = new Handler();

	@Override
	public void run() {
		invalidate();
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
