import java.util.*;
import java.io.*;
public class ALT extends Tournament{
	
	public ALT(MLB mlb, Team[][] st, String r)
	{
		super(mlb, st, r);
		type = "ALT";
		for(int x=0; x<8; x++)
		{
			main1[x]=st[1][x];
		}
		for(int x=0; x<7; x++)
		{
			main1[x+8]=st[0][x+8];
		}
		main2[0] = main1[0];
	}
	

}
