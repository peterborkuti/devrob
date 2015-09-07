package hu.bp.common;

import java.util.List;

public class Utils {

	public static <T> T getRandomElement(List<T> l) {
		if (l.isEmpty()) {
			return null;
		}

		int index = (int) Math.floor(Math.random() * l.size());

		return l.get(index);
	}
}
