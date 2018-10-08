import java.util.*;
import java.io.*;
public class MLB {
	NationalLeague league1;
	AmericanLeague league2;
	
	public MLB() throws IOException
	{
		Scanner fs = new Scanner(new File("baseball.txt"));
		league1 = new NationalLeague(fs);
		league2 = new AmericanLeague(fs);
		fs.close();
	}

}