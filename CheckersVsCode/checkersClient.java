package CheckersVsCode;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Scanner;

public class checkersClient {
	/*
	 * Released under MIT license.
	 */
		public static void main(String[] args) {
			String serverHostname = "192.168.1.7"; // The server's hostname or IP address
			int serverPortDefault = 7878; // Server default port 7878
			
			System.out.printf("Using default port %d.%n", serverPortDefault);

			try (DatagramSocket clientSocket = new DatagramSocket()) {
				InetAddress serverAddress = InetAddress.getByName(serverHostname);
				
				Scanner s = new Scanner(System.in);
				
				boolean isMove = false;
				
				String move = "";
				
				while(!isMove) {
					System.out.print("%nInput next move (moveFromXmoveFromYmoveToXmoveToY):"); // This is where moving graphics would input stuff
					move = s.next();
					isMove = moveCheck(move);
				}
				

				String message = move;
				byte[] sendData = message.getBytes();

				DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress, serverPortDefault);
				clientSocket.send(sendPacket);
				System.out.println("Sent to server: " + message);

				byte[] receiveData = new byte[1024];

				DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
				//DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length + 1);

				clientSocket.receive(receivePacket); // Blocks until a packet is received

				String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
				System.out.println("Received from server: " + response);

			} catch (UnknownHostException e) {
				System.err.println("Unknown host: " + e.getMessage());
			} catch (SocketException e) {
				System.err.println("Socket error: " + e.getMessage());
			} catch (IOException e) {
				System.err.println("I/O error: " + e.getMessage());
			} 
			/*catch (IllegalArgumentException e) {
				System.err.println("Parameter error: " + e.getMessage());
			}*/
		}
		
		public static boolean moveCheck(String input) {
			if (input.length() != 4) return false;
			if (Integer.valueOf(input.charAt(0)) < 8 || Integer.valueOf(input.charAt(0)) > 0) return false;
			if (Integer.valueOf(input.charAt(1)) < 8 || Integer.valueOf(input.charAt(1)) > 0) return false;
			if (Integer.valueOf(input.charAt(2)) < 8 || Integer.valueOf(input.charAt(2)) > 0) return false;
			if (Integer.valueOf(input.charAt(3)) < 8 || Integer.valueOf(input.charAt(3)) > 0) return false;
			return true;
		}
	}
