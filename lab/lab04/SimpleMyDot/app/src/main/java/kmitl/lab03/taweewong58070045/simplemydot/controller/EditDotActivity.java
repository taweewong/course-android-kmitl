package kmitl.lab03.taweewong58070045.simplemydot.controller;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotPreview;

public class EditDotActivity extends AppCompatActivity {
    Button colorPickerButton;
    EditText radiusEditText;
    DotPreview dotPreview;
    Dot dot;
    int dotPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dot);

        initialize();
    }

    private void initialize() {
        dot = getIntent().getParcelableExtra("dot");
        dotPosition = getIntent().getIntExtra("dotPosition", -1);

        dotPreview = (DotPreview) findViewById(R.id.editDotView);
        colorPickerButton = (Button) findViewById(R.id.colorPickerButton);
        radiusEditText = (EditText) findViewById(R.id.radiusEditText);

        dotPreview.setDot(dot);
        colorPickerButton.setBackgroundColor(dot.getColor());
        radiusEditText.setText(String.valueOf(dot.getRadius()));
    }

    public void onClickColorPicker(View view) {
        showColorPicker();
    }

    public void onClickConfirm(View view) {
        updateDot(dot.getColor(), getInputRadius());
        returnDot();
    }

    public void onClickCancel(View view) {
        finish();
    }

    public void onClickApply(View view) {
        updateDot(dot.getColor(), getInputRadius());
        dotPreview.invalidate();
    }

    private void showColorPicker() {
        ColorPickerDialogBuilder
                .with(EditDotActivity.this)
                .setTitle("Choose color")
                .initialColor(dot.getColor())
                .wheelType(ColorPickerView.WHEEL_TYPE.FLOWER)
                .density(12)
                .setOnColorSelectedListener(new OnColorSelectedListener() {
                    @Override
                    public void onColorSelected(int selectedColor) {
                    }
                })
                .setPositiveButton("ok", new ColorPickerClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int selectedColor, Integer[] allColors) {
                        colorPickerButton.setBackgroundColor(selectedColor);
                        updateDot(selectedColor, dot.getRadius());
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .build()
                .show();
    }

    private void updateDot(int color, int radius) {
        dot.setColor(color);
        dot.setRadius(radius);
    }

    private int getInputRadius() {
        return Integer.parseInt(radiusEditText.getText().toString());
    }

    private void returnDot() {
        Intent returnIntent = new Intent();
        returnIntent.putExtra("dot", dot);
        returnIntent.putExtra("dotPosition", dotPosition);
        setResult(RESULT_OK, returnIntent);
        finish();
    }
}
