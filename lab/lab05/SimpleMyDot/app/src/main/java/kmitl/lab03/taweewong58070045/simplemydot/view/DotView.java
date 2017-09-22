package kmitl.lab03.taweewong58070045.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dots;

public class DotView extends View {
    public interface OnDotViewPressListener {
        void onDotViewPressed(int x, int y);
        void onDotViewLongPressed(int x, int y);
        void onDotViewDrag(int x, int y, boolean isDragging);
    }

    private Paint paint;
    private Dots dots;
    private OnDotViewPressListener listener;

    public DotView(Context context) {
        super(context);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (dots != null) {
            for (Dot dot : dots.getAllDot()) {
                paint.setColor(dot.getColor());
                canvas.drawCircle(dot.getCenterX(), dot.getCenterY(), dot.getRadius(), paint);
            }
        }
    }


    final GestureDetector gestureDetector = new GestureDetector(getContext(), new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onDown(MotionEvent e) {
            return true;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            listener.onDotViewPressed((int) e.getX(), (int) e.getY());
            return super.onSingleTapUp(e);
        }

        @Override
        public void onLongPress(MotionEvent e) {
            listener.onDotViewLongPressed((int) e.getX(), (int) e.getY());
        }
    });


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                listener.onDotViewDrag((int) event.getX(), (int) event.getY(), true);
                break;
            case MotionEvent.ACTION_UP:
                listener.onDotViewDrag((int) event.getX(), (int) event.getY(), false);
        }
        return gestureDetector.onTouchEvent(event);
    }

    public void setDots(Dots dots) {
        this.dots = dots;
    }

    public void setListener(OnDotViewPressListener listener) {
        this.listener = listener;
    }
}
