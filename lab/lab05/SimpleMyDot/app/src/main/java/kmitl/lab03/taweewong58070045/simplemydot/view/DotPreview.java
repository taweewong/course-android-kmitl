package kmitl.lab03.taweewong58070045.simplemydot.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;

public class DotPreview extends View{
    private Paint paint;
    private Dot dot;

    public DotPreview(Context context) {
        super(context);
        paint = new Paint();
    }

    public DotPreview(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        paint = new Paint();
    }

    public DotPreview(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setColor(dot.getColor());
        canvas.drawCircle((this.getWidth()/2), this.getHeight()/2, dot.getRadius(), paint);
    }

    public void setDot(Dot dot) {
        this.dot = dot;
    }
}
