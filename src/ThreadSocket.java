import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketException;

public class ThreadSocket implements Runnable
{
	private DatagramSocket socket;
	private boolean rodando;
	
	public void bind(int port) throws SocketException
	{
		socket = new DatagramSocket(port);
	}
	
	public void start()
	{
		Thread thread = new Thread(this);
		thread.start();
	}
	
	public void stop()
	{
		rodando = false;
		socket.close();
	}

	@Override
	public void run()
	{
		byte[] buffer = new byte[1024];
		DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
		
		rodando = true;
		while(rodando)
		{
			try
			{
				socket.receive(packet);
				
				String msg = new String(buffer, 0, packet.getLength());
				System.out.println(msg);
			} 
			catch (IOException e)
			{
				break;
			}
		}
	}

	public void sendTo(InetSocketAddress address, String msg) throws IOException
	{
		byte[] buffer = msg.getBytes();
		
		DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
		pack.setSocketAddress(address);
		
		socket.send(pack);
	}
}
