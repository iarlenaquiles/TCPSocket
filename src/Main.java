import java.io.IOException;
import java.net.UnknownHostException;

public class Main {

	public static void main(String[] args) throws UnknownHostException, IOException {
		Server s1 = new Server("Serv 1", "127.0.0.1", 6789);
		Thread t1 = new Thread(s1);
		t1.start();

		Server s2 = new Server("Serv 2", "127.0.0.1", 6788);
		Thread t2 = new Thread(s2);
		t2.start();

		Server s3 = new Server("Serv 3", "127.0.0.1", 6787);
		Thread t3 = new Thread(s3);
		t3.start();

	}

}
