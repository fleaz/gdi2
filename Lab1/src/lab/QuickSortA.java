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

		SortingItem pivot = records.getElementAt(left);
		int i = left + 1;
		int j = right;

		while(i <= j){

			while((i <= j) && (lessOrEqual(records.getElementAt(i), pivot))){
				//System.out.println("left:" + left);
				i++;
			}
			while((j >= i) && (biggerThan(records.getElementAt(j),pivot))){
				//System.out.println("right:" + right);
				j--;
			}
			if(i < j){
				swap(records, i, j);
			}
		}
        swap(records, left, j);
        if (j-left > 1) Quicksort(records, left, j-1);
        if (right-i >= 1) Quicksort(records, i, right);
	}

	// You may add additional methods here

}
