package hu.bp.pattern;

public class Pattern {
	public String getPattern() {
		return pattern;
	}

	public int getOccurences() {
		return occurences;
	}

	public Pattern(String pattern, int occurences) {
		super();
		this.pattern = pattern;
		this.occurences = occurences;
	}

	private String pattern;
	private int occurences;
	
}
