package foundation;

import java.util.Random;

/**
 * Class for custom mathematical methods.
 */
public abstract class SWMath {

	/**
	 * Generates an int with binomial probability distribution.
	 *
	 * @param n Number of trials
	 * @param p Probability of sucess
	 * @param RANDOM Project wide Math.Random object
	 * @return Generated int
	 */
	public static int generateBinomialInt(int n, double p, Random RANDOM) {

		int x = 0;
		for(int k = 0; k < n; k ++) {
			if(RANDOM.nextDouble() < p) x++;
		}

		return x;

	}

}
