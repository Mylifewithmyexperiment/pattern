import com.sun.nio.sctp.*;
import java.io.*;
import java.net.*;
import java.nio.*;
import java.nio.charset.*;
import java.text.*;
import java.util.*;

public class DaytimeServer {
    static int SERVER_PORT = 3456;
    static int US_STREAM = 0;
    static int FR_STREAM = 1;

    public static void main(String[] args) throws IOException {
        SctpServerChannel ssc = SctpServerChannel.open();
        InetSocketAddress serverAddr = new InetSocketAddress(SERVER_PORT);
        ssc.bind(serverAddr);

        ByteBuffer buffer = ByteBuffer.allocateDirect(4*1024);
        ByteBuffer recvbuf = ByteBuffer.allocateDirect(4*1024);

        while (true) {
            SctpChannel sc = ssc.accept();

            MessageInfo outMessageInfo = MessageInfo.createOutgoing(null,0);
			while(true){
			buffer.putLong(12);
			sc.send(buffer, outMessageInfo);
			buffer.clear();
			}
            /*buffer.flip();

      
            
   

      
  
            // shutdown and receive all pending messages/notifications
            sc.shutdown();
            AssociationHandler assocHandler = new AssociationHandler();
            MessageInfo inMessageInfo = null;
            while (true) {
              inMessageInfo = sc.receive(recvbuf, System.out, assocHandler);
              if (inMessageInfo == null || inMessageInfo.bytes() == -1) {
                break;
              }
            }
            sc.close(); */
        }
    }

    static class AssociationHandler
        extends AbstractNotificationHandler<PrintStream>
    {
        public HandlerResult handleNotification(AssociationChangeNotification not,
                                                PrintStream stream) {
            stream.println("AssociationChangeNotification received: " + not);
            return HandlerResult.CONTINUE;
        }

        public HandlerResult handleNotification(ShutdownNotification not,
                                                PrintStream stream) {
            stream.println("ShutdownNotification received: " + not);
            return HandlerResult.RETURN;
        }
    }
}