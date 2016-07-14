package com.huntero.andusb;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.huntero.andusb.models.UsbSerialDevice;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CommunicateActivity extends AppCompatActivity {


    public static final String TAG = CommunicateActivity.class.getSimpleName();
    private static final String USB_PERMISSION_ACTION = "com.huntero.andusb.USB_PERMISSION_CONNECTION";
    @InjectView(R.id.rv_content)
    RecyclerView mRvCommands;
    @InjectView(R.id.iv_device_config)
    ImageView mIvDeviceConfig;
    @InjectView(R.id.et_device_commands)
    EditText mEtCommands;
    @InjectView(R.id.btn_write)
    Button mBtnWrite;

    UsbManager manager = null;
    UsbSerialDevice mUsbSerialDevice = null;
    UsbInterface usbInterface = null;
    UsbDeviceConnection mConnection = null;
    UsbEndpoint mEndpointIn = null;
    UsbEndpoint mEndpointOut = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_communicate);
        ButterKnife.inject(this);

        Intent args = getIntent();
        mUsbSerialDevice = args.getParcelableExtra("data");


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mUsbSerialDevice != null){
            //连接
            int interFace = mUsbSerialDevice.interfaceCount;
            if (interFace <= 0) {
                Log.e(TAG, "onResume: Usb interface count is zero");
                return;
            }
            usbInterface = mUsbSerialDevice.geUsbDevice().getInterface(0);
            if (usbInterface.getEndpointCount() != 2) {
                //
                Log.e(TAG, "onResume: Not a usb device");
                return;
            }
            mEndpointIn = usbInterface.getEndpoint(0);
            mEndpointOut = usbInterface.getEndpoint(1);

            manager = (UsbManager)CommunicateActivity.this.getSystemService(Context.USB_SERVICE);

            if(!manager.hasPermission(mUsbSerialDevice.geUsbDevice())){
                PendingIntent mPermissionIntent = PendingIntent
                                .getBroadcast(this, 0, new Intent(USB_PERMISSION_ACTION), 0);
                IntentFilter filter=new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
                filter.addAction(USB_PERMISSION_ACTION);
                registerReceiver(mUsbReceiver, filter);
                manager.requestPermission(mUsbSerialDevice.geUsbDevice(), mPermissionIntent);
            }else{
                mConnection = manager.openDevice(mUsbSerialDevice.geUsbDevice());
                if (mConnection != null && mConnection.claimInterface(usbInterface, true)) {
                    Log.i(TAG, "onResume: connected");

                } else {
                    Log.e(TAG, "onResume: connect failure");
                    //finish();
//                    AlertDialog
                }
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mUsbSerialDevice != null){
            //断开连接
        }
    }

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (USB_PERMISSION_ACTION.equals(intent.getAction())) {
                Log.i(TAG, "onReceive: action true");
                synchronized (this) {
                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        Log.i(TAG, "onReceive: granted");
                        if (device != null) {
                            mConnection = manager.openDevice(device);
                            if (mConnection != null && mConnection.claimInterface(usbInterface, true)) {
                                Log.i(TAG, "onReceive: connected");
                                Toast.makeText(CommunicateActivity.this, "connected", Toast.LENGTH_SHORT)
                                        .show();
                            } else {
                                Log.e(TAG, "onReceive: connect failure");
                                //finish();
                            }
                        }else{
                            Log.i(TAG, "onReceive: device is null");
                        }
                    }else{
                        Log.i(TAG, "onReceive: not granted");
                    }
                }
            }
        }
    };
}
