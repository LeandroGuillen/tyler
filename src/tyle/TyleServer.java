package tyle;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

import tyle.protocol.Message;

public abstract class TyleServer {

	/**
	 * Sends the measurement received from a TCP or UDP packet up to an Orion
	 * Context Broker.
	 * 
	 * @param message
	 *            The message that will be delivered.
	 * @return -1 if there was an error while sending, or the HTTP status code
	 *         otherwise.
	 */
	public int send(Message message) {
		HttpClient httpClient = HttpClientBuilder.create().build();

		int statusCode = -1;

		// Prepare request
		String baseurl = "http://leandroguillen.com:1026/v1";
		String idString = new String(message.getId());
		String measurementString = Short.toString(message.getMeasurement());
		String url = baseurl + "/contextEntities/" + idString + "/attributes/" + message.getMeasurementName();

		System.out.println("Updating temperature of sensor with id '" + idString + "' to value " + measurementString);

		try {
			// Send request
			HttpPost request = new HttpPost(url);
			StringEntity params = new StringEntity("{\"value\":\"" + measurementString + "\"}");
			request.addHeader("content-type", "application/json");
			request.setEntity(params);

			// Get response
			HttpResponse response = httpClient.execute(request);
			statusCode = response.getStatusLine().getStatusCode();

		} catch (Exception ex) {
			// Something went wrong
			statusCode = -1;
		}
		return statusCode;
	}

	public static void main(String args[]) {
		new TyleServerTCP().run();
		// TODO: new TyleServerUDP().run()
	}

}
