package lab;

import frame.SortArray;

public class QuickSortB extends QuickSort {

	/**
	 * Quicksort algorithm implementation to sort a SorrtArray by choosing the
	 * pivot as the median of the elements at positions (left,middle,right)
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
        int pivotPos = getPivot(records,left, right);
        SortingItem pivot = records.getElementAt(pivotPos);
        swap(records, left, pivotPos);  // Dirty hack!

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

    private int getPivot(SortArray records, int i, int j) {
        SortingItem a = records.getElementAt(i);
        SortingItem b = records.getElementAt((int)Math.floor((i+j)/2));
        SortingItem c = records.getElementAt(j);

        if((j-i) < 2){
            return i;
        }

        if(equals(a,c)){
            return i;
        }

        if(equals(a,b)){
            return i;
        }

        if(equals(c,b)){
            return j;
        }

        if(lessThan(a,b)) { // a kleiner b
            if(lessThan(b,c)){ // b kleiner  c
                return (int)Math.floor((i+j)/2);
            }
            else{ // c kleiner  b
                if(lessThan(a,c)){ // a kleiner c
                    return j;
                }
                else{ // c kleiner a
                    return i;
                }
            }
        }else{ // b kleiner a
            if(lessThan(a,c)){ // a kleiner c
                return i;
            }
            else{ // c kleiner a
                if(lessThan(b,c)){ // b kleiner c
                    return j;
                }
                else{   // c kleiner b
                    return (int)Math.floor((i+j)/2);
                }
            }
        }

    }

    // You may add additional methods here

}
