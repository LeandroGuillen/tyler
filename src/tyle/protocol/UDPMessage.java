package tyle.protocol;

/**
 * A UDPMessage will build messages from a UDP byte stream in the form:
 * 
 * 2 bytes Humidity + 2 bytes ID
 */
import java.io.DataInputStream;
import java.io.IOException;

public class UDPMessage extends Message {

	public UDPMessage(DataInputStream data) throws IOException {
		// TODO
	}
	
	@Override
	public String getMeasurementName() {
		return "humidity";
	}

}
