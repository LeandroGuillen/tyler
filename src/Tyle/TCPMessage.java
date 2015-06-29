package Tyle;

import java.io.DataInputStream;
import java.io.IOException;

public class TCPMessage extends Message {

	public TCPMessage(DataInputStream data) throws IOException {
		// TCP: 16 bytes for ID + 2 bytes measurement
		byte[] b = new byte[16];
		System.out.println("Read " + data.read(b, 0, 16) + " bytes");

		setId(b);
		setMeasurement(data.readShort());
	}

}
