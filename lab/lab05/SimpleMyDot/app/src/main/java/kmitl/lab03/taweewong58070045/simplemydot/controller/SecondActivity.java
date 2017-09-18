package kmitl.lab03.taweewong58070045.simplemydot.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import kmitl.lab03.taweewong58070045.simplemydot.R;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotParcelable;
import kmitl.lab03.taweewong58070045.simplemydot.model.DotSerializable;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Button btnBack = (Button) findViewById(R.id.btnBack);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        int x = getIntent().getIntExtra("x", 0);
        DotSerializable dotSerializable = (DotSerializable) getIntent().getSerializableExtra("dotSerializable");
        DotParcelable dotParcelable = getIntent().getParcelableExtra("dotParcelable");

        TextView tvValueX = (TextView) findViewById(R.id.tvValueX);
        tvValueX.setText(String.valueOf(x));

        TextView tvDotSerial = (TextView) findViewById(R.id.tvDotSerial);
        tvDotSerial.setText("Serialization centerX " + dotSerializable.getCenterX() + " centerY " + dotSerializable.getCenterY());

        TextView tvDotParcel = (TextView) findViewById(R.id.tvDotParcel);
        tvDotParcel.setText("Parcel centerX " + dotParcelable.getCenterX() + " centerY " + dotParcelable.getCenterY());
    }
}
