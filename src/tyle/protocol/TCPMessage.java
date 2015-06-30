package tyle.protocol;

/**
 * A TCPMessage will build messages from a TCP byte stream in the form:
 * 
 * 16 bytes ID + 8 bytes Temperature
 */
import java.io.DataInputStream;
import java.io.IOException;

public class TCPMessage extends Message {

	public TCPMessage(DataInputStream data) throws IOException {
		
		byte[] b = new byte[16];
		
		System.out.println("Reading " + data.read(b, 0, 16) + " bytes as ID");
		setId(b);
		
		System.out.println("Reading 2 bytes as Measurement");
		setMeasurement(data.readShort());
	}

	@Override
	public String getMeasurementName() {
		return "temperature";
	}

}
