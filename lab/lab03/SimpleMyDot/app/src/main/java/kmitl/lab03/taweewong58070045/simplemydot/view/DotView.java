package kmitl.lab03.taweewong58070045.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;

public class DotView extends View {

    private Paint paint;
    private Dot dot;
    private ArrayList<Dot> dots;

    public DotView(Context context) {
        super(context);
        paint = new Paint();
        dots = new ArrayList<>();
    }

    public DotView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
        dots = new ArrayList<>();
    }

    public DotView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        dots = new ArrayList<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(Color.RED);
        if (!dots.isEmpty()) {
            for (Dot dot : dots) {
                canvas.drawCircle(dot.getCenterX(), dot.getCenterY(), 30, paint);
            }
        }
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }

    public void addDot(Dot dot) {
        dots.add(dot);
    }
}
