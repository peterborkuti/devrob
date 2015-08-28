package hu.bp.pattern;

import java.util.ArrayList;
import java.util.List;

public class PatternFinder {

	private String sequence;
	private int itemLength = 2;

	public PatternFinder(String sequence) {
		this.sequence = sequence;
	}

	public PatternFinder(String sequence, int itemLength) {
		this.sequence = sequence;
		this.itemLength = itemLength;
	}

	
	public String[] getAll(String endSequence) {
		List<String> found = new ArrayList<String>();

		return found.toArray(new String[0]);
	}

}
