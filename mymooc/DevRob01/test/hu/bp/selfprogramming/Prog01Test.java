package hu.bp.selfprogramming;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsMapContaining.hasEntry;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.*;
import hu.bp.common.SelectedExperiment;

import java.util.Map;

import org.junit.Before;
import org.junit.Test;

public class Prog01Test {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAnticipateDefault() {
		String[] defaultInteractions = {"a", "b", "c"};

		Map<String, Integer> a = Prog01.anticipate("", defaultInteractions, 1);

		assertThat(a, hasEntry("a", 0));
		assertThat(a, hasEntry("b", 0));
		assertThat(a, hasEntry("c", 0));
		assertEquals(a.size(), 3);
	}

	@Test
	public void testAnticipateSimple() {
		String[] defaultInteractions = {"a"};

		Map<String, Integer> a = Prog01.anticipate("abcab", defaultInteractions, 1);

		assertThat(a, hasEntry("a", 0));
		assertThat(a, hasEntry("ca", 1));
		assertEquals(a.size(), 2);
	}

	@Test
	public void testAnticipateOneOcasion() {
		String[] defaultInteractions = {"a"};

		Map<String, Integer> a = Prog01.anticipate("abcabdcab", defaultInteractions, 1);

		assertThat(a, hasEntry("a", 0));
		assertThat(a, hasEntry("ca", 1));
		assertThat(a, hasEntry("dca", 1));
		assertEquals(a.size(), 3);
	}

	@Test
	public void testAnticipateTwoOcasions() {
		String[] defaultInteractions = {"a"};

		Map<String, Integer> a = Prog01.anticipate("abcabcabdcab", defaultInteractions, 1);

		assertThat(a, hasEntry("a", 0));
		assertThat(a, hasEntry("ca", 2));
		assertThat(a, hasEntry("dca", 1));
		assertEquals(a.size(), 3);
	}

	@Test
	public void selectedExperimentEmpty() {

		Prog01 p = new Prog01();

		String[] defaultInteractions = {"a"};

		Map<String, Integer> a = Prog01.anticipate("abcabcabdcab", defaultInteractions, 1);

/*
		SelectedExperiment e = p.selectExperiment(anticipations, counter, lastInteraction)
		String[] defaultInteractions = {"a"};

		Map<String, Integer> a = Prog01.anticipate("abcabcabdcab", defaultInteractions, 1);

		assertThat(a, hasEntry("a", 0));
		assertThat(a, hasEntry("ca", 2));
		assertThat(a, hasEntry("dca", 1));
		assertEquals(a.size(), 3);
*/
	}


}
