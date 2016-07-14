package com.huntero.andusb.controller;

import android.app.Activity;
import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.huntero.andusb.BuildConfig;
import com.huntero.andusb.models.UsbSerialDevice;
import com.huntero.andusb.view.UsbUIView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by huntero on 16-7-5.
 */
public class UsbController {

    private UsbUIView view;
    private UsbManager mUsbManager;

    public UsbController(UsbUIView view){
        this.view = view;

         mUsbManager = (UsbManager) ((Activity)view).getSystemService(Context.USB_SERVICE);
    }

    public void findDevices(){
        HashMap<String, UsbDevice> devices = mUsbManager.getDeviceList();

        if (!BuildConfig.DEBUG && (devices == null || devices.isEmpty())) {
            //无数据
            view.showEmptyHint();
        } else {
            //更新adapter根据获取的设备
            List<UsbSerialDevice> lst = new ArrayList<UsbSerialDevice>();
            for(Iterator<UsbDevice> iterator = devices.values().iterator();iterator.hasNext();) {
                lst.add(new UsbSerialDevice(iterator.next()));
            }
            view.setOrUpdateAdapter(lst);
        }

    }


}
