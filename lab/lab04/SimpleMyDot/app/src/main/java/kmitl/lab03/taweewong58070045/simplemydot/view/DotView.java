package kmitl.lab03.taweewong58070045.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import java.util.ArrayList;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dots;

public class DotView extends View {
    public interface OnDotViewPressListener {
        void onDotViewPressed(int x, int y);
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

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                this.listener.onDotViewPressed((int) event.getX(), (int) event.getY());
                return true;
        }
        return false;
    }

    public void setDots(Dots dots) {
        this.dots = dots;
    }

    public void setListener(OnDotViewPressListener listener) {
        this.listener = listener;
    }
}
