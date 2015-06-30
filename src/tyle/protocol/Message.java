package tyle.protocol;

/**
 * A message represents both a TCP and an UDP incoming request.
 * 
 * @author leandro
 *
 */
public abstract class Message {

	private short measurement;
	private byte[] id;

	public byte[] getId() {
		return id;
	}

	public void setId(byte[] id) {
		this.id = id;
	}

	public short getMeasurement() {
		return measurement;
	}

	public void setMeasurement(short measurement) {
		this.measurement = measurement;
	}

	public abstract String getMeasurementName();
}
