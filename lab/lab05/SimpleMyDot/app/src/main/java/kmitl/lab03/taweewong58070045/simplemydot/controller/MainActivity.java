package kmitl.lab03.taweewong58070045.simplemydot.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.model.Colors;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotParcelable;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotSerializable;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dots;
import kmitl.lab03.taweewong58070045.simplemydot.utility.ScreenshotUtils;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotView;

public class MainActivity extends AppCompatActivity implements Dots.OnDotsChangedListener, DotView.OnDotViewPressListener{

    private DotView dotView;
    private Dots dots;

    private final int EDIT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());

        initialize();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_activity_menu, menu);
        return true;
    }

    private void initialize() {
        dotView = (DotView) findViewById(R.id.dotView);
        dotView.setListener(this);
        dots = new Dots();
        dots.setListener(this);
    }

    public void onOpenActivity(View view) {
        //Code in Workshop Week04
        final DotSerializable dotSerializable = new DotSerializable(150, 150, 30, Color.RED);
        final DotParcelable dotParcelable = new DotParcelable(250, 250, 35, Color.RED);

        Button openActivity = (Button) findViewById(R.id.undoButton);
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

    public void onCaptureScreen() {
        File file = ScreenshotUtils.store(ScreenshotUtils.getScreenshot(dotView),
                "screenshot.jpg",
                ScreenshotUtils.getMainDirectoryName(this));
        shareImageFile(file);
    }

    public void onRandomDot(View view) {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());
        Dot newDot = new Dot(centerX, centerY, 30, Colors.randomColor());
        dots.addDot(newDot);
    }

    public void onUndoDot(View view) {
        dots.undo();
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
            Dot newDot = new Dot(x, y, 50, Colors.randomColor());
            dots.addDot(newDot);
        } else {
            showAlertDialog(dotPosition);
        }
    }

    private void shareImageFile(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
        intent.setType("image/jpg");
        startActivity(Intent.createChooser(intent, "send image"));
    }

    private void showAlertDialog(final int dotPosition) {
        final List<String> optionList = new ArrayList<>();
        optionList.add("Edit");
        optionList.add("Delete");
        CharSequence[] options = optionList.toArray(new String[optionList.size()]);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Edit or Delete ?");
        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                switch (item) {
                    case 0:
                        startEditDotActivity(dots.getDot(dotPosition), dotPosition);
                        break;
                    case 1:
                        dots.removeBy(dotPosition);
                        Toast.makeText(MainActivity.this, "Deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });
        dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                onCaptureScreen();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == EDIT_REQUEST) {
            if (resultCode == RESULT_OK) {
                Dot dot = data.getParcelableExtra("dot");
                int dotPosition = data.getIntExtra("dotPosition", -1);

                if (dotPosition != -1) {
                    dots.setDot(dotPosition, dot);
                }
            }
        }
    }

    private void startEditDotActivity(Dot dot, int dotPosition) {
        Intent intent = new Intent(MainActivity.this, EditDotActivity.class);
        intent.putExtra("dot", dot);
        intent.putExtra("dotPosition", dotPosition);
        startActivityForResult(intent, EDIT_REQUEST);
    }
}
