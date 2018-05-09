package test;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

public class FilteringIteratorTest extends TestCase {
	private List<Integer> stringList = new ArrayList<Integer>();
	private FilteringIterator<Integer> filteringIterator;
	
	public void setUp() {
		for(int i= 1; i<=10; i++) {
			stringList.add(i);
		}
	}
	
	public void testWithEvenNumberAccept() {
		filteringIterator = new FilteringIterator<Integer>(stringList.iterator(), new EvenObjectTest());
		int next = filteringIterator.next();
		assertTrue(2 == next);
		int count = 1;
		while(filteringIterator.hasNext()) {
			count++;
			int item = filteringIterator.next();
			assertEquals(0, item%2);			
		}
		
		assertEquals(5, count);
		
		assertTrue(false == filteringIterator.hasNext());
		
		try {
			filteringIterator.next();
		} catch(Exception e) {
		   assertTrue(e.getMessage().equals("No element present in Filtered Iterator"));
		}
	}
	
	public void tearDown() {
		filteringIterator = null;
		stringList = null;
	}

	
	private class EvenObjectTest implements IObjectTest<Integer> {

		@Override
		public boolean test(Object o) {
			if(o instanceof Integer == false) {
				return false;
			}
			
			return ((Integer)o) % 2 == 0;
		}
		
	}

}
