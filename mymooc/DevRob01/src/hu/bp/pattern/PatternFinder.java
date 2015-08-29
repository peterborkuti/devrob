package hu.bp.pattern;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternFinder {

	private String sequence;
	private int itemLength = 2;

	public PatternFinder(String sequence) {
		this.sequence = sequence;
	}

	
	/**
	 * Sets the sequence for PatternFinder in which the sub-sequences will be found.
	 * itemLength parameter specifies the length of the basic building block of
	 * sub-sequences.
	 * @param sequence
	 * @param itemLength
	 */
	public PatternFinder(String sequence, int itemLength) {
		this.sequence = sequence;
		this.itemLength = itemLength;
	}

	
	/**
	 * Gets all the sub-sequences in sequence where the sub-sequence
	 * starts with the last item of sequences and ends with endSequence.
	 * 
	 * It trims the starting item from the sub-sequence.
	 * If endSequence was next to the starting item, the returned sub-sequence
	 * will be the endSequence.
	 * 
	 * It also counts how many times was the actual sub-sequence found.
	 * 
	 * @param endSequence
	 * @return Map of found sub-sequences and occurences
	 */
	public Map<String, Integer> getAll(String endSequence) {
		Map<String, Integer> occurences = new HashMap<String, Integer>();

		if ((sequence == null) || ("".equals(sequence)) ||
			(endSequence == null) || ("".equals(endSequence))) {
			return occurences;
		}

		String lastItem = sequence.substring(sequence.length() - itemLength);
		String regExp = lastItem + ".*?" + endSequence;
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(sequence);

		while(matcher.find()) {
			String found =
				sequence.substring(
					matcher.start() + itemLength,
					matcher.end());

			if (!occurences.containsKey(found)) {
				occurences.put(found, 0);
			}

			occurences.put(found, occurences.get(found) + 1);

			matcher.region(matcher.end() - itemLength, sequence.length());
		}

		return occurences;
	}

}
