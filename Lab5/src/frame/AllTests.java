package frame;
import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import lab.*;

import static org.junit.Assert.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;


public class AllTests{

	@Rule
	public Timeout globalTimeout = new Timeout(10000);
	
	/////////////////////////////////DivisionLinear
	
	
	@Test
	public void testReadTestFile1_DivisionLinear() {
		HashTable table = new HashTable(10, "division", "linear_probing");
		int loaded = table.loadFromFile("TestFile1");
		assertTrue("Didn't load 19 entries from TestFile1 with division and linear_probing.",loaded  == 19);
	}

	@Test
	public void testInsert_DivisionLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "division", "linear_probing");
		assertTrue(table.insert(testEntry1));		
	}
	@Test
	public void testFind_DivisionLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		HashTable table = new HashTable(10, "division", "linear_probing");
	
		boolean inserted = table.insert(testEntry1);
		assertTrue(inserted);
		Entry foundEntry = table.find(testEntry1.getKey());
		assertNotNull("Didn't find Entry " + testEntry1.getKey() + ".", foundEntry);
		assertTrue("Inserted Entry " + testEntry1.getKey() + " and found Entry " + foundEntry.getKey() + " are not the same.", foundEntry == testEntry1);
	}
	
	
	@Test
	public void testDelete_DivisionLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "division", "linear_probing");
		
		boolean inserted = table.insert(testEntry1);
		assertTrue(inserted);
		
		Entry deletedEntry = table.delete(testEntry1.getKey());
		assertNotNull(deletedEntry);
		
		Entry e = table.find(testEntry1.getKey());
		assertNull(e);
	}
	
	@Test
	public void testHomeAdress_DivisionLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "division", "linear_probing");
		table.insert(testEntry1);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry1.getKey());
		assertTrue(testEntry1.getKey()+" is not at home adress.", adresses == null);
	}
	
	@Test
	public void testCollisionAdress_DivisionLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		Entry testEntry2 = new Entry();
		testEntry2.setKey("X8IG4LDXS");
		testEntry2.setData("OK");
		
		HashTable table = new HashTable(10, "division", "linear_probing");
		table.insert(testEntry1);
		table.insert(testEntry2);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry2.getKey());
		assertTrue(testEntry2.getKey() + " is at home adress.", adresses != null);
		assertTrue(testEntry2.getKey() + " should have home adress 2.", adresses.length == 1 && adresses[0] == 2);
	}

	
	///////////////////////////DivisionQuadratic
	@Test
	public void testReadTestFile1_DivisionQuadratic() {
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		assertTrue("Didn't load 19 entries from TestFile1 with division and quadratic_probing.",table.loadFromFile("TestFile1") == 19);
	}
	
	
	@Test
	public void testInsert_DivisionQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));		
	}
	
	@Test
	public void testDelete_DivisionQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));
		assertNotNull(table.delete(testEntry1.getKey()));
		assertNull(table.find(testEntry1.getKey()));	
	}
	@Test
	public void testFind_DivisionQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));
		Entry foundEntry = table.find(testEntry1.getKey());
		assertNotNull("Didn't find Entry " + testEntry1.getKey() + ".", foundEntry);
		assertTrue("Inserted Entry " + testEntry1.getKey() + " and found Entry " + foundEntry.getKey() + " are not the same.", foundEntry == testEntry1);
	}
	
	@Test
	public void testHomeAdress_DivisionQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		table.insert(testEntry1);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry1.getKey());
		assertTrue(testEntry1.getKey()+" is not at home adress.", adresses == null);
	}
	
	@Test
	public void testCollisionAdress_DivisionQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");	
		Entry testEntry2 = new Entry();
		testEntry2.setKey("X8IG4LDXS");
		testEntry2.setData("OK");	
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		table.insert(testEntry1);
		table.insert(testEntry2);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry2.getKey());
		assertTrue(testEntry2.getKey() + " is at home adress.", adresses != null);
		assertTrue(testEntry2.getKey() + " should have home adress 2.", adresses.length == 1 && adresses[0] == 2);
	}
		

	//////////////////////////////////////////////////MidSquareLinear
	
	
	@Test
	public void testReadTestFile1_MidSquareLinear() {
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		assertTrue("Didn't load 19 entries from TestFile1 with mid_square and linear_probing.",table.loadFromFile("TestFile1") == 19);
	}


	@Test
	public void testInsert_MidSquareLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		assertNotNull(table.insert(testEntry1));		
	}

	
	@Test
	public void testFind_MidSquareLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		assertNotNull(table.insert(testEntry1));
		Entry foundEntry = table.find(testEntry1.getKey());
		assertNotNull("Didn't find Entry " + testEntry1.getKey() + ".", foundEntry);
		assertTrue("Inserted Entry " + testEntry1.getKey() + " and found Entry " + foundEntry.getKey() + " are not the same.", foundEntry == testEntry1);
	}

	@Test
	public void testDelete_MidSquareLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		assertNotNull(table.insert(testEntry1));
		assertNotNull(table.delete(testEntry1.getKey()));
		assertNull(table.find(testEntry1.getKey()));		
	}
	

	
	@Test
	public void testHomeAdress_MidSquareLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		table.insert(testEntry1);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry1.getKey());
		assertTrue(testEntry1.getKey()+" is not at home adress.", adresses == null);
	}
	
	@Test
	public void testCollisionAdress_MidSquareLinear() {

	
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		
		testEntry1.setData("OK");
		
		Entry testEntry2 = new Entry();
		testEntry2.setKey("F5HF8MECA");
		testEntry2.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		table.insert(testEntry1);
		table.insert(testEntry2);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry2.getKey());
		assertTrue(testEntry2.getKey() + " is at home adress.", adresses != null);
		assertTrue(testEntry2.getKey() + " should have home adress 3.", adresses.length == 1 && adresses[0] == 0);
	
	}
	
	
	
	////////////////////////////MidSquareQuadractic
	
	@Test
	public void testReadTestFile1_MidSquareQuadractic() {
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		assertTrue("Didn't load 19 entries from TestFile1 with mid_square and quadratic_probing.",table.loadFromFile("TestFile1") == 19);
	}
	
	@Test
	public void testInsert_MidSquareQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));		
	}
	
	@Test
	public void testFind_MidSquareQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));
		Entry foundEntry = table.find(testEntry1.getKey());
		assertNotNull("Didn't find Entry " + testEntry1.getKey() + ".", foundEntry);
		assertTrue("Inserted Entry " + testEntry1.getKey() + " and found Entry " + foundEntry.getKey() + " are not the same.", foundEntry == testEntry1);
	}
	@Test
	public void testDelete_MidSquareQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));
		assertNotNull(table.delete(testEntry1.getKey()));
		assertNull(table.find(testEntry1.getKey()));		
	}
	

	@Test
	public void testHomeAdress_MidSquareQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		table.insert(testEntry1);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry1.getKey());
		assertTrue(testEntry1.getKey()+" is not at home adress.", adresses == null);
	}
	@Test
	public void testCollisionAdress_MidSquareQuadratic() {
		
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		
		testEntry1.setData("OK");
		
		Entry testEntry2 = new Entry();
		testEntry2.setKey("F5HF8MECA");
		testEntry2.setData("OK");
		
	
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		table.insert(testEntry1);
		table.insert(testEntry2);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry2.getKey());
		assertTrue(testEntry2.getKey() + " is at home adress.", adresses != null);
		assertTrue(testEntry2.getKey() + " should have home adress 0.", adresses.length == 1 && adresses[0] == 0);
	}
	
	
	//////////////////////////////////FoldingLinear

	@Test
	public void testReadTestFile1_FoldingLinear() {
		HashTable table = new HashTable(10, "folding", "linear_probing");
		assertTrue("Didn't load 19 entries from TestFile1 with folding and linear_probing.",table.loadFromFile("TestFile1") == 19);
	}
	
	@Test
	public void testInsert_FoldingLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "linear_probing");
		assertNotNull(table.insert(testEntry1));		
	}
	
	@Test
	public void testFind_FoldingLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "linear_probing");
		assertNotNull(table.insert(testEntry1));
		Entry foundEntry = table.find(testEntry1.getKey());
		assertNotNull("Didn't find Entry " + testEntry1.getKey() + ".", foundEntry);
		assertTrue("Inserted Entry " + testEntry1.getKey() + " and found Entry " + foundEntry.getKey() + " are not the same.", foundEntry == testEntry1);
	}
	
	@Test
	public void testDelete_FoldingLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "linear_probing");
		assertNotNull(table.insert(testEntry1));
		assertNotNull(table.delete(testEntry1.getKey()));
		assertNull(table.find(testEntry1.getKey()));	
	}

	@Test
	public void testHomeAdress_FoldingLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "linear_probing");
		table.insert(testEntry1);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry1.getKey());
		assertTrue(testEntry1.getKey()+" is not at home adress.", adresses == null);
	}
	
	@Test
	public void testCollisionAdress_FoldingLinear() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		Entry testEntry2 = new Entry();
		testEntry2.setKey("Z7IG5LDXS");
		testEntry2.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "linear_probing");
		table.insert(testEntry1);
		table.insert(testEntry2);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry2.getKey());
		
		assertTrue(testEntry2.getKey() + " is at home adress.", adresses != null);
		assertTrue(testEntry2.getKey() + " should have home adress 5.", adresses.length == 1 && adresses[0] == 5);
		
	}
	
//////////////////////////////////FoldingLinear
	
	@Test
	public void testReadTestFile1_FoldingQuadratic() {
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		assertTrue("Didn't load 19 entries from TestFile1 with folding and quadratic_probing.",table.loadFromFile("TestFile1") == 19);
	}
	

	@Test
	public void testInsert_FoldingQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));		
	}
	
	@Test
	public void testFind_FoldingQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));
		Entry foundEntry = table.find(testEntry1.getKey());
		assertNotNull("Didn't find Entry " + testEntry1.getKey() + ".", foundEntry);
		assertTrue("Inserted Entry " + testEntry1.getKey() + " and found Entry " + foundEntry.getKey() + " are not the same.", foundEntry == testEntry1);
	}

	@Test
	public void testDelete_FoldingQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("ABCDELDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		assertNotNull(table.insert(testEntry1));
		assertNotNull(table.delete(testEntry1.getKey()));
		assertNull(table.find(testEntry1.getKey()));
	}
	
	@Test
	public void testHomeAdress_FoldingQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		table.insert(testEntry1);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry1.getKey());
		assertTrue(testEntry1.getKey()+" is not at home adress.", adresses == null);
	}

	@Test
	public void testCollisionAdress_FoldingQuadratic() {
		Entry testEntry1 = new Entry();
		testEntry1.setKey("Z8IG4LDXS");
		testEntry1.setData("OK");
		
		Entry testEntry2 = new Entry();
		testEntry2.setKey("Z7IG5LDXS");
		testEntry2.setData("OK");
		
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		table.insert(testEntry1);
		table.insert(testEntry2);
		ArrayList<String> tableArrayList = table.getHashTable();
		int[] adresses = getAdresses(tableArrayList, testEntry2.getKey());
		assertTrue(testEntry2.getKey() + " is at home adress.", adresses != null);
		assertTrue(testEntry2.getKey() + " should have home adress 5.", adresses.length == 1 && adresses[0] == 5);
	}
	
	
	
	private int[] getAdresses(ArrayList<String> table, String key) {
		
		for (String line : table) {
			if(line.matches(".*"+key+".*")) {
				String[] lastPart = line.split("\\x7C");
				if(lastPart.length == 3) {
					String allNumbersAndEnd = lastPart[line.split("\\x7C").length - 1].substring(0, lastPart[line.split("\\x7C").length - 1].indexOf("}"));
					String[] allNumbers = allNumbersAndEnd.split(",");
					int[] numbers = new int[allNumbers.length];
					for (int i = 0; i < allNumbers.length; i++) {
						numbers[i] = Integer.valueOf(allNumbers[i].trim());
					}
					return numbers;
				}
			}
		}
		
		return null;
	}
	
	private void printArrayList(ArrayList<String> dot) {
		try {
			FileWriter fw = new FileWriter("test.txt");			
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (String string : dot) {
				bw.write(string + System.getProperty("line.separator"));
			}
			
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
