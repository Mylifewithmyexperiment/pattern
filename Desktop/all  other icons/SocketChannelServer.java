import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SocketChannelServer {
   public static void main(String[] args) throws IOException {
      SocketChannel server = SocketChannel.open();
      SocketAddress socketAddr = new InetSocketAddress("10.121.21.45", 6369);
      server.connect(socketAddr);


      ByteBuffer buffer = ByteBuffer.allocate(4*1024);

			buffer.putLong(12);
			while(true){
			buffer.putLong(12);
			server.write(buffer);
			buffer.clear();
			}
      //System.out.println("File Sent");
      //server.close();
   }
}
