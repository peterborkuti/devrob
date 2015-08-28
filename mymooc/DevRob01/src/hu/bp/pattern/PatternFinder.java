package hu.bp.pattern;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
	 * It trims the staring item and endSequence from the sub-sequence.
	 * 
	 * @param endSequence
	 * @return array of found sub-sequences
	 */
	public Map<String, Integer> getAll(String endSequence) {
		Map<String, Integer> occurences = new HashMap<String, Integer>(); 

		String lastItem = sequence.substring(sequence.length() - itemLength);
		String regExp = lastItem + ".*?" + endSequence;
		Pattern pattern = Pattern.compile(regExp);
		Matcher matcher = pattern.matcher(sequence);

		while(matcher.find()) {
			String found =
				sequence.substring(
					matcher.start() + itemLength,
					matcher.end() - endSequence.length());

			if (!occurences.containsKey(found)) {
				occurences.put(found, 0);
			}

			occurences.put(found, occurences.get(found) + 1);
		}

		return occurences;
	}

}
