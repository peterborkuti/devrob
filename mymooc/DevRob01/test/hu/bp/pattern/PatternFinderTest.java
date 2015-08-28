package hu.bp.pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsArrayContaining.hasItemInArray;
import static org.hamcrest.collection.IsArrayWithSize.arrayWithSize;
import static org.hamcrest.collection.IsArrayWithSize.emptyArray;
import static org.hamcrest.collection.IsMapContaining.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class PatternFinderTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindEmpty() {
		PatternFinder p = new PatternFinder("", 1);
		Map<String, Integer> patterns = p.getAll("");
		assertTrue(patterns.isEmpty());

		patterns = p.getAll("abcd");
		assertTrue(patterns.isEmpty());

		p = new PatternFinder("abcd", 1);
		patterns = p.getAll("");
		assertTrue(patterns.isEmpty());		
	}

	@Test
	public void testFindSameOneLength() {
		PatternFinder p = new PatternFinder("1231231", 1);
		Map<String, Integer> patterns = p.getAll("1");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("23", 2));
	}

	@Test
	public void testFindDoubles() {
		PatternFinder p = new PatternFinder("A1A2A3A1A2A3A1", 2);
		Map<String, Integer> patterns = p.getAll("A1");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("A2A3", 2));
	}

}
