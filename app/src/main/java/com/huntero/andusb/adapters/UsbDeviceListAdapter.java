package com.huntero.andusb.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.hardware.usb.UsbDevice;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.huntero.andusb.R;
import com.huntero.andusb.models.UsbSerialDevice;

import java.util.HashMap;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by huntero on 16-7-5.
 */
public class UsbDeviceListAdapter extends RecyclerView.Adapter<UsbDeviceListAdapter.ViewHolder> {

    OnItemClickListener onItemClickListener;
    LayoutInflater mLayoutInflater;
    List<UsbSerialDevice> mData;

    private Resources mResources;

    public UsbDeviceListAdapter(Context context, List<UsbSerialDevice> data) {
        mResources = context.getResources();
        mLayoutInflater = LayoutInflater.from(context);
        mData = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mLayoutInflater.inflate(R.layout.item_usb_device, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final UsbSerialDevice usb = this.mData.get(position);
        holder.mTvDeviceName.setText(mResources.getString(R.string.device_item_name, usb.deviceId, usb.deviceName,
                usb.vendorId, usb.productId));
        holder.mTvDeviceInterface.setText("" + usb.interfaceCount);
        String config = "--";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            config = "" + usb.configurationCount;
        }
        holder.mTvDeviceConfig.setText(config);

        holder.itemView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClickListener(position, usb);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null?0:mData.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        @InjectView(R.id.tv_device_name)
        TextView mTvDeviceName;
        @InjectView(R.id.tv_device_interface)
        TextView mTvDeviceInterface;
        @InjectView(R.id.tv_device_config)
        TextView mTvDeviceConfig;
        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.inject(this, itemView);
        }
    }

    public interface OnItemClickListener {
        void onItemClickListener(int position, Object obj);
    }
}
