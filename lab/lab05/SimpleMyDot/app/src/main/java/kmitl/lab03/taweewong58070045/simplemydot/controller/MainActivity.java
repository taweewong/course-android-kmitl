package kmitl.lab03.taweewong58070045.simplemydot.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import java.io.File;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.fragment.EditDotFragment;
import kmitl.lab03.taweewong58070045.simplemydot.fragment.MainFragment;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;

public class MainActivity extends AppCompatActivity implements MainFragment.OnDotSelectListener, EditDotFragment.OnDotUpdatedListener{

    private final int EDIT_REQUEST = 1;
    private final String MAIN_FRAGMENT_TAG = "MainFragmentTag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            initialFragment();
        }
        setStrictMode();
    }

    private void setStrictMode() {
        //Fix error when use Uri on Android Oreo
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void initialFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, MainFragment.newInstance(), MAIN_FRAGMENT_TAG)
                .commit();
    }

    private void updateFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .addToBackStack(null)
                .commit();
    }

//    public void onCaptureScreen() {
//        File file = ScreenshotUtils.store(ScreenshotUtils.getScreenshot(dotView),
//                "screenshot.jpg",
//                ScreenshotUtils.getMainDirectoryName(this));
//        shareImageFile(file);
//    }

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
//                onCaptureScreen();
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
//                    dots.setDot(dotPosition, dot);
                }
            }
        }
    }

    @Override
    public void onDotSelect(Dot dot, int position) {
        updateFragment(EditDotFragment.newInstance(dot, position));
    }

    @Override
    public void onDotUpdate(Dot dot, int position) {
        getSupportFragmentManager().popBackStack();
        MainFragment fragment = (MainFragment) getSupportFragmentManager().findFragmentByTag(MAIN_FRAGMENT_TAG);
        fragment.updateEditDotByPosition(dot, position);
    }
}
