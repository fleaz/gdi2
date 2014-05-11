package lab;

/**
 * 
 * This class represents one entry of the list that has to be sorted.
 * 
 */
public class SortingItem {

	// DO NOT modify
	public String BookSerialNumber;
	public String ReaderID;
	public String Status;

	// DO NOT modify
	public SortingItem() {

	}

	// DO NOT modify
	public SortingItem(SortingItem otherItem) {
		this.BookSerialNumber = otherItem.BookSerialNumber;
		this.ReaderID = otherItem.ReaderID;
		this.Status = otherItem.Status;
	}

	// You may add additional methods here
    public String getBookSerialNumber(){
        return this.BookSerialNumber;
    }

    public String getReaderID(){
        return this.ReaderID;
    }
    
    public String getStatus(){
        return this.Status;
    }

    @Override
    public String toString(){
        StringBuilder str = new StringBuilder();
        str.append(this.getBookSerialNumber());
        str.append(this.getReaderID());

        return str.toString();
    }
}
