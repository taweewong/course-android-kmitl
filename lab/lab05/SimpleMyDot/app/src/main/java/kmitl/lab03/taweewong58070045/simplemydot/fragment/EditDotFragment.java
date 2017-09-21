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
    public interface OnDotUpdatedListener {
        void onDotUpdate(Dot dot, int position);
    }

    Button colorPickerButton;
    EditText radiusEditText;
    DotPreview dotPreview;
    EditText positionXEditText;
    EditText positionYEditText;
    Dot dot;
    Dot previewDot;
    int dotPosition;
    OnDotUpdatedListener listener;

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
        previewDot = new Dot(dot.getCenterX(), dot.getCenterY(), dot.getRadius(), dot.getColor());
        dotPosition = getArguments().getInt("dotPosition");
        setOnDotUpdatedListener((OnDotUpdatedListener) getActivity());
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
        positionXEditText = (EditText) rootView.findViewById(R.id.positionXEditText);
        positionYEditText = (EditText) rootView.findViewById(R.id.positionYEditText);
        Button cancelButton = (Button) rootView.findViewById(R.id.cancelButton);
        Button applyButton = (Button) rootView.findViewById(R.id.applyButton);
        Button confirmButton = (Button) rootView.findViewById(R.id.confirmButton);

        dotPreview.setDot(previewDot);
        colorPickerButton.setBackgroundColor(previewDot.getColor());
        colorPickerButton.setOnClickListener(this);
        radiusEditText.setText(String.valueOf(previewDot.getRadius()));
        positionXEditText.setText(String.valueOf(previewDot.getCenterX()));
        positionYEditText.setText(String.valueOf(previewDot.getCenterY()));
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
        updateDot(dot, previewDot.getColor(), getInputRadius(), getInputPositionX(), getInputPositionY());
        listener.onDotUpdate(dot, dotPosition);
    }

    public void onClickCancel() {
        listener.onDotUpdate(dot, dotPosition);
    }

    public void onClickApply() {
        updateDot(previewDot, previewDot.getColor(), getInputRadius());
        dotPreview.invalidate();
    }

    private void showColorPicker() {
        ColorPickerDialogBuilder
                .with(getContext())
                .setTitle("Choose color")
                .initialColor(previewDot.getColor())
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
                        updateDot(previewDot, selectedColor, previewDot.getRadius());
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

    private void updateDot(Dot dot, int color, int radius) {
        dot.setColor(color);
        dot.setRadius(radius);
    }

    private void updateDot(Dot dot, int color, int radius, int centerX, int centerY) {
        dot.setColor(color);
        dot.setRadius(radius);
        dot.setCenterX(centerX);
        dot.setCenterY(centerY);
    }

    private int getInputRadius() {
        return Integer.parseInt(radiusEditText.getText().toString());
    }

    private int getInputPositionX() {
        return Integer.parseInt(positionXEditText.getText().toString());
    }

    private int getInputPositionY() {
        return Integer.parseInt(positionYEditText.getText().toString());
    }

    private void setOnDotUpdatedListener(OnDotUpdatedListener listener) {
        this.listener = listener;
    }
}
