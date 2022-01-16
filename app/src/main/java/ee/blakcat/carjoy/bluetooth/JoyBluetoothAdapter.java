package ee.blakcat.carjoy.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.BufferedOutputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class JoyBluetoothAdapter {

    private BluetoothAdapter bluetoothAdapter;
    private OutputStream outputStream;


    public String connect(String selectedDeviceId) {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> bd = bluetoothAdapter.getBondedDevices();
        BluetoothDevice bluetoothDevice = null;
        for (BluetoothDevice device : bd) {
            if (device.getAddress().equals(selectedDeviceId)) {
                bluetoothDevice = device;
                break;
            }
        }
        if (bluetoothDevice != null) {
            try {
                BluetoothSocket bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(bluetoothDevice.getUuids()[0].getUuid());
                bluetoothSocket.connect();
                outputStream = bluetoothSocket.getOutputStream();
                return "Connected to " + bluetoothDevice.getName();
            } catch (Exception e) {
                return "error " + e.getMessage();
            }
        }
        return "Device not found";
    }

    public synchronized String send(String command) {
        try {
            if (outputStream != null) {
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(outputStream);
                bufferedOutputStream.write(command.getBytes(StandardCharsets.UTF_8));
                bufferedOutputStream.flush();
                return command;
            }
            return "output is null";
        } catch (Exception e) {
            return "Error " + e.getMessage();
        }
    }
}
