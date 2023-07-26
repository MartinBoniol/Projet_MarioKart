package fr.enseeiht.superjumpingsumokart.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.InputDevice;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;

import fr.enseeiht.superjumpingsumokart.R;
import fr.enseeiht.superjumpingsumokart.application.GUIWelcome;
import fr.enseeiht.superjumpingsumokart.application.MirorChoice;
import fr.enseeiht.superjumpingsumokart.arpack.GUIGame;

public class ControlChoice extends Activity {

    private Button joycon;
    private Button phone;

    private boolean isServer;
    private ARDiscoveryDeviceService currentDeviceService;

    private String info = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_control_choice);

        joycon = (Button) findViewById(R.id.joyconChoice);
        phone = (Button) findViewById(R.id.phoneChoice);

        Intent intent = getIntent();
        isServer = intent.getExtras().getBoolean("isServer");
        currentDeviceService = (ARDiscoveryDeviceService) intent.getExtras().get("currentDeviceService");

        joycon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                joyconChoice();
            }
        });

        phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneChoice();
            }
        });

    }

    public void joyconChoice() {
        if (currentDeviceService == null) {
            Toast.makeText(getApplicationContext(), R.string.no_drone_connected, Toast.LENGTH_SHORT).show();
        } else if (!(isJoyConConnected())) {
            Toast.makeText(getApplicationContext(), R.string.no_joy_con_connected, Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(ControlChoice.this, MirorChoice.class);
            i.putExtra("currentDeviceService", currentDeviceService);
            i.putExtra("isServer", isServer);
            i.putExtra("joyconControl", true);
            startActivity(i);
        }
    }

    public void phoneChoice() {
        if (currentDeviceService == null) {
            Toast.makeText(getApplicationContext(), R.string.no_drone_connected, Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(ControlChoice.this, GUIGame.class);
            i.putExtra("joyconControl", false);
            i.putExtra("isMiroir", false);
            i.putExtra("currentDeviceService", currentDeviceService);
            i.putExtra("isServer", isServer);
            startActivity(i);
        }
    }

    public Boolean isJoyConConnected() {
        int[] deviceIds = InputDevice.getDeviceIds();
        for (int deviceId : deviceIds) {
            InputDevice dev = InputDevice.getDevice(deviceId);
            info += dev.getName();
        }
        return info.contains("Joy-Con");
    }

}
