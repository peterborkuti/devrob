package hu.bp.common;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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

}
