package com.example.bluetoothtest.view.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.Preference;
import androidx.preference.PreferenceDataStore;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.le.ScanResult;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.bluetoothtest.R;
import com.example.bluetoothtest.view.fragments.FragmentMainPage;
import com.example.bluetoothtest.utility.BleHelper;
import com.example.bluetoothtest.utility.WindowSetting;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    public static final String userTag = "tg-user";

    boolean Enabled = false, Scanning = false;
    BleHelper BleHelpers;

    public static String username;

    private BottomNavigationView bottomNavigationView;
    private View bottomNavigationShadow;

    public WindowSetting windowSetting;

    private static final int BUILD_VERSION = Build.VERSION.SDK_INT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTheme(R.style.SettingFragmentStyle); // Changing SettingPreferences Theme


        windowSetting = new WindowSetting(getWindow());
        windowSetting.windowsFullScreen().
                setStatusBarColor(ContextCompat.getColor(MainActivity.this, R.color.colorAccentH));

        bottomNavigationShadow = findViewById(R.id.shadow_bottom_navigation);


        Bundle bundle = getIntent().getExtras();

        if (bundle != null)
            username = MainActivityArgs.fromBundle(bundle).getUsername();


          /*  if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE))
                Toast.makeText(this, "You Don't Have Bluetooth Ble", Toast.LENGTH_SHORT).show();

            BleHelpers = new BleHelper();
            BleHelpers.setManager((BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE));
            BleHelpers.setAdapter(BleHelpers.manager.getAdapter());

            if (!isAdapterEnabledOrNotNull()) {
                Intent RequestBluetooth = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(RequestBluetooth, 1);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                {
                    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        AlertDialog.Builder Dialog = new AlertDialog.Builder(this);

                        Dialog.setTitle("Location Permission");
                        Dialog.setMessage("Do You Grant Permission To Location ? \n \r Location Needed For Bluetooth Scan");
                        Dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 2);

                            }
                        });
                        Dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(MainActivity.this, "You Didn't Grant Permission Bye !", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        });
                        Dialog.show();
                    }
                }


                BleHelpers.setBleScanner(BleHelpers.getAdapter().getBluetoothLeScanner());
            }*/


    /*
                    if (!Scanning) {
                        ScanHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {


                                BleHelpers.getBleScanner().stopScan(BluetoothCallBack);
                                Scanning = false;
                            }
                        }, 1750);
                        Devices.clear();

                        BleHelpers.getBleScanner().startScan(BluetoothCallBack);
                        Scanning = true;
                    } else {
                        BleHelpers.getBleScanner().stopScan(BluetoothCallBack);
                        Scanning = false;
                    }
    */
          /*  final Dialog WelcomeDialog = new Dialog(this);
            Rect rect = new Rect();
            WelcomeDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            WelcomeDialog.getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
            WelcomeDialog.getWindow().setLayout((int) (0.8f * rect.width()), (int) (0.4f * rect.height()));
           // WelcomeDialog.getWindow().getDecorView().setMinimumHeight((int)(0.2f * rect.height()));
           // WelcomeDialog.getWindow().getDecorView().setMinimumWidth((int)(0.8f * rect.width()));
            WelcomeDialog.getWindow().setBackgroundDrawable(getDrawable(R.drawable.background_dialog));
            View DialogView = LayoutInflater.from(this).inflate(R.layout.welcome_dialog, null);
            TextView UsernameTextView = DialogView.findViewById(R.id.TextView_UserName);
            UsernameTextView.setText(getIntent().getExtras().getString("UserName"));
            Button DialogOK = DialogView.findViewById(R.id.Button_Ok_Dialog);
            DialogOK.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    view.startAnimation(AnimationUtils.loadAnimation(WelcomeDialog.getContext(), R.anim.button_animation));
                    WelcomeDialog.cancel();
                }
            });
            WelcomeDialog.setContentView(DialogView);
            WelcomeDialog.show();*/

        //Navigation container which contains different elements of navigating
        NavHostFragment navContainer =
                (NavHostFragment) getSupportFragmentManager().
                        findFragmentById(R.id.nav_container);


        //Navigation controller , Controls navigation and destinations
        NavController navController = navContainer.getNavController();


        //BottomNavigationView initialization
        bottomNavigationView = findViewById(R.id.BottomNavigation);

        //NavigationUi gives a controller ability to update the ui as navigation changes
        //in this example our ui is BottomNavigationView
        NavigationUI.setupWithNavController(bottomNavigationView, navController);


        navController.addOnDestinationChangedListener((controller, destination, arguments) -> {
            int id = destination.getId();

            if (id == R.id.fEditProfile || id == R.id.fUserInformation) {
                bottomNavigationView.setVisibility(View.GONE);
                bottomNavigationShadow.setVisibility(View.GONE);
            } else {
                bottomNavigationView.setVisibility(View.VISIBLE);
                bottomNavigationShadow.setVisibility(View.VISIBLE);
            }
        });


    }

    private boolean isAdapterEnabledOrNotNull() {
        BluetoothAdapter adapter = BleHelpers.getAdapter();
        return adapter.isEnabled() && adapter != null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == 1) {
            if (resultCode != Activity.RESULT_OK) {
                Toast.makeText(this, "User Didn't Grant Permission", Toast.LENGTH_SHORT).show();
                finish();
            } else
                Enabled = true;
        }

    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        windowSetting.onSystemUiVisibilityChange(newConfig.orientation);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        windowSetting.windowsFullScreen();

    }


}
