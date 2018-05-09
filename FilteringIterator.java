package test;

import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Create an Iterator filtering framework: 
 * (1) IObjectTest interface with a single boolean test(Object o) method 
 * (2) an implementation of Iterator (let's call it FilteringIterator) 
 * which is initialized with another Iterator and an IObjectTest instance:
 *  new FilteringIterator(myIterator, myTest). 
 *  Your FilteringIterator will then allow iteration over 'myIterator', but skipping any objects which don't pass the 'myTest' test. 
 *  Create a simple unit test for this framework.
 */
public final class FilteringIterator<T> implements Iterator<T> {
	
	private boolean hasNextElem;
	private final IObjectTest<T> filter;
	private T nextElem;
	private final Iterator<T> iterator;
	
	public  FilteringIterator(Iterator<T> iterator, IObjectTest<T> filter ) {
			this.iterator = iterator;			
			if(this.iterator == null) {
				throw new NullPointerException("Iterator cannot be null");
			}			
			this.filter = filter == null ? new PassAll<T>() : filter;
	        this.hasNextElem = true;
			this.findNextElement();
	}
	
	private void findNextElement() {
        while (iterator.hasNext()) {
            this.nextElem = iterator.next();
            if (filter.test(this.nextElem)) {
                return;
            }
        }
        
        this.nextElem = null;
        this.hasNextElem = false;
    }

	
	private static final class PassAll<E> implements IObjectTest<E> {

		@Override
		public boolean test(Object o) {
			return true;
		}	
		
	}
	
	
	@Override
	public boolean hasNext() {
		return hasNextElem;
	}

	@Override
	public T next() {
		T val = nextElem;
		if(!hasNextElem) {
			throw new NoSuchElementException("No element present in Filtered Iterator"); 
		}
		
	    findNextElement();
	    return val;
	}
	
	@Override
	public void remove() {
		throw new UnsupportedOperationException("Filtered Iterator doesnt support remove operation");
	}

}
