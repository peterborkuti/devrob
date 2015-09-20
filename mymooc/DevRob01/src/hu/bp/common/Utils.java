package hu.bp.common;

import java.util.ArrayList;
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

	/**
	 * True if the key ends with the same string as the hole starts
	 * 
	 * @param key
	 * @param hole
	 * @param len how long the checked part is
	 * @return
	 */
	public static boolean isFitted(String key, String hole, int len) {
		if (key == null || hole == null || "".equals(key) || "".equals(hole) ||
			len <= 0) {
			return false;
		}

		len = Math.min(len, hole.length());

		String suffix = hole.substring(0, len);

		return key.endsWith(suffix);
	}

	/**
	 * Gives the number of characters where key best fits to hole.
	 * hole is checked from its left while key checked from its left
	 * 
	 * key: ABCDEFG
	 * hole: FGHIJKL
	 * 
	 * Matches:
	 * ABCDEFG
	 *      FGHIJKL
	 *
	 * The longest match is 2 ("FG".length())
	 *
	 * @param key
	 * @param hole
	 * @return
	 */
	public static int bestFitSize(String key, String hole) {
		if (hole == null || key == null ||
			hole.length() == 0 || key.length() == 0) {

			return -1;
		}

		int maxLen = Math.min(key.length(), hole.length());

		int len = maxLen;

		while ((len > 0) && !isFitted(key, hole, len)) {
			len--;
		}

		return (len);
	}

	public static List<String> splitBy(String s, int by) {
		List<String> splited = new ArrayList<String>();

		if (s == null || "".equals(s) || by <= 0) {
			return splited;
		}

		for (int i = 0; i + by <= s.length(); i += by) {
			splited.add(s.substring(i, i + by));
		}

		return splited;
	}
}
