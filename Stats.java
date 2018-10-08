import java.util.*;
public class Stats {

	String POS;
	double AB;
	double H;
	int twoB;
	int threeB;
	int HR;
	int R;
	int RBI;
	int BB;
	int K;
	
	double permAB;
	double permH;
	int permtwoB;
	int permthreeB;
	int permHR;
	int permR;
	int permRBI;
	int permBB;
	int permK;
	//double AVG = H/AB;
	
	public Stats()
	{
		AB = 0;
		H = 0;
		twoB = 0;
		threeB = 0;
		HR = 0;
		R = 0;
		RBI = 0;
		BB = 0;
		K = 0;
		
		permAB = 0;
		permH = 0;
		permtwoB = 0;
		permthreeB = 0;
		permHR = 0;
		permR = 0;
		permRBI = 0;
		permBB = 0;
		permK = 0;
	}
	
	public void update()
	{
		permAB += AB;
		permH += H;
		permtwoB += twoB;
		permthreeB += threeB;
		permHR += HR;
		permR += R;
		permRBI += RBI;
		permBB += BB;
		permK += K;
	}
	
	public void reset()
	{
		AB = 0;
		H = 0;
		twoB = 0;
		threeB = 0;
		HR = 0;
		R = 0;
		RBI = 0;
		BB = 0;
		K = 0;
	}
	
	public double getStat(Category stat) {
		//pAB, pH, p2B, p3B, pHR, pR, pRBI, pBB, pK
		switch(stat) {
			default:
				return permAB;
			case peAB:
				return permAB;
			case peH:
				return permH;
			case pe2B:
				return (double)permtwoB;
			case pe3B:
				return (double)permthreeB;
			case peHR:
				return (double)permHR;
			case peR:
				return (double)permR;
			case peRBI:
				return (double)permRBI;
			case peBB:
				return (double)permBB;
			case peK:
				return (double)permK;
			
				
		}
	}
}
