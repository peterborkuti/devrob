package hu.bp.common;

import java.util.List;
import java.util.Random;

public class Utils {

	/**
	 * Gets a random element from a list 
	 * @param l a list
	 * @return a random element or null if list was empty
	 */
	public static <T> T getRandomElement(List<T> l) {
		if (l.isEmpty()) {
			return null;
		}

		Random r = new Random();

		return l.get(r.nextInt(l.size()));
	}
}
