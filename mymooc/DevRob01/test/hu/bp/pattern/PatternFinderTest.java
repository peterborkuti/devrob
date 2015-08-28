package hu.bp.pattern;

import java.lang.reflect.Array;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContaining.*;

import static org.hamcrest.collection.IsArrayWithSize.*;
import org.junit.Before;
import org.junit.Test;

public class PatternFinderTest {

	@Before
	public void setUp() throws Exception {
	}

	public void testFindEmpty() {
		PatternFinder p = new PatternFinder("", 1);
		String[] patterns = p.getAll("");
		assertThat(patterns, emptyArray());

		patterns = p.getAll("abcd");
		assertThat(patterns, emptyArray());

		p = new PatternFinder("abcd", 1);
		patterns = p.getAll("");
		assertThat(patterns, emptyArray());
		
	}

	public void testFindSameOneLength() {
		PatternFinder p = new PatternFinder("1231231", 1);
		String[] patterns = p.getAll("1");
		assertThat(patterns, arrayWithSize(1));
		assertThat(patterns, hasItemInArray("23"));
		assertThat(patterns, hasItemInArray("e1r1e2r1"));
	}

	public void testFindSmallest() {
		String[] patterns = PatternFinder.find("","1");
		assertThat(patterns, arrayWithSize(2));
		assertThat(patterns, hasItemInArray("1"));
		

		patterns = PatternFinder.find("1234321","1");
		assertThat(patterns, arrayWithSize(2));
		assertThat(patterns, hasItemInArray("1"));
	}

	public void testFindDoubles() {
		String[] patterns = PatternFinder.find("12341234","12");
		assertThat(patterns, arrayWithSize(2));
		assertThat(patterns, hasItemInArray("12"));
		
		

		patterns = PatternFinder.find("1234321","1");
		assertThat(patterns, arrayWithSize(2));
		assertThat(patterns, hasItemInArray("1"));
	}

}
