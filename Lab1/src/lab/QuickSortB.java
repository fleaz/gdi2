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

        SortingItem pivot = getPivot(records, left, right);
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
    public SortingItem getPivot(SortArray records, int left, int right){
        SortingItem a,b,c;
        a = records.getElementAt(left);
        b = records.getElementAt((int)Math.floor(left/right));
        c = records.getElementAt(right);
        //System.out.println("-----------");
        //System.out.println("A: " + a.getBookSerialNumber());
        //System.out.println("B: " + b.getBookSerialNumber());
        //System.out.println("C: " + c.getBookSerialNumber());

        if(lessOrEqual(a,b)){ // a < b
            if(lessOrEqual(b,c)){ // (a,b) < c
                //System.out.println("ret b");
                return b;
            }
            else{
                if(lessOrEqual(a,c)){
                    //System.out.println("ret c");
                    return c;
                }
                else{
                    //System.out.println("ret a");
                    return a;
                }
            }
        }
        else{ // b < a
            if(lessOrEqual(a,c)){ // (b,a) < c
                //System.out.println("Ret a");
                return a;
            }
            else{
                if(lessOrEqual(b,c)){
                    //System.out.println("Ret c");
                    return c;
                }
                else{
                    //System.out.println("Ret b");
                    return b;
                }
            }
        }
    }
}
