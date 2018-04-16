package com.gds.app.ble;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.bluetooth.BluetoothProfile;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.gds.app.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BleActivity extends AppCompatActivity {


    private static final UUID MYUUID = null;
    private static final UUID MYWRITECHARACTERISTIC = null;
    ArrayList<BluetoothDevice> mLeDevices=null;

    private static final long SCAN_PERIOD = 10000;

    private boolean mScanning;

    BluetoothAdapter mBluetoothAdapter;

    private BluetoothDevice mBluetoothDevice;

    private BluetoothGatt mBluetoothGatt;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ble);

        context=this;
        mLeDevices=new ArrayList<>();

//        BluetoothManager bluetoothManager= (BluetoothManager)
//                getSystemService(Context.BLUETOOTH_SERVICE);
//
//        mBluetoothAdapter=bluetoothManager.getAdapter();
//        Log.e("getAddress",mBluetoothAdapter.getAddress());
//        if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()) {
//            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(enableBtIntent, 1);
//        }





    }

    public void scanLeDevice(View view) {
        if(mScanning){
           return;
        }
        mScanning=true;
        mBluetoothAdapter.startLeScan(mLeScanCallback);
        handler.sendEmptyMessageDelayed(0,10*1000);
    }



    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            mBluetoothAdapter.stopLeScan(mLeScanCallback);
            mScanning=false;

        }
    };

    private BluetoothAdapter.LeScanCallback mLeScanCallback=new BluetoothAdapter.LeScanCallback() {
        @Override
        public void onLeScan(BluetoothDevice bluetoothDevice, int i, byte[] bytes) {

            mLeDevices.add(bluetoothDevice);
//            if(bluetoothDevice.getName()==null){
//                return;
//            }
            String name=bluetoothDevice.getName();
            if (bluetoothDevice.getAddress().contains("08:45:FE:23:7D:E3")) {
                Log.e("onLeScan",bluetoothDevice.getAddress());
                mBluetoothAdapter.stopLeScan(mLeScanCallback);
                mScanning=false;
                mBluetoothDevice=bluetoothDevice;
                connect();  //连接
            }


        }
    };


    public boolean connect() {
        if (mBluetoothDevice == null) {
            Log.i("connect", "BluetoothDevice is null.");
            return false;
        }
        mBluetoothGatt = mBluetoothDevice.connectGatt(context, true, mGattCallback);  //mGattCallback为回调接口
        if (mBluetoothGatt != null){
            if(mBluetoothGatt.connect()){
                Log.e("connect","Connect succeed");
                return true;
            }else {
                Log.e("connect","Connect fail");
                return false;
            }
        }else {

        }


        return false;
    }

    private UUID SERVICE_UUID;

    private UUID CHARACTERISTIC_UUID;

    private  BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {


        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if (newState == BluetoothProfile.STATE_CONNECTED){
                gatt.discoverServices();
                Log.e("onConnectionStateChange", "Connected to GATT server.");
            }else if(newState == BluetoothProfile.STATE_DISCONNECTED){
                if(mBluetoothDevice != null){
                    if(mBluetoothDevice!=null){
                        connect();
                        Log.e("mGattCallback", "重新连接");
                    }else {
                        Log.e("mGattCallback", "Disconnected from GATT server.");
                    }
                }
            }
        }


        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);

            if (status == BluetoothGatt.GATT_SUCCESS) {

                /*
                List<BluetoothGattService> gattServices=mBluetoothGatt.getServices();
                List<BluetoothGattCharacteristic> gattCharacteristics =
                        gattServices.getCharacteristics();
                 */
                BluetoothGattService service = gatt.getService(SERVICE_UUID); //通过厂家给的UUID获取BluetoothGattService
                if (service != null) {
                    BluetoothGattCharacteristic characteristic = service.getCharacteristic(CHARACTERISTIC_UUID);//同上
                    if (characteristic != null &&
                            (characteristic.getProperties() | BluetoothGattCharacteristic.PROPERTY_NOTIFY) > 0) {
                        //通过判断，打开Notification 通知，提醒。一般是设备测量完成了之后会发送对应的数据上来。
                        gatt.setCharacteristicNotification(characteristic, true);

                        //在通过上面的设置返回为true之后还要进行下面的操作，才能订阅到数据的上传。下面是完整的订阅数据代码！
                        if(gatt.setCharacteristicNotification(characteristic, true)){
                            for(BluetoothGattDescriptor dp: characteristic.getDescriptors()){
                                if (dp != null) {
                                    if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_NOTIFY) != 0) {
                                        dp.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
                                    } else if ((characteristic.getProperties() & BluetoothGattCharacteristic.PROPERTY_INDICATE) != 0) {
                                        dp.setValue(BluetoothGattDescriptor.ENABLE_INDICATION_VALUE);
                                    }
                                    gatt.writeDescriptor(dp);
                                }
                            }
                        }
                    }
                }
            }
        }


        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
            readCharacteristic(characteristic);
        }
    };


    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (mBluetoothAdapter == null || mBluetoothGatt == null) {
            Log.e("", "BluetoothAdapter not initialized");
            return;
        }
        mBluetoothGatt.readCharacteristic(characteristic);
        byte[] bytes = characteristic.getValue();
        String str = new String(bytes);
        Log.e("", "## readCharacteristic, 读取到: " + str);

    }


    private void changeMonitorMod(BluetoothGatt gatt, byte[] buffer) {

        BluetoothGattService writeService=null;
        if (gatt != null && gatt != null) {
            writeService = gatt.getService(MYUUID);
            if (writeService == null) {
                return;
            }
        }

        BluetoothGattCharacteristic writeCharacteristic = writeService.getCharacteristic(MYWRITECHARACTERISTIC);
        if (writeCharacteristic == null) {
            return;
        }

        writeCharacteristic.setValue(buffer);
        //上面的buffer数组中装的就是指令，多长？ 每一位上面的数字代表什么意思在协议中查看！
        gatt.writeCharacteristic(writeCharacteristic);//像设备写入指令。

    }


 




}
