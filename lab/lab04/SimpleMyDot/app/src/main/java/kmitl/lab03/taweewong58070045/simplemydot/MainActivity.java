package kmitl.lab03.taweewong58070045.simplemydot;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import java.util.Random;

import kmitl.lab03.taweewong58070045.simplemydot.model.Colors;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotParcelable;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotSerializable;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dots;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity implements Dots.OnDotsChangedListener, DotView.OnDotViewPressListener{

    private DotView dotView;
    private Dots dots;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dotView = (DotView) findViewById(R.id.dotView);
        dotView.setListener(this);
        dots = new Dots();
        dots.setListener(this);
    }

    public void onOpenActivity(View view) {
        final DotSerializable dotSerializable = new DotSerializable(150, 150, 30, Color.RED);
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
    }

    public void onRandomDot(View view) {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());
        Dot newDot = new Dot(centerX, centerY, 30, new Colors().randomColor());
        dots.addDot(newDot);
    }

    public void onClearDots(View view) {
        dots.clearAll();
    }

    @Override
    public void onDotsChanged(Dots dots) {
        dotView.setDots(dots);
        dotView.invalidate();
    }

    @Override
    public void onDotViewPressed(int x, int y) {
        int dotPosition = dots.findDot(x, y);

        if (dotPosition == -1) {
            Dot newDot = new Dot(x, y, 50, new Colors().randomColor());
            dots.addDot(newDot);
        } else {
            dots.removeBy(dotPosition);
        }

    }
}
