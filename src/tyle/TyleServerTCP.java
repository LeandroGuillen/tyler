package tyle;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import tyle.protocol.Message;
import tyle.protocol.TCPMessage;

public class TyleServerTCP extends TyleServer implements Runnable {

	public static int PORT = 6789;

	public void run() {

		ServerSocket mainSocket = null;
		try {
			mainSocket = new ServerSocket(PORT);
			System.out.println("TyleServer listening on port " + PORT);

			while (true) {
				Socket socket = mainSocket.accept();
				System.out.println("Received a new request");
				new ThreadProcessor(socket).run();
			}

		} catch (Exception ex) {
			System.out.println("Unexpected error");
		}
	}

	private class ThreadProcessor implements Runnable {

		private Socket socket;

		public ThreadProcessor(Socket socket) {
			this.socket = socket;
		}

		@Override
		public void run() {
			try {
				DataInputStream input = new DataInputStream(socket.getInputStream());
				Message message = new TCPMessage(input);

				// Got the message correctly, send upstream to
				// be stored
				int status = send(message);

				System.out.println("Got a statusCode = " + status);

			} catch (IOException ex) {
				System.out.println("Connection error: " + ex);
			}

		}
	}

}
