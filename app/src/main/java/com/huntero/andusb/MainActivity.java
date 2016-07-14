package com.huntero.andusb;

import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.huntero.andusb.adapters.UsbDeviceListAdapter;
import com.huntero.andusb.controller.UsbController;
import com.huntero.andusb.models.UsbSerialDevice;
import com.huntero.andusb.view.UsbUIView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements UsbUIView{
    private static final String TAG = MainActivity.class.getSimpleName();

    @InjectView(R.id.vs_content)
    ViewSwitcher mVsContent;
    @InjectView(R.id.tv_count)
    TextView mTvDeviceCount;
    @InjectView(R.id.rv_devices)
    RecyclerView mRvDevices;
    UsbDeviceListAdapter mAdapterDevices = null;
    List<UsbSerialDevice> mDataDevices = null;

    UsbController mController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);

        mRvDevices.setHasFixedSize(true);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRvDevices.setLayoutManager(layoutManager);

        mController = new UsbController(this);
        mController.findDevices();
    }

    @Override
    protected void onResume() {
        super.onResume();

//        mUsbManager = (UsbManager)getSystemService(Context.USB_SERVICE);
//        HashMap<String, UsbDevice> devices = mUsbManager.getDeviceList();
//        Log.i(TAG, "onCreate: devices size=" + devices.size());
//        Intent intent = getIntent();
//        String action = intent.getAction();
//        if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
//            mUsbDevice = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//        }
//        if (mUsbDevice == null) {
//            refreshDevice();
//        }
//        if (mUsbDevice == null) {
//            return;
//        }
//        //判断权限
//        if(!mUsbManager.hasPermission(mUsbDevice)){
//            PendingIntent mPermissionIntent = PendingIntent
//                            .getBroadcast(this, 0, new Intent(USB_ACTION), 0);
//            IntentFilter filter=new IntentFilter(Intent.ACTION_MEDIA_MOUNTED);
//            filter.addAction(USB_ACTION);
//            registerReceiver(mUsbReceiver, filter);
//            mUsbManager.requestPermission(mUsbDevice, mPermissionIntent);
//        }else{
//        }
    }

//    private void refreshDevice() {
//        HashMap<String, UsbDevice> devices = mUsbManager.getDeviceList();
//        Iterator<UsbDevice> deviceIterator = devices.values().iterator();
//        if (deviceIterator.hasNext()) {
//            mUsbDevice = deviceIterator.next();
//        }
//    }
//
//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//    }
//
//    private static final String USB_ACTION = "com.huntero.andusb.USB_PERMISSION";
//    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            if (USB_ACTION.equals(intent.getAction())) {
//                Log.i(TAG, "onReceive: action true");
//                synchronized (this) {
//                    UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
//                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
//                        Log.i(TAG, "onReceive: granted");
//                        if (device != null) {
//                            //
//                            Log.i(TAG, "onReceive: device=" + device.getDeviceName());
//                        }else{
//                            Log.i(TAG, "onReceive: device is null");
//                        }
//                    }else{
//                        Log.i(TAG, "onReceive: not granted");
//                    }
//                }
//            }else{
//                Log.i(TAG, "onReceive: action=" + intent.getAction());
//            }
//        }
//    };

    @OnClick(R.id.tv_count)
    protected void onRefresh(){
        mController.findDevices();
    }

    @Override
    public void showEmptyHint() {
        mTvDeviceCount.setText(getString(R.string.device_count, 0));

        mVsContent.setDisplayedChild(1);
    }

    @Override
    public void setOrUpdateAdapter(List<UsbSerialDevice> devices) {
        //
//        if (BuildConfig.DEBUG) {
//            for (int i = 0; i < 20; i++) {
//                devices.add(new UsbSerialDevice(i+1, "usb" + i));
//            }
//        }
        //
        mTvDeviceCount.setText(getString(R.string.device_count, devices.size()));
        //
        mVsContent.setDisplayedChild(0);
        if (mDataDevices == null) {
            mDataDevices = new ArrayList<>();
        } else {
            mDataDevices.clear();
        }
        mDataDevices.addAll(devices);

        if (mAdapterDevices == null) {
            setRecyclerViewAdapter();
        } else {
            mAdapterDevices.notifyDataSetChanged();
        }

    }

    private void setRecyclerViewAdapter() {
        mAdapterDevices = new UsbDeviceListAdapter(this, mDataDevices);
        mRvDevices.setAdapter(mAdapterDevices);
        mAdapterDevices.setOnItemClickListener(new UsbDeviceListAdapter.OnItemClickListener() {
            @Override
            public void onItemClickListener(int position, Object obj) {
                Intent intent = new Intent(MainActivity.this, CommunicateActivity.class);
                intent.putExtra("data", (UsbSerialDevice)obj);
                startActivity(intent);
            }
        });
    }
}
