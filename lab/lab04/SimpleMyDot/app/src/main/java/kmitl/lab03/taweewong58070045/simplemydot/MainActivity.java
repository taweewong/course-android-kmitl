package kmitl.lab03.taweewong58070045.simplemydot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotParcelable;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotSerializable;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity implements Dot.OnDotChangedListener, DotView.OnTouchListener{

    private DotView dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final DotSerializable dotSerializable = new DotSerializable();
        dotSerializable.setCenterX(150);
        dotSerializable.setCenterY(150);
        dotSerializable.setColor(Color.RED);
        dotSerializable.setRadius(30);

        final DotParcelable dotParcelable = new DotParcelable(250, 250, 35, Color.RED);

        Button openActivity = (Button) findViewById(R.id.openActivity);
        openActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SecondActivity.class);
                intent.putExtra("x", 10);
                intent.putExtra("dotSerializable", dotSerializable);
                intent.putExtra("dotParcelable", dotParcelable);
                startActivity(intent);
            }
        });

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
