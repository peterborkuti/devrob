package hu.bp.pattern;

public class SubSequence {
	public String getSubSequence() {
		return subSequence;
	}

	public int getOccurences() {
		return occurences;
	}

	public SubSequence(String subSequence, int occurences) {
		super();
		this.subSequence = subSequence;
		this.occurences = occurences;
	}

	private String subSequence;
	private int occurences;
	
}
