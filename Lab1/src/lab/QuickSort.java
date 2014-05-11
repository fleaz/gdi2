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
    public void swap(SortArray records, int a, int b){
        SortingItem tmp = records.getElementAt(a);
        records.setElementAt(a, records.getElementAt(b));
        records.setElementAt(b, tmp);
    }

    public boolean lessThan(SortingItem left, SortingItem right){
        if(left.toString().compareTo(right.toString()) < 0){
            return true;
        }
        return false;
    }

    public boolean biggerThan(SortingItem left, SortingItem right){
        if(left.toString().compareTo(right.toString()) > 0){
            return true;
        }
        return false;
    }

    public boolean biggerThanOrEqual(SortingItem left, SortingItem right){
        return !lessThan(left,right);
    }

    public boolean  lessThanOrEqual(SortingItem left, SortingItem right){
        return !biggerThan(left, right);
    }

    public boolean equals(SortingItem left, SortingItem right){
        return lessThanOrEqual(left,right) && biggerThanOrEqual(left,right);
    }
}
