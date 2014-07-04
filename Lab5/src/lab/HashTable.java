package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import frame.Entry;

import static java.lang.Math.floor;
import static java.lang.Math.pow;

/*
 * Implements a Hash-Table structure as introduced in the 
 * lecture to store the information read by the RFID 
 * readers in the library.
 *	
 * Make sure that you have tested all the given test cases
 * given on the homepage before you submit your solution.
 *
 */



public class HashTable {
	private Entry[] table = null;
    private String hashFunction, collisionResolution;

    /**
	 * The constructor
	 * 
	 * @param initialCapacity
	 *            represents the initial size of the Hash Table.
	 * @param hashFunction
	 *            can have the following values: division folding mid_square
	 * @param collisionResolution
	 *            can have the following values: linear_probing
	 *            quadratic_probing
	 * 
	 * The Hash-Table itself should be implemented as an array of entries
	 * (Entry[] in Java) and no other implementation will be accepted. When the
	 * load factor exceeds 75%, the capacity of the Hash-Table should be
	 * increased as described in the method rehash below. We assume a bucket
	 * factor of 1.
	 */
    public HashTable(int k, String hashFunction, String collisionResolution) {
        this.table = new Entry[k];
        this.hashFunction = hashFunction;
        this.collisionResolution = collisionResolution;
    }

    /**
	 * This method takes as input the name of a file containing a sequence of
	 * entries that should be inserted into the Hash-Table in the order they
	 * appear in the file. You cannot make any assumptions on the order of the
	 * entries nor is it allowed to change the order given in the file. You can
	 * assume that the file is located in the same directory as the executable
	 * program. The input file is similar to the input file for lab 1. The
	 * return value is the number of entries successfully inserted into the
	 * Hash-Table.
	 * 
	 * @param filename
	 *            name of the file containing the entries
	 * @return returns the number of entries successfully inserted in the
	 *         Hash-Table.
	 */
    public int loadFromFile (String filename) {
        ArrayList<String> input = readFile(filename);
        int x = 0;

        for (String s: input){
            System.out.println(s);
            String[] line = s.split(";");
            Entry e = new Entry(line[0], line[1], line[2]);
            if (this.insert(e)){
                x++;
            }

        }
    	return x;
    }

    private ArrayList<String> readFile(String filename) {
        BufferedReader br = null;
        ArrayList<String> data = new ArrayList<>();

        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(filename));
            while ((sCurrentLine = br.readLine()) != null) {
                data.add(sCurrentLine);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        return data;
    }

    /**
	 * This method inserts the entry insertEntry into the Hash-Table. Note that
	 * you have to deal with collisions if you want to insert an entry into a
	 * slot which is not empty. This method returns true if the insertion of the
	 * entry insertEntry is successful and false if the key of this entry
	 * already exists in the Hash-Table (the existing key/value pair is left
	 * unchanged).
	 * 
	 * @param insertEntry
	 *            entry to insert into the Hash-table
	 * @return returns true if the entry insertEntry is successfully inserted
	 *         false if the entry already exists in the Hash-Table
	 */
    public boolean insert(Entry insertEntry) {
        int hash = hashKey(insertEntry.getKey());

        if (find(insertEntry.getKey()) != null){
            System.out.println("Not Inserted. Key: " + insertEntry.getKey() + " Hash: " + hash);
            return false;
        }

        if(this.table[hash] == null || this.table[hash].isDeleted()){
            this.table[hash] = insertEntry;
            System.out.println("Inserted. Key: " + insertEntry.getKey() + " Hash: " + hash);
            return true;
        }
        else{
            int newHash = findNextPosition(hash);
            this.table[newHash] = insertEntry;
            System.out.println("Inserted. Key: " + insertEntry.getKey() + " Hash: " + hash);
            return true;
        }

    }

    private int findNextPosition(int oldHash) {
        int newHash = oldHash;
        //System.out.println("Old Hash: " + oldHash);

        if(this.collisionResolution.equals("linear_probing")){
            System.out.println(this.table[newHash]);
            while(this.table[newHash] != null || !this.table[newHash].isDeleted()){
                newHash++;
                if (newHash >= this.table.length){
                    return -1;
                }
            }
        }
        else if(this.collisionResolution.equals("quadratic_probing")){
            int step = 1;
            while(this.table[newHash] != null || !this.table[newHash].isDeleted()){
                newHash = nextPositionQuad(newHash, step);
                step++;
            }
        }

        //System.out.println("New Hash: " + newHash);
        return newHash;
    }

    private int nextPositionQuad(int oldHash, int i) {
        int ret =(int) (oldHash - pow(floor(i/2),i) * pow((-1), i));

        if(ret < 0){
            return ret % this.table.length;
        }
        else{
            return ret;
        }

    }

    public int hashKey(String key){
        String hash = "";
        key = key.substring(0,5);

        if(this.hashFunction.equals("division")){
            for(Character c: key.toCharArray()){
                hash += String.valueOf((int)c);
            }

            return (int) (Double.valueOf(hash) % this.table.length);
        }
        else if(this.hashFunction.equals("folding")){
            for(Character c: key.toCharArray()) {
                hash += String.valueOf((int) c);
            }

            int length = String.valueOf(this.table.length).length();
            int missingChar = hash.length() % length;

            String addIt = "";
            for(int i=0; i<missingChar; i++){
                addIt = addIt.concat("0");
            }
            hash = hash.concat(addIt);

            ArrayList<String> hashList = new ArrayList<>();

            for(int i=0; i < hash.length() / length; i++){
                hashList.add(hash.substring(i*length, length + i*length));
            }

            Collections.reverse(hashList);

            int sum = 0;

            for(int i=0; i < hashList.size(); i++){
                if (i % 2 == 0){
                    sum += Integer.valueOf(new StringBuilder(hashList.get(i)).reverse().toString());
                }
                else{
                    sum += Integer.valueOf(hashList.get(i));
                }
            }

            String foo = String.valueOf(sum);
            sum = Integer.valueOf(foo.substring(foo.length()-length, foo.length()));

            return sum % this.table.length;
        }
        else if(this.hashFunction.equals("mid_square")){
            
            return 0;
        }
        else{
            return 0;
        }
    }

    /**
	 * This method deletes the entry from the Hash-Table, having deleteKey as
	 * key This method returns the entry, having deleteKey as key if the
	 * deletion is successful and null if the key deleteKey is not found in the
	 * Hash-Table.
	 * 
	 * @param deleteKey
	 *            key of the entry to delete from the Hash-Table
	 * @return returns the deleted entry if the deletion ends successfully null
	 *         if the entry is not found in the Hash-Table
	 */
    public Entry delete(String deleteKey) {
        int hash = hashKey(deleteKey);

        if(find(deleteKey) == null){
            System.out.println("Not deleted. Key: " + deleteKey + " Hash: " + hash);
            return null;

        }
        else{
            while(true){
                if(this.table[hash].getKey().equals(deleteKey)){
                    this.table[hash].markDeleted();
                    break;
                }
                else{
                    hash = findNextPosition(hash);
                }
            }
            System.out.println("Deleted. Key: " + deleteKey + " Hash: " + hash);
            return this.table[hash];
        }
    }

    /**
	 * This method searches in the Hash-Table for the entry with key searchKey.
	 * It returns the entry, having searchKey as key if such an entry is found,
	 * null otherwise.
	 * 
	 * @param searchKey
	 *            key of the entry to find in the Hash-table
	 * @return returns the entry having searchKey as key if such an entry exists
	 *         null if the entry is not found in the Hash-Table
	 */
    public Entry find(String searchKey) {
        int oldHash = hashKey(searchKey);
        int hash = oldHash;

        if(this.table[hash] == null || this.table[hash].isDeleted()){
            System.out.println("Not found");
            return null;
        }

        while(true){
            if(this.table[hash].getKey().equals(searchKey) && !this.table[hash].isDeleted()){
                System.out.println("Found at pos " + hash);
                return this.table[hash];
            }

            hash = findNextPosition(hash);

            if(oldHash == hash || hash == -1){
                System.out.println("Not found");
                return null;
            }

        }

    }
    
    /**
	 * This method returns a ArrayList<String> containing the output Hash-Table.
	 * The output should be directly interpretable dot code. Each item in the
	 * ArrayList corresponds to one line of the output Hash-Table. The nodes of the
	 * output Hash-Table should contain the keys of the entries and also the
	 * data.
	 * 
	 * @return returns the output Hash-Table in directly interpretable dot code
	 */    
    public ArrayList<String> getHashTable() {
        /**
         * Add your code here
    	 */
    	return null;
    }

 

    /**
	 * This method increases the capacity of the Hash-Table and reorganizes it,
	 * in order to accommodate and access its entries more efficiently. This
	 * method is called automatically when the load factor exceeds 75%. To
	 * increase the size of the Hash-Table, you multiply the actual capacity by
	 * 10 and search for the closest primary number less than the result of this
	 * multiplication. For example if the actual capacity of the Hash-Table is
	 * 101, the capacity will be increased to 1009, which is the closest primary
	 * number less than (101*10).
	 */    
    private void rehash() {
        /**
         * Add your code here
    	 */
    }
}


