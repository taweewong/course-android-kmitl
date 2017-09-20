package kmitl.lab03.taweewong58070045.simplemydot.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.Random;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.model.Colors;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dots;
import kmitl.lab03.taweewong58070045.simplemydot.view.DotView;

public class MainFragment extends Fragment implements DotView.OnDotViewPressListener, Dots.OnDotsChangedListener, View.OnClickListener {
    public interface OnDotSelectListener {
        void onDotSelect(Dot dot, int position);
    }

    private Dots dots;
    private DotView dotView;
    private OnDotSelectListener listener;

    public MainFragment() {
        // Required empty public constructor
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
            dots = savedInstanceState.getParcelable("dots");
        } else {
            dots = new Dots();
        }
        dots.setListener(this);
        listener = (OnDotSelectListener) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_dot_view, container, false);
        initialView(rootView);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("dots", dots);
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
    public void onDotsChanged(Dots dots) {
        dotView.setDots(dots);
        dotView.invalidate();
    }

//    private void showAlertDialog(final int dotPosition) {
//        final List<String> optionList = new ArrayList<>();
//        optionList.add("Edit");
//        optionList.add("Delete");
//        CharSequence[] options = optionList.toArray(new String[optionList.size()]);
//
//        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
//        dialogBuilder.setTitle("Edit or Delete ?");
//        dialogBuilder.setItems(options, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int item) {
//                switch (item) {
//                    case 0:
//                        startEditDotActivity(dots.getDot(dotPosition), dotPosition);
//                        Toast.makeText(getContext(), "Edit ja", Toast.LENGTH_SHORT).show();
//                        break;
//                    case 1:
//                        dots.removeBy(dotPosition);
//                }
//            }
//        });
//        dialogBuilder.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//
//            }
//        });
//
//        AlertDialog alertDialog = dialogBuilder.create();
//        alertDialog.show();
//    }

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

    public void updateEditDotByPosition(Dot dot, int position) {
        dots.setDot(position, dot);
    }
}
