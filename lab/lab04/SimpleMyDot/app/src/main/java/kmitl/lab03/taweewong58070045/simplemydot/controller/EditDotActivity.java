package kmitl.lab03.taweewong58070045.simplemydot.controller;

import android.content.DialogInterface;
import android.graphics.Color;
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
import kmitl.lab03.taweewong58070045.simplemydot.view.DotView;

public class EditDotActivity extends AppCompatActivity {
    Button colorPickerButton;
    EditText radiusEditText;
    DotView dotView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_dot);

        Dot dot = getIntent().getParcelableExtra("dot");
        dotView = (DotView) findViewById(R.id.editDotView);
        colorPickerButton = (Button) findViewById(R.id.colorPickerButton);
        radiusEditText = (EditText) findViewById(R.id.radiusEditText);

        colorPickerButton.setBackgroundColor(dot.getColor());
        radiusEditText.setText(String.valueOf(dot.getRadius()));
    }

    public void onClickColorPicker(View view) {
        showColorPicker();
    }

    private void showColorPicker() {
        ColorPickerDialogBuilder
                .with(EditDotActivity.this)
                .setTitle("Choose color")
                .initialColor(Color.RED)
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
}