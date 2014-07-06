package lab;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import frame.Entry;

import static java.lang.Math.ceil;
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
    private String[] sequences = null;
    private String hashFunction, collisionResolution;
    private int fillLevel = 0;


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
        this.sequences = new String[k];
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
        System.out.println("- Starting to insert");
        String key = insertEntry.getKey();
        int hash = hashKey(key);
        int homeAddr = hash;

        if(this.table[hash] == null || this.table[hash].isDeleted()){ // check home addr
            System.out.println("- Easy inserted " + key + " with hash " + homeAddr + " at Position " + hash);
            this.table[hash] = insertEntry;
            this.fillLevel++;
            checkIfExpansionIsNeeded();
            return true;
        }

        hash = findNextPositionToInsert(key);

        if(this.table[hash] == null || this.table[hash].isDeleted()){ // check next found position
            this.table[hash] = insertEntry;
            System.out.println("- Inserted " + key + " with hash " + homeAddr +" at Position " + hash);
            this.fillLevel++;
            checkIfExpansionIsNeeded();
            return true;
        }
        else{   // found key
            System.out.println("- Not inserted " + key + " was already at pos "+ hash);
            return false;
        }
    }

    private void checkIfExpansionIsNeeded() {
        if(this.fillLevel > this.table.length * 0.75){
            rehash();
        }
    }

    // Returns the next position where an entry can be inserted or the position where the key is found
    private int findNextPositionToInsert(String key) {
        int newHash = hashKey(key);
        String sequence = new String();
        boolean overflow = false;

        if(this.collisionResolution.equals("linear_probing")){
            //System.out.println(this.table[newHash]);
            while(this.table[newHash] != null && !this.table[newHash].isDeleted()){

                if(this.table[newHash].getKey().equals(key) && !this.table[newHash].isDeleted()){
                    return newHash; // already in list
                }
                if (newHash == this.table.length-1 && !overflow){ // start again from the beginning
                    newHash -= this.table.length-1;
                    overflow = true;
                }
                sequence = sequence + newHash + ", ";
                newHash++;
            }
        }
        else if(this.collisionResolution.equals("quadratic_probing")){
            int step = 1;
            while(this.table[newHash] != null && !this.table[newHash].isDeleted()){

                if( this.table[newHash].getKey().equals(key) && !this.table[newHash].isDeleted()){
                    return newHash; // already in list
                }
                sequence = sequence + newHash + ", ";
                newHash = nextPositionQuad(newHash, step)  % this.table.length;
                step++;

                if(newHash < 0){ // negative Values
                    newHash += this.table.length;
                }
            }
        }

        if(sequence.length() > 0){
            System.out.println("Seq:" + sequence);
            if(this.collisionResolution.equals("quadratic_probing")){
                this.sequences[newHash] = sequence.substring(0,sequence.length()-5); // Inserts the same thing twice so we have to delete it
            }
            else{
                this.sequences[newHash] = sequence.substring(0,sequence.length()-2);
            }

        }
        else{
            this.sequences[newHash] = "";
        }
        return newHash; // can be inserted here
    }



    private int nextPositionQuad(int oldHash, int i) {
        int ret =(int) (oldHash - ceil(pow(i / 2, 2)) * pow((-1), i));

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
        int length = String.valueOf(this.table.length - 1).length(); // e.g. length is 2 for 100 positions [0-99]

        for(Character c: key.toCharArray()){
            hash += String.valueOf((int)c);
        }

        if(this.hashFunction.equals("division")){
            return (int) (Double.valueOf(hash) % this.table.length);
        }
        else if(this.hashFunction.equals("folding")){
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
            double hashValue = Double.valueOf(hash) * Double.valueOf(hash);
            String hashString = String.valueOf(hashValue);
            int hashLength = hashString.length();

            System.out.printf("Hash:%.0f\n", hashValue);
            System.out.println("hashlength: " + hashLength + " length: " + length);

            String hashCut = hashString.substring(hashLength-9-length+1, hashLength-9+1);

            System.out.println("Substring from " + (hashLength-9-length+1) + " to " + (hashLength-9+1)); // TODO
            System.out.println("Cut: " + hashCut);
            int ret = Integer.valueOf(hashCut);
            return ret % this.table.length;

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

        if(this.table[hash] == null){
            return null;
        }
        int newHash = findNextPositionToInsert(deleteKey);

        if (this.table[newHash] == null || this.table[newHash].isDeleted()) {
            return null;
        } else {
            this.table[newHash].markDeleted();
            return this.table[newHash];
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
        int newHash = oldHash;

        if (this.table[newHash] == null) {
            System.out.println("Not found");
            return null;
        }

        newHash = findNextPositionToInsert(searchKey);

        if (this.table[newHash] == null || this.table[newHash].isDeleted()) {
            System.out.println("Not found");
            return null;
        } else {
            System.out.println("Found at pos " + newHash);
            return this.table[newHash];
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

        ArrayList<String> dot = new ArrayList<>();
        int[] positions = new int[this.table.length];

        // Construct Head
        dot.add("Digraph{");
        dot.add("nodesep=.05;");
        dot.add("rankdir=LR;");
        dot.add("node[shape=record,height=.1];");

        // Construct line #5
        String line5 = "ht[label=\"";
        for (int i=0; i < this.table.length; i++){
            line5 += "<f" + i + ">" + i + "|";
        }
        line5 = line5.substring(0,line5.length()-1);
        line5 += "\"];";
        dot.add(line5);

        // Insert nodes
        int nodeNumber = 1;
        for(int i=0; i < this.table.length; i++){
            Entry e = this.table[i];

            if (e != null && !e.isDeleted()) {
                String[] entryData = e.toString().split(";");

                String entry = entryData[0] + entryData[1] + "|" + entryData[2];

                if (this.sequences[i] != null && !this.sequences[i].equals("")) {
                    entry += "|" + this.sequences[i];
                }

                String node = "node" + nodeNumber + "[label=\"{<l>" + entry + "}\"];";
                positions[i] = nodeNumber;
                dot.add(node);
                nodeNumber++;
            }
        }


        // Insert ht
        for(int i=0; i < this.table.length; i++){
            Entry e = this.table[i];

            if(e != null && !e.isDeleted()){
                String ht ="ht:f" + i + "->node" + positions[i] + ":l;";
                dot.add(ht);
            }
        }
        dot.add("}");

        System.out.println("---");
        for(String s: this.sequences){
            System.out.println(s);
        }
        System.out.println("---");
        for(String s: dot){
            System.out.println(s);
        }
        System.out.println("---");


        return dot;
    }

    boolean isPrime(int n) {
        if (n % 2 == 0) return false;

        for (int i = 3; i * i <= n; i += 2) {
            if (n % i == 0)
                return false;
        }
        return true;
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
        System.out.println("REHASH!");
        Entry[] tmpEntrys = this.table.clone();

        int newSize = 0;

        //Calc new Size
        for (int i=this.table.length * 10; i > this.table.length; i--){
            if (isPrime(i)){
                newSize = i;
                break;
            }
        }

        this.table = new Entry[newSize];
        this.sequences = new String[newSize];

        for(Entry e: tmpEntrys){
            if(e != null && !e.isDeleted()){
                this.insert(e);
            }
        }
    }
}


