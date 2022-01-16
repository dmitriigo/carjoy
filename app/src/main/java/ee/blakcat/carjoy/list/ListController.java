package ee.blakcat.carjoy.list;

import android.bluetooth.BluetoothDevice;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.widget.AppCompatCheckedTextView;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ListController implements AdapterView.OnItemSelectedListener {
    private Map<String, String> deviceNameAddressMap = new HashMap<>();
    private String selectedDeviceId;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        selectedDeviceId = deviceNameAddressMap.get(((AppCompatCheckedTextView) view).getText());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public String getSelectedDeviceId() {
        return selectedDeviceId;
    }

    public void updateDevices(Set<BluetoothDevice> bd) {
        for (BluetoothDevice device : bd) {
            deviceNameAddressMap.put(device.getName(), device.getAddress());
        }
    }
}
