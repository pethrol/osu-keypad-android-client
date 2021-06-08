package io.procq.osukeypad;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.nio.ByteBuffer;

public class SendDataRunnable implements Runnable {
    int keyid;
    private byte buffer[] = new byte[4];

    private static DatagramSocket socket;
    private static InetAddress address;

    private static void reverse(byte[] array) {
        if (array == null) {
            return;
        }
        int i = 0;
        int j = array.length - 1;
        byte tmp;
        while (j > i) {
            tmp = array[j];
            array[j] = array[i];
            array[i] = tmp;
            j--;
            i++;
        }
    }

    SendDataRunnable(int keyid, String ip)  {
        try {
            this.keyid = keyid;
            socket = new DatagramSocket();
            address = InetAddress.getByName(ip);
        }
        catch (Exception e) {
            System.out.println("Cannot get socket and address");
        }
    }

    @Override
    public void run() {
        try {
            buffer = ByteBuffer.allocate(4).putInt(keyid).array();
            reverse(buffer);


            DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, 9121);
            socket.send(packet);
            socket.close();

        }
        catch (Exception e) {
            System.out.println("BAD HAPPEDNED \n");
            //e.printStackTrace();
        }
    }
}
