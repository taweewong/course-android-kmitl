package kmitl.lab03.taweewong58070045.simplemydot;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import java.util.Random;

import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity implements Dot.OnDotChangedListener, DotView.OnTouchListener{

    private DotView dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotView = (DotView) findViewById(R.id.dotView);
        dotView.setOnTouchListener(this);
    }

    public void onRandomDot(View view) {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());
        new Dot(centerX, centerY, 30, randomColor(), this);
    }

    public void onClearDots(View view) {
        dotView.clear();
        dotView.invalidate();
    }

    @Override
    public void onDotChanged(Dot dot) {
        dotView.addDot(dot);
        dotView.invalidate();
    }

    private int randomColor() {
        Random random = new Random();
        int r = random.nextInt(256);
        int g = random.nextInt(256);
        int b = random.nextInt(256);

        return Color.rgb(r, g, b);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        new Dot((int) event.getX(), (int) event.getY(), 150, Color.BLUE, this);

        return false;
    }
}
