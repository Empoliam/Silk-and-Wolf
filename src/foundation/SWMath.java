package foundation;

import java.util.Random;

/**
 * Class for custom mathematical methods.
 */
public final class SWMath {

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
	
	/**
	 * If the double d is outside the interval [l,u], round to the nearest boundary.
	 *
	 * @param d value to truncate
	 * @param l lower bound
	 * @param u upper bound
	 * @return truncated value
	 */
	public static double cutDoubleToRange(double d, double l, double u) {
		
		double o = d;
		
		if(o > 1.0) o = 1.0;
		else if(o < 0.0) o = 0.0;
		
		return o;
		
	}

}
