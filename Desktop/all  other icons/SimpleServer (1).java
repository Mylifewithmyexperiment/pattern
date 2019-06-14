import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class SimpleServer {
	public static void main(String[] args) throws Exception {
		ServerSocket server = new ServerSocket(6568);
		Socket socket = server.accept();
		OutputStream output = socket.getOutputStream();

		byte[] bytes = new byte[4*1024]; // 4K
		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = 12;
		}

		while (true) {
			output.write(bytes);
		}
	}
}
