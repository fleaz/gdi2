package lab;

import frame.SortArray;

public class QuickSortA extends QuickSort {

	/**
	 * Quicksort algorithm implementation to sort a SorrtArray by choosing the
	 * pivot as the first (leftmost) element in the list
	 * 
	 * @param records
	 *            - list of elements to be sorted as a SortArray
	 * @param left
	 *            - the index of the left bound for the algorithm
	 * @param right
	 *            - the index of the right bound for the algorithm
	 * @return Returns the sorted list as SortArray
	 */
	@Override
	public void Quicksort(SortArray records, int left, int right) {
        if(left < right){
            int pivot = partition(records, left, right);
            Quicksort(records, left, pivot - 1);
            Quicksort(records, pivot + 1, right);
        }
	}

    private int partition(SortArray records, int left, int right) {
        SortingItem pivot = records.getElementAt(left);
        int i = left + 1;
        int j = right;

        while(i< j){
            while(lessThanOrEqual(records.getElementAt(i), pivot) && (i < right)){
                i++;
            }
            while(biggerThanOrEqual(records.getElementAt(j), pivot) && (j > left)){
                j--;
            }
            if (i<j){
                swap(records, i, j);
            }
        }

        if(lessThan(records.getElementAt(j), pivot)){
            swap(records, j, left); // left = pivotPos
        }
        return j;
    }

    // You may add additional methods here

}
