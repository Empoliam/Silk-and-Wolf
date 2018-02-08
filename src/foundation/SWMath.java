package foundation;

import java.util.Random;

public abstract class SWMath {

	public static int generateBinomialInt(int n, double p, Random r) {

		int x = 0;
		for(int k = 0; k < n; k ++) {
			if(r.nextDouble() < p) x++;
		}

		return x;

	}

}
