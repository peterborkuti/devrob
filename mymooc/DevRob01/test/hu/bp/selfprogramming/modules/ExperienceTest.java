package hu.bp.selfprogramming.modules;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import static hu.bp.common.TestUtils.*;

public class ExperienceTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testExperience() {
		fail("Not yet implemented");
	}

	@Test
	public void testExperienceMapOfStringExperimentString() {
		fail("Not yet implemented");
	}

	@Test
	public void testExperienceMapOfStringExperiment() {
		fail("Not yet implemented");
	}

	@Test
	public void testExperienceListOfPrimitiveInteractionString() {
		fail("Not yet implemented");
	}

	@Test
	public void testLearn() {
		Experience e = new Experience();
		System.out.println(e.toString());

		Experiment enacted = new Experiment(p11);
		e.learn(enacted, null);

		System.out.println(e.toString());

		enacted = new Experiment(p12);

		List<Experiment> t1 = new ArrayList<Experiment>();
		Experiment newE = new Experiment(p11, p11);
		t1.add(newE);

		e.learn(enacted, t1);

		System.out.println(e.toString());

		e.learn(enacted, t1);
		System.out.println(e.toString());
	}

	@Test
	public void testGetBestExperiment() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLast() {
		Experience e = new Experience();
		assertNull(e.getLast(pis));

		Experiment t1 = new Experiment(p11);
		e.learn(t1, null);
		assertEquals(e.getLast(pis), p11);


		Experiment t2 = new Experiment(p12);
		e.learn(t2, null);
		assertEquals(e.getLast(pis), p12);

	}

}
