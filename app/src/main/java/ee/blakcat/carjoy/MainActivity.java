package ee.blakcat.carjoy;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import ee.blakcat.carjoy.bluetooth.JoyBluetoothAdapter;
import ee.blakcat.carjoy.list.ListController;
import io.github.controlwear.virtual.joystick.android.JoystickView;

public class MainActivity extends AppCompatActivity {

    private BluetoothAdapter bluetoothAdapter;
    private ListController listController;
    private JoyBluetoothAdapter joyBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listController = new ListController();
        joyBluetoothAdapter = new JoyBluetoothAdapter();
        fillMenu(null);
        JoystickView joystickView = (JoystickView) findViewById(R.id.joystickView);
        joystickView.setOnMoveListener(((angle, strength) -> {
            sendToDevice("1234;");
        }));

    }

    public void fillMenu(View view) {
        Spinner dropdown = findViewById(R.id.spinner1);
        List<String> items = new ArrayList<>();
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bd = bluetoothAdapter.getBondedDevices();
        for (BluetoothDevice device : bd) {
            items.add(device.getName());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
        listController.updateDevices(bd);
        dropdown.setOnItemSelectedListener(listController);
    }

    public void connectToDevice(View view) {
        String msg = joyBluetoothAdapter.connect(listController.getSelectedDeviceId());
        ((TextView) findViewById(R.id.textView2)).setText(msg);
    }

    public void sendToDevice(String command) {
        String msg = joyBluetoothAdapter.send(command);
        ((TextView) findViewById(R.id.textView2)).setText(msg);
    }

    public void pressedTest(View view) {
        sendToDevice("1234;");
    }
}