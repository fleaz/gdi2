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
	public Timeout globalTimeout = new Timeout(30000);


	
	@Test
	public void testLargeAddressLengths_FoldingLinear()
	{
		///generate 1250 record
		ArrayList<String> testData = getTestData();
		
		String testkey = "ABCDEANXA";
		String KeyAtHomePosition = "ABCDEANBA";
		
		HashTable table = new HashTable(10, "folding", "linear_probing");
		for(String s : testData)
		{
			Entry e = new Entry();
			e.setKey(s);
			e.setData("ok");
			table.insert(e);
		}
		
		ArrayList<String> dot =  table.getHashTable();
		//this.printArrayList(dot, "largeFileFoldingLinear");
		int [] addresses = this.getAdresses(dot , testkey);
		int [] addresses2 = this.getAdresses(dot , KeyAtHomePosition);
		int tableCapacity = this.getCapacity(dot);

		assertEquals("folding error at large addresses",44 ,addresses.length);
		assertEquals("Hashtable capacity must be equal to 9661 ", 9661,tableCapacity);
		assertNull("key ABCDEANBA must be at home position",addresses2);
	}
	
	
	
	@Test
	public void testLargeAddressLengths_FoldingQuadratic()
	{
		
		ArrayList<String> testData = getTestData();
		
		String testkey = "ABCDEAJQA";
		String KeyAtHomePosition = "ABCDEALJA";
		
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		for(String s : testData)
		{
			Entry e = new Entry();
			e.setKey(s);
			e.setData("ok");
			table.insert(e);
		}
		
		ArrayList<String> dot =  table.getHashTable();
		//this.printArrayList(dot, "largeFileFoldingQuadratic");
		int [] addresses = this.getAdresses(dot , testkey);
		int [] addresses2 = this.getAdresses(dot , KeyAtHomePosition);
		int tableCapacity = this.getCapacity(dot);

		assertEquals("folding error at large addresses",160 ,addresses.length);
		assertEquals("Hashtable capacity must be equal to 9661 ", 9661,tableCapacity);
		assertNull("key ABCDEALJA must be at home position",addresses2);
	}
	
	
	
	@Test
	public void testLargeAddressLengths_MidsquareLinear()
	{
		
		ArrayList<String> testData = getTestData();
		
		String testkey = "ABCDEAJQA";
		String KeyAtHomePosition = "ABCDEAAAA";
		
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		for(String s : testData)
		{
			Entry e = new Entry();
			e.setKey(s);
			e.setData("ok");
			table.insert(e);
		}
		
		ArrayList<String> dot =  table.getHashTable();
		//this.printArrayList(dot, "largeFileMidsquareLinear");
		int [] addresses = this.getAdresses(dot , testkey);
		int [] addresses2 = this.getAdresses(dot , KeyAtHomePosition);
		int tableCapacity = this.getCapacity(dot);
		
		assertEquals("folding error at large addresses",482 ,addresses.length);
		assertEquals("Hashtable capacity must be equal to 9661 ", 9661,tableCapacity);


		assertNull("key ABCDEALJA must be at home position",addresses2);
	}
	
	@Test
	public void testLargeAddressLengths_MidsquareQuadratic()
	{
		
		ArrayList<String> testData = getTestData();
		
		String testkey = "ABCDEAJQA";
		String KeyAtHomePosition = "ABCDEACLB";
		
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		for(String s : testData)
		{
			Entry e = new Entry();
			e.setKey(s);
			e.setData("ok");
			table.insert(e);
		}
		
		ArrayList<String> dot =  table.getHashTable();
		//this.printArrayList(dot, "largeFileMidsquareQuadratic");
		int [] addresses = this.getAdresses(dot , testkey);
		int [] addresses2 = this.getAdresses(dot , KeyAtHomePosition);
		int tableCapacity = this.getCapacity(dot);
		
		assertEquals("folding error at large addresses",736 ,addresses.length);
		assertEquals("Hashtable capacity must be equal to 9661 ", 9661,tableCapacity);
		assertNull("key ABCDEALJA must be at home position",addresses2);
	}
	
	
	
	
	@Test
	public void testCollisionsTestFile2_DivisionLinear()
	{
		HashTable table = new HashTable(10, "division", "linear_probing");
		table.loadFromFile("TestFile2");		
		ArrayList<String> dotOutput = table.getHashTable();
		
		int [] addresses = this.getAdresses(dotOutput, "S04XXGKQ3");
		assertNotNull("a collision must have happened for key S04XXGKQ3",addresses);
		assertEquals("Home address must be at 144", 144, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "DJF2K2E7O");
		assertNotNull("a collision must have happened for key DJF2K2E7O",addresses);
		assertEquals("Home address must be at 371", 371, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "9SIVZX8HM");
		assertNotNull("a collision must have happened for key 9SIVZX8HM",addresses);
		assertEquals("Home address must be at 485", 485, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "JQ645DIXK");
		assertNotNull("a collision must have happened for key JQ645DIXK",addresses);
		assertEquals("Home address must be at 666", 666, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "GZYOHTCZ5");
		assertNotNull("a collision must have happened for key GZYOHTCZ5",addresses);
		assertEquals("Home address must be at 707", 707, addresses[0]);
	}
	
	@Test
	public void testCorrectPositionTestFile2_DivisionLinear()
	{
		HashTable table = new HashTable(10, "division", "linear_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		int [] addresses = this.getAdresses(dotOutput, "XQ6GDLTHX");
		assertNull("key XQ6GDLTHX is not at original position",addresses);		
		int position = getPosition(dotOutput, "XQ6GDLTHX");
		assertEquals("Home address must be at 288", 288, position);
		
		addresses = this.getAdresses(dotOutput, "PBNXCLREK");
		assertNull("key PBNXCLREK is not at original position",addresses);		
		position = getPosition(dotOutput, "PBNXCLREK");
		assertEquals("Home address must be at 408", 408, position);
		
		addresses = this.getAdresses(dotOutput, "C4X08HFX4");
		assertNull("key C4X08HFX4 is not at original position",addresses);		
		position = getPosition(dotOutput, "C4X08HFX4");
		assertEquals("Home address must be at 878", 878, position);
	}
	
	@Test
	public void testCapacityTestFile2_DivisionLinear()
	{
		HashTable table = new HashTable(10, "division", "linear_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		assertEquals("Capacity must be 967", 967, getCapacity(dotOutput));
	}
	///////////////////////////DivisionQuadratic
	
	@Test
	public void testCollisionsTestFile2_DivisionQuadratic()
	{
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		table.loadFromFile("TestFile2");		
		ArrayList<String> dotOutput = table.getHashTable();
		
		int [] addresses = this.getAdresses(dotOutput, "S04XX3HO6");
		assertNotNull("a collision must have happened for key S04XX3HO6",addresses);
		assertEquals("Home address must be at 144", 144, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "DJF2K2E7O");
		assertNotNull("a collision must have happened for key DJF2K2E7O",addresses);
		assertEquals("Home address must be at 371", 371, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "9SIVZX8HM");
		assertNotNull("a collision must have happened for key 9SIVZX8HM",addresses);
		assertEquals("Home address must be at 485", 485, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "XHOY5BAKC");
		assertNotNull("a collision must have happened for key XHOY5BAKC",addresses);
		assertEquals("Home address must be at 522", 522, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "JQ645DIXK");
		assertNotNull("a collision must have happened for key JQ645DIXK",addresses);
		assertEquals("Home address must be at 666", 666, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "GZYOHTCZ5");
		assertNotNull("a collision must have happened for key GZYOHTCZ5",addresses);
		assertEquals("Home address must be at 707", 707, addresses[0]);
	}
	
	
	@Test
	public void testCorrectPositionTestFile2_DivisionQuadratic()
	{
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		int [] addresses = this.getAdresses(dotOutput, "G4BFF9ZFS");
		assertNull("key G4BFF9ZFS is not at original position",addresses);		
		int position = getPosition(dotOutput, "G4BFF9ZFS");
		assertEquals("Home address must be at 150", 150, position);
		
		addresses = this.getAdresses(dotOutput, "6QCD2DO9K");
		assertNull("key 6QCD2DO9K is not at original position",addresses);		
		position = getPosition(dotOutput, "6QCD2DO9K");
		assertEquals("Home address must be at 435", 435, position);
		
		addresses = this.getAdresses(dotOutput, "A3HSF7DJL");
		assertNull("key A3HSF7DJL is not at original position",addresses);		
		position = getPosition(dotOutput, "A3HSF7DJL");
		assertEquals("Home address must be at 699", 699, position);
	}
	
	
	@Test
	public void testCapacityTestFile2_DivisionQuadratic()
	{
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		assertEquals("Capacity must be 967", 967, getCapacity(dotOutput));
	}
	/////////////////////////////////mid_squareLinear
	
	@Test
	public void testCollisionsTestFile2_MidSquareLinear()
	{
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		table.loadFromFile("TestFile2");		
		ArrayList<String> dotOutput = table.getHashTable();
		
		int [] addresses = this.getAdresses(dotOutput, "UGWAQDPJJ");
		assertNotNull("a collision must have happened for key UGWAQDPJJ",addresses);
		assertEquals("Home address must be at 119", 119, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "9SIVZX8HM");
		assertNotNull("a collision must have happened for key 9SIVZX8HM",addresses);
		assertEquals("Home address must be at 234", 234, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "7M5BEM4PI");
		assertNotNull("a collision must have happened for key 7M5BEM4PI",addresses);
		assertEquals("Home address must be at 294", 294, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "TTJADFKQ4");
		assertNotNull("a collision must have happened for key TTJADFKQ4",addresses);
		assertEquals("Home address must be at 323", 323, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "JQ645DIXK");
		assertNotNull("a collision must have happened for key JQ645DIXK",addresses);
		assertEquals("Home address must be at 372", 372, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "S04XXGKQ3");
		assertNotNull("a collision must have happened for key S04XXGKQ3",addresses);
		assertEquals("Home address must be at 593", 593, addresses[0]);
		
		
		addresses = this.getAdresses(dotOutput, "Q04KJYCC4");
		assertNotNull("a collision must have happened for key Q04KJYCC4",addresses);
		assertEquals("Home address must be at 624", 624, addresses[0]);
	}
	
	
	@Test
	public void testCorrectPositionTestFile2_MidSquareLinear()
	{
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		int [] addresses = this.getAdresses(dotOutput, "6QCD2DO9K");
		assertNull("key 6QCD2DO9K is not at original position",addresses);		
		int position = getPosition(dotOutput, "6QCD2DO9K");
		assertEquals("Home address must be at 87", 87, position);
		
		addresses = this.getAdresses(dotOutput, "T29EP81RL");
		assertNull("key T29EP81RL is not at original position",addresses);		
		position = getPosition(dotOutput, "T29EP81RL");
		assertEquals("Home address must be at 294", 294, position);
		
		addresses = this.getAdresses(dotOutput, "EFL0DH5HI");
		assertNull("key EFL0DH5HI is not at original position",addresses);		
		position = getPosition(dotOutput, "EFL0DH5HI");
		assertEquals("Home address must be at 844", 844, position);
	}
	
	
	@Test
	public void testCapacityTestFile2_MidSquareLinear()
	{
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		assertEquals("Capacity must be 967", 967, getCapacity(dotOutput));
	}

	
	
	/////////////////////////////////mid_squareQuadratic
	
	@Test
	public void testCollisionsTestFile2_MidSquareQuadratic()
	{
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		table.loadFromFile("TestFile2");		
		ArrayList<String> dotOutput = table.getHashTable();
		
		int [] addresses = this.getAdresses(dotOutput, "UGWAQDPJJ");
		assertNotNull("a collision must have happened for key UGWAQDPJJ",addresses);
		assertEquals("Home address must be at 119", 119, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "9SIVZX8HM");
		assertNotNull("a collision must have happened for key 9SIVZX8HM",addresses);
		assertEquals("Home address must be at 234", 234, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "7M5BEM4PI");
		assertNotNull("a collision must have happened for key 7M5BEM4PI",addresses);
		assertEquals("Home address must be at 294", 294, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "TTJADFKQ4");
		assertNotNull("a collision must have happened for key TTJADFKQ4",addresses);
		assertEquals("Home address must be at 323", 323, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "JQ645DIXK");
		assertNotNull("a collision must have happened for key JQ645DIXK",addresses);
		assertEquals("Home address must be at 372", 372, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "S04XXGKQ3");
		assertNotNull("a collision must have happened for key S04XXGKQ3",addresses);
		assertEquals("Home address must be at 593", 593, addresses[0]);
		
		
		addresses = this.getAdresses(dotOutput, "Q04KJYCC4");
		assertNotNull("a collision must have happened for key Q04KJYCC4",addresses);
		assertEquals("Home address must be at 624", 624, addresses[0]);
	}
	
	
	@Test
	public void testCorrectPositionTestFile2_MidSquareQuadratic()
	{
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		int [] addresses = this.getAdresses(dotOutput, "XQ6GDLTHX");
		assertNull("key XQ6GDLTHX is not at original position",addresses);		
		int position = getPosition(dotOutput, "XQ6GDLTHX");
		assertEquals("Home address must be at 97", 97, position);
		
		addresses = this.getAdresses(dotOutput, "G4BFF9ZFS");
		assertNull("key G4BFF9ZFS is not at original position",addresses);		
		position = getPosition(dotOutput, "G4BFF9ZFS");
		assertEquals("Home address must be at 214", 214, position);
		
		addresses = this.getAdresses(dotOutput, "8AJFD2PFF");
		assertNull("key 8AJFD2PFF is not at original position",addresses);		
		position = getPosition(dotOutput, "8AJFD2PFF");
		assertEquals("Home address must be at 838", 838, position);
	}
	
	
	@Test
	public void testCapacityTestFile2_MidSquareQuadratic()
	{
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		assertEquals("Capacity must be 967", 967, getCapacity(dotOutput));
	}
	
	
	
	/////////////////////////////////foldingLinear
	
	@Test
	public void testCollisionsTestFile2_FoldingLinear()
	{
		HashTable table = new HashTable(10, "folding", "linear_probing");
		table.loadFromFile("TestFile2");		
		ArrayList<String> dotOutput = table.getHashTable();
		
		int [] addresses = this.getAdresses(dotOutput, "JQ645DIXK");
		assertNotNull("a collision must have happened for key JQ645DIXK",addresses);
		assertEquals("Home address must be at 88", 88, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "BR1LSTBSC");
		assertNotNull("a collision must have happened for key BR1LSTBSC",addresses);
		assertEquals("Home address must be at 175", 175, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "9SIVZX8HM");
		assertNotNull("a collision must have happened for key 9SIVZX8HM",addresses);
		assertEquals("Home address must be at 226", 226, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "S04XXGKQ3");
		assertNotNull("a collision must have happened for key S04XXGKQ3",addresses);
		assertEquals("Home address must be at 267", 267, addresses[0]);
		
	}
	
	
	@Test
	public void testCorrectPositionTestFile2_FoldingLinear()
	{
		HashTable table = new HashTable(10, "folding", "linear_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		int [] addresses = this.getAdresses(dotOutput, "HGY62F5SR");
		assertNull("key HGY62F5SR is not at original position",addresses);		
		int position = getPosition(dotOutput, "HGY62F5SR");
		assertEquals("Home address must be at 128", 128, position);
		
		addresses = this.getAdresses(dotOutput, "L97IOPCZY");
		assertNull("key L97IOPCZY is not at original position",addresses);		
		position = getPosition(dotOutput, "L97IOPCZY");
		assertEquals("Home address must be at 293", 293, position);
		
		addresses = this.getAdresses(dotOutput, "LMD2QXICN");
		assertNull("key LMD2QXICN is not at original position",addresses);		
		position = getPosition(dotOutput, "LMD2QXICN");
		assertEquals("Home address must be at 648", 648, position);
	}
	
	
	@Test
	public void testCapacityTestFile2_FoldingLinear()
	{
		HashTable table = new HashTable(10, "folding", "linear_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		assertEquals("Capacity must be 967", 967, getCapacity(dotOutput));
	}

	
	/////////////////////////////////foldingQuadratic
	
	@Test
	public void testCollisionsTestFile2_FoldingQuadratic()
	{
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		table.loadFromFile("TestFile2");		
		ArrayList<String> dotOutput = table.getHashTable();
		
		int [] addresses = this.getAdresses(dotOutput, "JQ645DIXK");
		assertNotNull("a collision must have happened for key JQ645DIXK",addresses);
		assertEquals("Home address must be at 88", 88, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "BR1LSTBSC");
		assertNotNull("a collision must have happened for key BR1LSTBSC",addresses);
		assertEquals("Home address must be at 175", 175, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "9SIVZX8HM");
		assertNotNull("a collision must have happened for key 9SIVZX8HM",addresses);
		assertEquals("Home address must be at 226", 226, addresses[0]);
		
		addresses = this.getAdresses(dotOutput, "S04XXGKQ3");
		assertNotNull("a collision must have happened for key S04XXGKQ3",addresses);
		assertEquals("Home address must be at 267", 267, addresses[0]);
	}
	
	
	@Test
	public void testCorrectPositionTestFile2_FoldingQuadratic()
	{
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		int [] addresses = this.getAdresses(dotOutput, "XQ6GDLTHX");
		assertNull("key XQ6GDLTHX is not at original position",addresses);		
		int position = getPosition(dotOutput, "XQ6GDLTHX");
		assertEquals("Home address must be at 604", 604, position);
		
		addresses = this.getAdresses(dotOutput, "G4BFF9ZFS");
		assertNull("key G4BFF9ZFS is not at original position",addresses);		
		position = getPosition(dotOutput, "G4BFF9ZFS");
		assertEquals("Home address must be at 28", 28, position);
		
		addresses = this.getAdresses(dotOutput, "8AJFD2PFF");
		assertNull("key 8AJFD2PFF is not at original position",addresses);		
		position = getPosition(dotOutput, "8AJFD2PFF");
		assertEquals("Home address must be at 178", 178, position);
	}
	
	
	@Test
	public void testCapacityTestFile2_FoldingQuadratic()
	{
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		table.loadFromFile("TestFile2");
		ArrayList<String> dotOutput = table.getHashTable();
		assertEquals("Capacity must be 967", 967, getCapacity(dotOutput));
	}
	
	
	
	
	
	
	/////////////////////////////////DivisionLinear
	
	
	@Test
	public void testReadTestFile1_DivisionLinear() {
		HashTable table = new HashTable(10, "division", "linear_probing");
		int loaded = table.loadFromFile("TestFile1");
		assertTrue("Didn't load 19 entries from TestFile1 with division and linear_probing.",loaded  == 19);
	}
	
	
	@Test
	public void testReadTestFile2_DivisionLinear() {
		HashTable table = new HashTable(10, "division", "linear_probing");
		int loaded = table.loadFromFile("TestFile2");
		//this.printArrayList(table.getHashTable(),"DivisionLinearFile2");
		assertTrue("Didn't load 99 entries from TestFile2 with division and linear_probing.",loaded == 99);
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
	public void testReadTestFile2_DivisionQuadratic() {
		HashTable table = new HashTable(10, "division", "quadratic_probing");
		assertTrue("Didn't load 99 entries from TestFile2 with division and quadratic_probing.",table.loadFromFile("TestFile2") == 99);
		//this.printArrayList(table.getHashTable(),"DivisionQuadraticFile2");
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
	public void testReadTestFile2_MidSquareLinear() {
		HashTable table = new HashTable(10, "mid_square", "linear_probing");
		assertTrue("Didn't load 99 entries from TestFile2 with mid_square and linear_probing.",table.loadFromFile("TestFile2") == 99);
		//this.printArrayList(table.getHashTable(),"MidSquareLinearFile2");
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
		assertTrue(testEntry2.getKey() + " should have home adress 0.", adresses.length == 1 && adresses[0] == 0);
	
	}
	
	
	
	////////////////////////////MidSquareQuadractic
	
	@Test
	public void testReadTestFile1_MidSquareQuadractic() {
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		assertTrue("Didn't load 19 entries from TestFile1 with mid_square and quadratic_probing.",table.loadFromFile("TestFile1") == 19);
	}
	
	
	
	@Test
	public void testReadTestFile2_MidSquareQuadractic() {
		HashTable table = new HashTable(10, "mid_square", "quadratic_probing");
		int loaded = table.loadFromFile("TestFile2");
		//this.printArrayList(table.getHashTable(),"MidsquareQuadraticFile2");
		assertTrue("Didn't load 99 entries from TestFile2 with mid_square and quadratic_probing.",loaded == 99);
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
	public void testReadTestFile2_FoldingLinear() {
		HashTable table = new HashTable(10, "folding", "linear_probing");
		assertTrue("Didn't load 99 entries from TestFile2 with folding and linear_probing.",table.loadFromFile("TestFile2") == 99);
		//this.printArrayList(table.getHashTable(),"FoldingLinearFile2");
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
	public void testReadTestFile2_FoldingQuadratic() {
		HashTable table = new HashTable(10, "folding", "quadratic_probing");
		assertTrue("Didn't load 99 entries from TestFile2 with folding and quadratic_probing.",table.loadFromFile("TestFile2") == 99);
		//this.printArrayList(table.getHashTable(),"FoldingQuadraticFile2");
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
	

	
	private int getPosition(ArrayList<String> table, String key) {

		String nodeName ="";
		int position =-1;
		for (String line : table) {
			if(line.matches(".*"+key+".*")) {
				String [] parts = line.split("\\x5B");
				nodeName = parts[0];
				break;
				
				}
			}
		if(!nodeName.equals(""))
		{
			for (String line : table) {
				if(line.matches(".*"+nodeName+":"+".*")) {
					String [] parts = line.split("\\x2D\\x3E");
					if(parts.length > 1)
					{
						String firstPart = parts[0];
						firstPart = firstPart.replace("ht:f", "");
						position = Integer.parseInt(firstPart);
						break;
					}
				}
				
			}
		}
		
		
		return position;
	}

private int getCapacity(ArrayList<String> table) {
		int capacity = 0;
		
		String line = table.get(4);
		
		String []parts = line.split("\\x7C");
			
		String lastEntry = parts[parts.length-1];
			
		int firstIndex = lastEntry.indexOf(">")	;
		int lastIndex = lastEntry.indexOf("\"");
			
		lastEntry = lastEntry.substring(firstIndex+1, lastIndex);
		
		capacity = Integer.parseInt(lastEntry);
			
		return capacity + 1;
		
	}
	
	private void printArrayList(ArrayList<String> dot,String fileName) {
		try {
			FileWriter fw = new FileWriter(fileName+".txt");			
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
	

	private ArrayList<String> getTestData()
	{
		ArrayList<String> testData = new ArrayList<>();
		String keyPrefix = "ABCDEA";
		for(char c1= 'A';c1 !='Z' ;c1++)
		{
			for(char c2= 'A';c2 !='Z' ;c2++)
			{
				for(char c3= 'A';c3 !='C' ;c3++)
				{
					testData.add(keyPrefix+c1+c2+c3);
				}
			}
		}
		return testData;
	}

}
