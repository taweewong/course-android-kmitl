package kmitl.lab03.taweewong58070045.simplemydot.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.flask.colorpicker.ColorPickerView;
import com.flask.colorpicker.OnColorSelectedListener;
import com.flask.colorpicker.builder.ColorPickerClickListener;
import com.flask.colorpicker.builder.ColorPickerDialogBuilder;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotPreview;

public class EditDotFragment extends Fragment implements View.OnClickListener {
    Button colorPickerButton;
    EditText radiusEditText;
    DotPreview dotPreview;
    Dot dot;
    int dotPosition;

    public EditDotFragment() {
        // Required empty public constructor
    }

    public static EditDotFragment newInstance(Dot dot, int dotPosition) {
        Bundle args = new Bundle();
        EditDotFragment fragment = new EditDotFragment();
        args.putParcelable("dot", dot);
        args.putInt("dotPosition", dotPosition);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

            dot = getArguments().getParcelable("dot");
            dotPosition = getArguments().getInt("dotPosition");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_edit_dot, container, false);
        initialView(rootView);
        return rootView;
    }

    private void initialView(View rootView) {
        dotPreview = (DotPreview) rootView.findViewById(R.id.editDotView);
        colorPickerButton = (Button) rootView.findViewById(R.id.colorPickerButton);
        radiusEditText = (EditText) rootView.findViewById(R.id.radiusEditText);
        Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        Button applyButton = (Button) rootView.findViewById(R.id.applyButton);
        Button confirmButton = (Button) rootView.findViewById(R.id.confirmButton);

        dotPreview.setDot(dot);
        colorPickerButton.setBackgroundColor(dot.getColor());
        colorPickerButton.setOnClickListener(this);
        radiusEditText.setText(String.valueOf(dot.getRadius()));
        cancelButton.setOnClickListener(this);
        applyButton.setOnClickListener(this);
        confirmButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.cancelButton:
                onClickCancel();
                break;
            case R.id.applyButton:
                onClickApply();
                break;
            case R.id.confirmButton:
                onClickConfirm();
                break;
            case R.id.colorPickerButton:
                onClickColorPicker();
        }
    }

    public void onClickColorPicker() {
        showColorPicker();
    }

    public void onClickConfirm() {
        updateDot(dot.getColor(), getInputRadius());
//        returnDot();
    }

    public void onClickCancel() {
//        finish();
    }

    public void onClickApply() {
        updateDot(dot.getColor(), getInputRadius());
        dotPreview.invalidate();
    }

    private void showColorPicker() {
        ColorPickerDialogBuilder
                .with(getContext())
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

//    private void returnDot() {
//        Intent returnIntent = new Intent();
//        returnIntent.putExtra("dot", dot);
//        returnIntent.putExtra("dotPosition", dotPosition);
//        setResult(RESULT_OK, returnIntent);
//        finish();
//    }
}
