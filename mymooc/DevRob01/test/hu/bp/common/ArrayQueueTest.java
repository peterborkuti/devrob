package hu.bp.common;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.hamcrest.collection.*;

import static org.hamcrest.collection.IsIterableContainingInOrder.*;

public class ArrayQueueTest {

	private ArrayQueue<Integer> q;
	private static final int ELEMENTS = 3;
	

	@Before
	public void setUp() throws Exception {
		q = new ArrayQueue<Integer>(ELEMENTS);
		for (int i = 0; i < ELEMENTS; i++) {
			q.add(i + 1);
		}
	}

	@Test
	public void testArrayQueue() {
		assertEquals(q.maxSize, ELEMENTS);
		assertThat(q.asList(), IsIterableContainingInOrder.contains(1,2,3));
	}

	@Test
	public void testAdd() {
		q.add(ELEMENTS + 1);
		assertThat(q.asList(), contains(2,3,4));
		assertEquals((int)q.get(0), 2);

		q.add(ELEMENTS + 2);
		assertThat(q.asList(), contains(3,4,5));
		assertEquals((int)q.get(0), 3);

	}

	@Test
	public void testGet() {
		for (int i = 0; i < ELEMENTS; i++) {
			assertEquals((int)q.get(i), i + 1);
		}
	}

}
