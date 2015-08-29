package hu.bp.pattern;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
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
	public void testTooSmallSequence() {
		PatternFinder p = new PatternFinder("12", 4);
		Map<String, Integer> patterns = p.getAll("1");
		assertTrue(patterns.isEmpty());
	}

	@Test
	public void testFindSameOneLength() {
		PatternFinder p = new PatternFinder("1231231", 1);
		Map<String, Integer> patterns = p.getAll("1");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("231", 2));
	}

	@Test
	public void testFindSameTwoLength() {
		PatternFinder p = new PatternFinder("A1A2A3A1A2A3A1", 2);
		Map<String, Integer> patterns = p.getAll("A1");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("A2A3A1", 2));
	}

	@Test
	public void testFindDifferentOneLength() {
		PatternFinder p = new PatternFinder("12312312", 2);

		Map<String, Integer> patterns = p.getAll("1");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("31", 2));

		patterns = p.getAll("3");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("3", 2));
	}

	@Test
	public void testFindDifferentTwoLength() {
		PatternFinder p = new PatternFinder("A1A2A3A1A2A3A1A2", 2);

		Map<String, Integer> patterns = p.getAll("A1");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("A3A1", 2));

		patterns = p.getAll("A3");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("A3", 2));
	}

	@Test
	public void testFindComplexTwoLength() {
		PatternFinder p = new PatternFinder("A1A2A3A1A2A3A4A1A2A1A2", 2);

		Map<String, Integer> patterns = p.getAll("A1");
		assertEquals(patterns.size(), 3);
		assertThat(patterns, hasEntry("A1", 1));
		assertThat(patterns, hasEntry("A3A1", 1));
		assertThat(patterns, hasEntry("A3A4A1", 1));

		patterns = p.getAll("A2");
		assertEquals(patterns.size(), 3);
		assertThat(patterns, hasEntry("A3A1A2", 1));
		assertThat(patterns, hasEntry("A3A4A1A2", 1));
		assertThat(patterns, hasEntry("A1A2", 1));

		patterns = p.getAll("A3");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("A3", 2));

		patterns = p.getAll("A4");
		assertEquals(patterns.size(), 1);
		assertThat(patterns, hasEntry("A3A1A2A3A4", 1));
	}

}
