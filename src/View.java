import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Scanner;

public class View {

	public static void main(String[] args) throws IOException {
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("Nome desta Maquina: ");
		String nome = scanner.nextLine();
		
		System.out.print("Porta atual: ");
		int portaAtual = Integer.parseInt(scanner.nextLine());
		
		System.out.print("IP de destino:");
		String ipDestino = scanner.nextLine();
		
		System.out.print("Porta de destino:");
		int portaDestino = Integer.parseInt(scanner.nextLine());
		
		ThreadSocket threadSocket = new ThreadSocket();
		threadSocket.bind(portaAtual);
		threadSocket.start();
		
		System.out.println("Iniciou.");
		
		InetSocketAddress address = new InetSocketAddress(ipDestino, portaDestino);
		
		while(true){
			String msg = scanner.nextLine();
			
			if(msg.isEmpty())
				break;
			
			msg = nome + " disse: " + msg;
			
			threadSocket.sendTo(address, msg);
			System.out.println(msg);
		}
		
		scanner.close();
		threadSocket.stop();
		
		System.out.println("Finalizou.");
	}

}
