package Tyle;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class TyleTCPTestClient {
	public static String id = "sensor123";
	public static short measurement = 12;

	public static void main(String[] args) {
		Socket socket = null;
		try {
			socket = new Socket("localhost", TyleServer.PORT);
			DataOutputStream output = new DataOutputStream(socket.getOutputStream());
			
			// Pad with 0s
			while (id.length() < 16) {
				id = "0" + id;
			}

			// Copy bytes
			byte[] bytesId = id.getBytes(StandardCharsets.UTF_8);
			byte[] bytes = new byte[18];
			for (int i = 0; i < 16; i++){
				bytes[i] = bytesId[i];
			}
			
			// Little endian: most significant byte is first
			bytes[16] = (byte) (measurement >> 8);
			bytes[17] = (byte) measurement;
			
			System.out.println("Sending " + bytes.length + " bytes to localhots:" + TyleServer.PORT);
			output.write(bytes);
			output.flush();

		} catch (IOException e) {
			System.out.println("Error connecting to TyleServer");
		}
	}
}
