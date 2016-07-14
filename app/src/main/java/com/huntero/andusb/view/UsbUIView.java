package com.huntero.andusb.view;

import android.app.Activity;
import android.hardware.usb.UsbDevice;

import com.huntero.andusb.models.UsbSerialDevice;

import java.util.HashMap;
import java.util.List;

/**
 * Created by huntero on 16-7-5.
 */
public interface UsbUIView {

    void showEmptyHint();

    void setOrUpdateAdapter(List<UsbSerialDevice> devices);
}
