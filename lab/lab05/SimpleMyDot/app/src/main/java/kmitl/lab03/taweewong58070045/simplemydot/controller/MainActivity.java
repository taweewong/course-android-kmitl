package kmitl.lab03.taweewong58070045.simplemydot.controller;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.fragment.EditDotFragment;
import kmitl.lab03.taweewong58070045.simplemydot.fragment.MainFragment;
import kmitl.lab03.taweewong58070045.simplemydot.model.Dot;

public class MainActivity extends AppCompatActivity implements MainFragment.OnDotSelectListener, EditDotFragment.OnDotUpdatedListener{

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
        String MAIN_FRAGMENT_TAG = "MainFragmentTag";
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

    @Override
    public void onDotSelect(Dot dot, int position) {
        updateFragment(EditDotFragment.newInstance(dot, position));
    }

    @Override
    public void onDotUpdate(Dot dot, int position) {
        getSupportFragmentManager().popBackStack();
    }
}
