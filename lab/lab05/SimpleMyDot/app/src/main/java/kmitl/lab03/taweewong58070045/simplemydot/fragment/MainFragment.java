package kmitl.lab03.taweewong58070045.simplemydot.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.File;
import java.util.Random;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.model.Colors;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dots;
import kmitl.lab03.taweewong58070045.simplemydot.utility.ScreenshotUtils;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotView;

public class MainFragment extends Fragment implements DotView.OnDotViewPressListener, Dots.OnDotsChangedListener, View.OnClickListener {
    public interface OnDotSelectListener {
        void onDotSelect(Dot dot, int position);
    }

    private static final String KEY_DOTS = "dots";

    private Dots dots;
    private DotView dotView;
    private OnDotSelectListener listener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.main_activity_menu, menu);
    }

    public static MainFragment newInstance() {
        Bundle args = new Bundle();
        MainFragment fragment = new MainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            dots = savedInstanceState.getParcelable(KEY_DOTS);
        } else {
            dots = new Dots();
        }

        if (dots != null) { dots.setListener(this); }
        setOnDotSelectListener((OnDotSelectListener) getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_dot_view, container, false);
        initialView(rootView);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable(KEY_DOTS, dots);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        dotView.setDots(dots);
    }

    private void initialView(View rootView) {
        dotView = (DotView) rootView.findViewById(R.id.dotView);
        Button randomDotButton = (Button) rootView.findViewById(R.id.randomDotButton);
        Button clearDotButton = (Button) rootView.findViewById(R.id.clearDotButton);
        Button undoButton = (Button) rootView.findViewById(R.id.undoButton);

        dotView.setListener(this);
        randomDotButton.setOnClickListener(this);
        clearDotButton.setOnClickListener(this);
        undoButton.setOnClickListener(this);
    }

    public void onCaptureScreen() {
        File file = ScreenshotUtils.captureScreen(dotView, "screenshot.jpg", getActivity());
        shareImageFile(file);
    }

    private void shareImageFile(File imageFile) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(imageFile));
        intent.setType("image/jpg");
        startActivity(Intent.createChooser(intent, "send image"));
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
    public void onDotViewPressed(int x, int y) {
        int dotPosition = dots.findDot(x, y);

        if (dotPosition == -1) {
            dots.addDot(new Dot(x, y, 50, Colors.randomColor()));
        } else {
            dots.removeBy(dotPosition);
        }
    }

    @Override
    public void onDotViewLongPressed(int x, int y) {
        int dotPosition = dots.findDot(x, y);

        if (dotPosition == -1) {
            dots.addDot(new Dot(x, y, 50, Colors.randomColor()));
        } else {
            listener.onDotSelect(dots.getDot(dotPosition), dotPosition);
        }
    }

    @Override
    public void onDotViewDrag(int x, int y, boolean isDragging) {
        int dotPosition = dots.findDot(x, y);

        if (dotPosition != -1 && isDragging) {
            dots.getDot(dotPosition).setCenterX(x);
            dots.getDot(dotPosition).setCenterY(y);
            dotView.invalidate();
        }
    }

    @Override
    public void onDotsChanged(Dots dots) {
        dotView.setDots(dots);
        dotView.invalidate();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.randomDotButton:
                onRandomDot();
                break;
            case R.id.clearDotButton:
                onClearDots();
                break;
            case R.id.undoButton:
                onUndoDot();
        }
    }

    public void onRandomDot() {
        Random random = new Random();
        int centerX = random.nextInt(dotView.getWidth());
        int centerY = random.nextInt(dotView.getHeight());
        Dot newDot = new Dot(centerX, centerY, 30, Colors.randomColor());
        dots.addDot(newDot);
    }

    public void onUndoDot() {
        dots.undo();
    }

    public void onClearDots() {
        dots.clearAll();
    }

    private void setOnDotSelectListener(OnDotSelectListener listener) {
        this.listener = listener;
    }

}
