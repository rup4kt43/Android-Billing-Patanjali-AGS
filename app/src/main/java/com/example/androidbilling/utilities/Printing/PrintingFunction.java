package com.example.androidbilling.utilities.Printing;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.example.androidbilling.billing_components.bill_save.pojo.SaveBillResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class PrintingFunction {
    public static PrintingFunction instance;

    //Printing Prop

    BluetoothAdapter bluetoothAdapter;
    BluetoothSocket bluetoothSocket;
    BluetoothDevice bluetoothDevice;
    OutputStream outputStream;
    InputStream inputStream;
    Thread thread;
    byte[] readBuffer;
    int readBufferPosition;
    volatile boolean stopWorker;
    private Activity context;

    private PrintingFunction(){

    }

    public static PrintingFunction getInstance(){
        if(instance == null){
            instance = new PrintingFunction();
        }
        return instance;
    }



    //Context changes according to application current page
    /*
     * ---------Main Thing to change to use this class in other application----------------
     * Change the context object
     * Change the Context object in constructor parameter from BillProceedView to respestive current class context
     * */
    public void printData(String printResponse, Activity context) {
        this.context = context;
        try {
            FindBluetoothDevice();
            openBluetoothPrinter();
            outputStream.write(printResponse.getBytes());
            disconnectBT();
        } catch (Exception ex) {
            Log.e("exception", ex.toString());
            ex.printStackTrace();
        }
    }

    void FindBluetoothDevice() {

        try {

            bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

            if (bluetoothAdapter.isEnabled()) {
                Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                context.startActivityForResult(enableBT, 0);
            }

            Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();

            if (pairedDevice.size() > 0) {
                for (BluetoothDevice pairedDev : pairedDevice) {

                    // My Bluetoth printer name is BTP_F09F1A
                    String bluetoothName = pairedDev.getName();

                    Log.e("bluetoothName ", bluetoothName);
                    Log.e("bluetoothName ", bluetoothName);
                    // TODO: 12/2/2019  restore bluetooth config condition
                  /*  if (bluetoothName.equals("BlueTooth Printer") || bluetoothName.equals("InnerPrinter")) {
                        //if(bluetoothDevice==null){
                        bluetoothDevice = pairedDev;
                    }*/  //----> updated By rupak : can be attached to any printer device 6/4/2019
                    bluetoothDevice = pairedDev;


                }
            }

            //lblPrinterName.setText("Bluetooth Printer Attached");
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    // Open Bluetooth Printer

    void openBluetoothPrinter() throws IOException {
        try {
            Log.e("Name", bluetoothDevice.getAddress());
            Log.e("Name", bluetoothDevice.getName());
            //Standard uuid from string //
            UUID uuidSting = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");
            bluetoothSocket = bluetoothDevice.createRfcommSocketToServiceRecord(uuidSting);
            bluetoothSocket.connect();
            outputStream = bluetoothSocket.getOutputStream();
            Log.e("message", outputStream.toString());
            Log.e("message", outputStream.toString());
            inputStream = bluetoothSocket.getInputStream();

            beginListenData();

        } catch (Exception ex) {
            Log.e("Exception", Objects.requireNonNull(ex.getMessage()));
        }
    }

    //begin to listen the data
    void beginListenData() {
        try {

            final Handler handler = new Handler();
            final byte delimiter = 10;
            stopWorker = false;
            readBufferPosition = 0;
            readBuffer = new byte[1024];

            thread = new Thread(() -> {

                while (!Thread.currentThread().isInterrupted() && !stopWorker) {
                    try {
                        int byteAvailable = inputStream.available();
                        if (byteAvailable > 0) {
                            byte[] packetByte = new byte[byteAvailable];
                            inputStream.read(packetByte);

                            for (int i = 0; i < byteAvailable; i++) {
                                byte b = packetByte[i];
                                if (b == delimiter) {
                                    byte[] encodedByte = new byte[readBufferPosition];
                                    System.arraycopy(
                                            readBuffer, 0,
                                            encodedByte, 0,
                                            encodedByte.length
                                    );
                                    final String data = new String(encodedByte, "US-ASCII");
                                    readBufferPosition = 0;
                                    handler.post(new Runnable() {
                                        @Override
                                        public void run() {
                                            //lblPrinterName.setText(data);
                                        }
                                    });
                                } else {
                                    readBuffer[readBufferPosition++] = b;
                                }
                            }
                        }
                    } catch (Exception ex) {
                        stopWorker = true;
                    }
                }

            });

            thread.start();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


    // Disconnect Printer //
    void disconnectBT() throws IOException {
        try {
            stopWorker = true;
            outputStream.close();
            inputStream.close();
            bluetoothSocket.close();
            //lblPrinterName.setText("Printer Disconnected.");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }


}
