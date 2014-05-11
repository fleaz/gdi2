package lab;

import frame.SortArray;

/**
 * Abstract superclass for the Quicksort algorithm.
 * 
 * @author NAJI
 */
public abstract class QuickSort {

	// DO NOT modify this method
	public abstract void Quicksort(SortArray records, int left, int right);

	// You may add additional methods here
	public boolean lessOrEqual(SortingItem a, SortingItem b){
        StringBuilder bookA = new StringBuilder();
        StringBuilder bookB = new StringBuilder();

        bookA.append(a.getBookSerialNumber());
        bookA.append(a.getReaderID());

        bookB.append(b.getBookSerialNumber());
        bookB.append(b.getReaderID());

		if(bookA.toString().compareTo(bookB.toString()) <= 0){
			return true;
		}
		return false;
	}
	
	public boolean biggerThan(SortingItem a, SortingItem b){
		return !lessOrEqual(a,b);
	}
	
	public void swap(SortArray records, int j, int k){
		SortingItem tmp = records.getElementAt(j);
		records.setElementAt(j, records.getElementAt(k));
		records.setElementAt(k, tmp);
	}
}
