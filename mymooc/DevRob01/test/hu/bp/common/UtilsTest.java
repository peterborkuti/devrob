package hu.bp.common;

import static org.junit.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class UtilsTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testGetRandomElementEmptyList() {
		assertNull(Utils.getRandomElement(Collections.nCopies(0, "x")));
	}

	@Test
	public void testGetRandomElementOneItem() {
		assertEquals("x", Utils.getRandomElement(Collections.nCopies(1, "x")));
	}

	@Test
	public void testIsFitted() {
		assertFalse(Utils.isFitted(null, null, 0));
		assertFalse(Utils.isFitted(null, null, 1));
		assertFalse(Utils.isFitted(null, null, -1));
		assertFalse(Utils.isFitted("", "", 1));
		assertTrue(Utils.isFitted("abc", "abc", 5));

		assertTrue(Utils.isFitted("a", "a", 1));
		assertTrue(Utils.isFitted("abcxy", "xyqw", 2));
		assertFalse(Utils.isFitted("abcxy", "abcxy", 2));
	}

	@Test
	public void testBestFitSize() {
		assertEquals(-1, Utils.bestFitSize("", ""));
		assertEquals(-1, Utils.bestFitSize("xxx", ""));
		assertEquals(-1, Utils.bestFitSize("", "xxx"));
		assertEquals(3, Utils.bestFitSize("xxx", "xxx"));
		assertEquals(2, Utils.bestFitSize("abcdxy", "xygh"));
		assertEquals(1, Utils.bestFitSize("xxx", "xyy"));
		assertEquals(0, Utils.bestFitSize("abc", "def"));

		assertEquals(3, Utils.bestFitSize("abc", "abcdef"));

		assertEquals(8, Utils.bestFitSize("e1r1e2r2e1r2", "e2r2e1r2"));
		assertEquals(8, Utils.bestFitSize("e1r1e2r2e1r2", "e2r2e1r2e1r1"));
	}

	@Test
	public void testSplitBy() {
		assertEquals(0, Utils.splitBy(null, 0).size());
		assertEquals(0, Utils.splitBy("", 0).size());
		assertEquals(0, Utils.splitBy(null, 1).size());
		assertEquals(0, Utils.splitBy(null, -1).size());
		assertEquals(0, Utils.splitBy("x", -1).size());
		assertEquals(0, Utils.splitBy("x", 0).size());

		List<String> l = Utils.splitBy("x", 1);
		assertEquals(1, l.size());
		assertEquals("x", l.get(0));

		l = Utils.splitBy("xy", 1);
		assertEquals(2, l.size());
		assertEquals("x", l.get(0));
		assertEquals("y", l.get(1));

		l = Utils.splitBy("ab12xy", 2);
		assertEquals(3, l.size());
		assertEquals("ab", l.get(0));
		assertEquals("12", l.get(1));
		assertEquals("xy", l.get(2));

		l = Utils.splitBy("ab12x", 2);
		assertEquals(2, l.size());
		assertEquals("ab", l.get(0));
		assertEquals("12", l.get(1));
	}

}
