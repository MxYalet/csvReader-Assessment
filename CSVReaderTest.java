/*
 ***** Important!  Please Read! *****
 *
 *  - Do NOT remove any of the existing import statements
 *  - Do NOT import additional junit packages 
 *  - You MAY add in other non-junit packages as needed
 * 
 *  - Do NOT remove any of the existing test methods or change their name
 *  - You MAY add additional test methods.  If you do, they should all pass
 * 
 *  - ALL of your assert test cases within each test method MUST pass, otherwise the 
 *        autograder will fail that test method
 *  - You MUST write the require number of assert test cases in each test method, 
 *        otherwise the autograder will fail that test method
 *  - You MAY write more than the required number of assert test cases as long as they all pass
 * 
 *  - All of your assert test cases within a method must be related to the method they are meant to test
 *  - All of your assert test cases within a method must be distinct and non-trivial
 *  - Your test cases should reflect the method requirements in the homework instruction specification
 * 
 *  - Your assert test cases will be reviewed by the course instructors and they may take off
 *        points if your assert test cases to do not meet the requirements
 */

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;

class CSVReaderTest {
	
	@Test
	void testBasic() throws CSVFormatException, IOException {
		// TODO Write at least 5 test cases with assert statements. All cases must pass

		// These tests should check basic functionality of readRow()
		String csvFilePath = Paths.get("easy1.csv").toString();

		// Create a CSVReader instance with the provided file
		try (CharacterReader characterReader = new CharacterReader(new FileReader(csvFilePath))) {
			CSVReader csvReader = new CSVReader(characterReader);

			// Read each row
			String[] row1 = csvReader.readRow();
			assertArrayEquals(new String[]{"name", "age"}, row1);

			String[] row2 = csvReader.readRow();
			assertArrayEquals(new String[]{"hannah", "20"}, row2);

			String[] row3 = csvReader.readRow();
			assertArrayEquals(new String[]{"harry", "40"}, row3);

			String[] row4 = csvReader.readRow();
			assertArrayEquals(new String[]{"mary", "56"}, row4);

			String[] row5 = csvReader.readRow();
			assertArrayEquals(new String[]{"john", "18"}, row5);

			String[] row6 = csvReader.readRow();
			assertArrayEquals(new String[]{"yule", "25"}, row6);

			String[] row7 = csvReader.readRow();
			assertArrayEquals(new String[]{"jiexi", "32"}, row7);

			String[] row8 = csvReader.readRow();
			assertArrayEquals(new String[]{"lauren", "35"}, row8);

			// Verify there are no more rows
			assertNull(csvReader.readRow());
		}
}

	@Test
	void testLineEnd() throws CSVFormatException, IOException {
		// TODO Write at least 5 test cases with assert statements. All cases must pass
		// These tests should check different line ending cases
		String csvContentWindows = "name,age\r\nhannah,20\r\nharry,40\r\nmary,56\r\njohn,18\r\nyule,25\r\njiexi,32\r\nlauren,35";
		String csvContentMixed = "name,age\nhannah,20\r\nharry,40\nmary,56\r\njohn,18\nyule,25\r\njiexi,32\nlauren,35";


		// Test line endings (\r\n)
		try (CharacterReader characterReader = new CharacterReader(new StringReader(csvContentWindows))) {
			CSVReader csvReader = new CSVReader(characterReader);
			assertArrayEquals(new String[]{"name", "age"}, csvReader.readRow());
			assertArrayEquals(new String[]{"hannah", "20"}, csvReader.readRow());
			assertArrayEquals(new String[]{"harry", "40"}, csvReader.readRow());
		}

		// Test Mixed line endings (\n and \r\n)
		try (CharacterReader characterReader = new CharacterReader(new StringReader(csvContentMixed))) {
			CSVReader csvReader = new CSVReader(characterReader);
			assertArrayEquals(new String[]{"name", "age"}, csvReader.readRow());
			assertArrayEquals(new String[]{"hannah", "20"}, csvReader.readRow());
			assertArrayEquals(new String[]{"harry", "40"}, csvReader.readRow());

		} catch (CSVFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

		@Test
	void testPathological() {
		// TODO Write at least 5 test cases with assert statements. All cases must pass
		// These tests should check that readRow() correctly handles exceptions
		String csvFilePathLF = Paths.get("easy1.csv").toString();
		try (CharacterReader characterReader = new CharacterReader(new FileReader(csvFilePathLF))) {
			CSVReader csvReader = new CSVReader(characterReader);

			String[] row1 = csvReader.readRow();
			assertArrayEquals(new String[]{"name", "age"}, row1);

			String[] row2 = csvReader.readRow();
			assertArrayEquals(new String[]{"hannah", "20"}, row2);

			String[] row3 = csvReader.readRow();
			assertArrayEquals(new String[]{"harry", "40"}, row3);


		} catch (CSVFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		// Test CRLF line endings
		String csvFilePathCRLF = Paths.get("easy2.csv").toString();
		try (CharacterReader characterReader = new CharacterReader(new FileReader(csvFilePathCRLF))) {
			CSVReader csvReader = new CSVReader(characterReader);

			String[] row1 = csvReader.readRow();
			assertArrayEquals(new String[]{"from", "to", "sequence_number", "message"}, row1);

			String[] row2 = csvReader.readRow();
			assertArrayEquals(new String[]{"alice", "bob", "1", "hello"}, row2);

			String[] row3 = csvReader.readRow();
			assertArrayEquals(new String[]{"bob", "alice", "2", "hello to you too"}, row3);

		} catch (CSVFormatException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}


	}

		@Test
	void testData() throws IOException, CSVFormatException {
			// TODO Write at least 5 test cases with assert statements. All cases must pass
			// These tests should check different corner cases that can occur

			// Test case 1: Handling empty fields and lines
			String emptyFieldsContent = "name,age\n,,\nJohn,25\n,35\nSarah,\n";

			try (CharacterReader characterReader = new CharacterReader(new StringReader(emptyFieldsContent))) {
				CSVReader csvReader = new CSVReader(characterReader);
				assertArrayEquals(new String[]{"name", "age"}, csvReader.readRow());
				assertArrayEquals(new String[]{"", ""}, csvReader.readRow());
				assertArrayEquals(new String[]{"John", "25"}, csvReader.readRow());
				assertArrayEquals(new String[]{"", "35"}, csvReader.readRow());
			}

			// Test case 2: Handling inconsistent number of fields
			String inconsistentFieldsContent = "name,age,city\nJohn,25,New York\nJane,30\nAlex,22,Los Angeles,USA\n";

			try (CharacterReader characterReader = new CharacterReader(new StringReader(inconsistentFieldsContent))) {
				CSVReader csvReader = new CSVReader(characterReader);
				assertArrayEquals(new String[]{"name", "age", "city"}, csvReader.readRow());
				assertArrayEquals(new String[]{"John", "25", "New York"}, csvReader.readRow());
				assertArrayEquals(new String[]{"Jane", "30"}, csvReader.readRow());
				assertArrayEquals(new String[]{"Alex", "22", "Los Angeles", "USA"}, csvReader.readRow());
				assertNull(csvReader.readRow());
			}


		}

	@Test
	void testSpeed() throws IOException, CSVFormatException {
		// TODO 
		// There are *** NO *** required tests for this method and they will *** NOT *** be checked by the autograder
		// However, it is good practice to consider how your code will do on large inputs
		// Consider cases that might result in inefficient run times
		// Also consider ways to measure your run time and determine if it can be improved

		// Generate a large CSV content string with 1 million rows
		StringBuilder csvContent = new StringBuilder();
		csvContent.append("name,age\n");
		for (int i = 0; i < 1000000; i++) { // 1 million rows
			csvContent.append("name").append(i).append(",").append(i % 100).append("\n");
		}

		// Measure the time taken to read all rows
		try (CharacterReader characterReader = new CharacterReader(new StringReader(csvContent.toString()))) {
			CSVReader csvReader = new CSVReader(characterReader);
			long startTime = System.nanoTime();
			String[] row;
			int rowCount = 0;
			while ((row = csvReader.readRow()) != null) {
				rowCount++;
			}
			long endTime = System.nanoTime();
			long duration = endTime - startTime;

			System.out.println("Time taken to read 1 million rows: " + duration / 1_000_000 + " ms");
			assertEquals(1000001, rowCount); // 1 header + 1 million data rows

			// Assert that the operation completes within a reasonable time frame (e.g., 5 seconds)
			assertTimeout(Duration.ofSeconds(5), () -> {
				try (CharacterReader reader = new CharacterReader(new StringReader(csvContent.toString()))) {
					CSVReader csvReaderTest = new CSVReader(reader);
					while (csvReaderTest.readRow() != null) {
						// no-op
					}
				}
			});
		}
	}
}


