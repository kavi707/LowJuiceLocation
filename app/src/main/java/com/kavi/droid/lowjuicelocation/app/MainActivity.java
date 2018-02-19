package com.kavi.droid.lowjuicelocation.app;

import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kavi.droid.lowjuicelocation.LowJuiceLocator;
import com.kavi.droid.lowjuicelocation.OnLocationChangeListener;
import com.kavi.droid.lowjuicelocation.models.LocationDetail;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.ACCESS_NETWORK_STATE;

public class MainActivity extends AppCompatActivity implements OnLocationChangeListener {

    private Context context = this;
    private Button tryMeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LowJuiceLocator.init(context, this);
        setUpView();
    }

    private void setUpView() {

        tryMeBtn = (Button) findViewById(R.id.try_me_btn);
        tryMeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                continueWithPermissions();
            }
        });
    }

    private void continueWithPermissions() {
        if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                        == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(context, ACCESS_NETWORK_STATE)
                        == PackageManager.PERMISSION_GRANTED ) {

            LowJuiceLocator.init(context, MainActivity.this).refreshLocation();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{ACCESS_NETWORK_STATE, ACCESS_COARSE_LOCATION, ACCESS_FINE_LOCATION,
                    }, 0);
        }
    }

    @Override
    public void onLocationChange(LocationDetail locationDetail) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    //Permission Granted Successfully. Write working code here.
                    if (ActivityCompat.checkSelfPermission(context, ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, ACCESS_COARSE_LOCATION)
                                    == PackageManager.PERMISSION_GRANTED &&
                            ActivityCompat.checkSelfPermission(context, ACCESS_NETWORK_STATE)
                                    == PackageManager.PERMISSION_GRANTED ) {
                        LowJuiceLocator.init(context, MainActivity.this).refreshLocation();
                    }
                } else {
                    continueWithPermissions();
                }
        }
    }
}
