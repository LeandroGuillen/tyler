package Tyle;

import java.io.DataInputStream;
import java.io.IOException;

public class TCPMessage extends Message {

	public TCPMessage(DataInputStream data) throws IOException {
		// TCP: 16 bytes for ID + 2 bytes measurement
		byte[] b = new byte[16];
		
		System.out.println("Reading " + data.read(b, 0, 16) + " bytes as ID");
		setId(b);
		
		System.out.println("Reading 2 bytes as Measurement");
		setMeasurement(data.readShort());
	}

}
