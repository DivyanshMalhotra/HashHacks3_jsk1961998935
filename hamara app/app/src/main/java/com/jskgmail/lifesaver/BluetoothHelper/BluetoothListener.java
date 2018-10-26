package com.jskgmail.lifesaver.BluetoothHelper;


public interface BluetoothListener {

    void initializeBluetooth(OnBluetoothSupportedCheckListener listener);

    void enableBluetooth(OnBluetoothEnabledCheckListener listener);

    interface OnBluetoothSupportedCheckListener {

        void onBLENotSupported();

        void onBluetoothNotSupported();
    }

    interface OnBluetoothEnabledCheckListener{

        void onBluetoothEnabled(boolean enable);
    }

    interface BluetoothTrigger
    {
        void initBluetooth();

        void enableBluetooth();

    }
}
