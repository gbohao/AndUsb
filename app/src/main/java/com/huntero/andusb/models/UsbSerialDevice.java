package com.huntero.andusb.models;

import android.hardware.usb.UsbDevice;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by huntero on 16-7-8.
 */
public class UsbSerialDevice implements Parcelable{
    public int vendorId;
    public int productId;
    public int deviceId;
    public String deviceName;
    public int interfaceCount;
    public int configurationCount;

    private UsbDevice mUsbDevice = null;

    public UsbDevice geUsbDevice() {
        return mUsbDevice;
    }

    public UsbSerialDevice() {

    }
    public UsbSerialDevice(UsbDevice usbDevice) {
        this.mUsbDevice = usbDevice;

        this.deviceId = usbDevice.getDeviceId();
        this.deviceName = usbDevice.getDeviceName();
        this.vendorId = usbDevice.getVendorId();
        this.productId = usbDevice.getProductId();
        this.interfaceCount = usbDevice.getInterfaceCount();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            this.configurationCount = usbDevice.getConfigurationCount();
        }

    }
    public UsbSerialDevice(int deviceId,String deviceName) {
        this.deviceId = deviceId;
        this.deviceName = deviceName;
    }


    protected UsbSerialDevice(Parcel in) {
        vendorId = in.readInt();
        productId = in.readInt();
        deviceId = in.readInt();
        deviceName = in.readString();
        interfaceCount = in.readInt();
        configurationCount = in.readInt();
        mUsbDevice = in.readParcelable(UsbDevice.class.getClassLoader());
    }

    public static final Creator<UsbSerialDevice> CREATOR = new Creator<UsbSerialDevice>() {
        @Override
        public UsbSerialDevice createFromParcel(Parcel in) {
            return new UsbSerialDevice(in);
        }

        @Override
        public UsbSerialDevice[] newArray(int size) {
            return new UsbSerialDevice[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(vendorId);
        parcel.writeInt(productId);
        parcel.writeInt(deviceId);
        parcel.writeString(deviceName);
        parcel.writeInt(interfaceCount);
        parcel.writeInt(configurationCount);
        parcel.writeParcelable(mUsbDevice, i);
    }
}
