package fr.enseeiht.superjumpingsumokart.application;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.InputDevice;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.parrot.arsdk.ardiscovery.ARDiscoveryDeviceService;

import fr.enseeiht.superjumpingsumokart.R;
import fr.enseeiht.superjumpingsumokart.arpack.GUIGame;

public class MirorChoice extends Activity {

    private Button miror;
    private Button normal;

    private boolean isServer;
    private ARDiscoveryDeviceService currentDeviceService;

    private String info = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_miror_choice);

        miror = (Button) findViewById(R.id.mirorChoice);
        normal = (Button) findViewById(R.id.nonMirorChoice);

        Intent intent = getIntent();
        isServer = intent.getExtras().getBoolean("isServer");
        currentDeviceService = (ARDiscoveryDeviceService) intent.getExtras().get("currentDeviceService");

        miror.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Miror();
            }
        });

        normal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NormalChoice();
            }
        });
    }

    public void Miror(){
        if (currentDeviceService == null) {
            Toast.makeText(getApplicationContext(), R.string.no_drone_connected, Toast.LENGTH_SHORT).show();
        } else if (!(isJoyConConnected())) {
            Toast.makeText(getApplicationContext(), R.string.no_joy_con_connected, Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(MirorChoice.this, GUIGame.class);
            i.putExtra("joyconControl", true);
            i.putExtra("isMiroir", true);
            i.putExtra("currentDeviceService", currentDeviceService);
            i.putExtra("isServer", isServer);
            startActivity(i);
        }
    }

    public void NormalChoice(){
        if (currentDeviceService == null) {
            Toast.makeText(getApplicationContext(), R.string.no_drone_connected, Toast.LENGTH_SHORT).show();
        } else if (!(isJoyConConnected())) {
            Toast.makeText(getApplicationContext(), R.string.no_joy_con_connected, Toast.LENGTH_SHORT).show();
        } else {
            Intent i = new Intent(MirorChoice.this, GUIGame.class);
            i.putExtra("joyconControl", true);
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
