package Tyle;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;

public class TyleServer extends Thread {
	public static int PORT = 6789;

	private Socket socket;

	public TyleServer(Socket sock) {
		this.socket = sock;
	}

	public static void main(String args[]) throws IOException {

		ServerSocket mainSocket = null;

		try {
			mainSocket = new ServerSocket(PORT);
			System.out.println("TyleServer listening on port " + PORT);
			
			while (true) {
				Socket socket = mainSocket.accept();
				System.out.println("Received a new request");
				new TyleServer(socket).run();
			}

		} catch (Exception ex) {
			System.out.println("Unexpected error");
		} finally {
			mainSocket.close();
		}
		
		System.out.println("Server stopped");

	}

	public void run() {
		try {
			DataInputStream input = new DataInputStream(socket.getInputStream());
			Message message = new TCPMessage(input);

			// Got the message correctly, send upstream to be stored
			int status = send(message);
			
			System.out.println("Got a statusCode = " + status);

		} catch (IOException ex) {
			System.out.println("Connection error: " + ex);
		}

	}

	public int send(Message message) {
		HttpClient httpClient = HttpClientBuilder.create().build();

		int statusCode = -1;

		// Prepare request
		String baseurl = "http://leandroguillen.com:1026/v1";
		String idString = new String(message.getId());
		String measurementString = Short.toString(message.getMeasurement());
		String url = baseurl + "/contextEntities/" + idString + "/attributes/temperature"; 
		
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
}
