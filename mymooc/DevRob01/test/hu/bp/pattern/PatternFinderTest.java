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
		String[] patterns = PatternFinder.find("","","");
		assertThat(patterns, emptyArray());

		patterns = PatternFinder.find("abcd","","");
		assertThat(patterns, emptyArray());
		
		patterns = PatternFinder.find("","abcd","");
		assertThat(patterns, emptyArray());

		patterns = PatternFinder.find("","","abcd");
		assertThat(patterns, emptyArray());

	}

	public void testFindSame() {
		String[] patterns = PatternFinder.find("e1r1e2r1","e1r1","r1");
		assertThat(patterns, arrayWithSize(2));
		assertThat(patterns, hasItemInArray("e1r1"));
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
