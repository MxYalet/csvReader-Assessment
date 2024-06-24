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

class CSVReaderTest {
	
	@Test
	void testBasic() throws CSVFormatException, IOException {
		// TODO Write at least 5 test cases with assert statements. All cases must pass

		// These tests should check basic functionality of readRow()
		String csvFilePath = Paths.get("easy1.csv").toString();

		// Create a CSVReader instance with the provided file
		try (CharacterReader characterReader = new CharacterReader(new FileReader(csvFilePath))) {
			CSVReader csvReader = new CSVReader(characterReader);

			// Read and verify each row
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
		String csvContentUnix = "name,age\nhannah,20\nharry,40\nmary,56\njohn,18\nyule,25\njiexi,32\nlauren,35";
		String csvContentWindows = "name,age\r\nhannah,20\r\nharry,40\r\nmary,56\r\njohn,18\r\nyule,25\r\njiexi,32\r\nlauren,35";
		String csvContentMac = "name,age\rhannah,20\rharry,40\rjohn,18\ryule,25\rjiexi,32\rlauren,35";
		String csvContentMixed = "name,age\nhannah,20\r\nharry,40\nmary,56\r\njohn,18\nyule,25\r\njiexi,32\nlauren,35";

		// Test line endings (\n)
		try (CharacterReader characterReader = new CharacterReader(new StringReader(csvContentUnix))) {
			CSVReader csvReader = new CSVReader(characterReader);
			assertArrayEquals(new String[]{"name", "age"}, csvReader.readRow());
			assertArrayEquals(new String[]{"hannah", "20"}, csvReader.readRow());
			assertArrayEquals(new String[]{"harry", "40"}, csvReader.readRow());
			assertArrayEquals(new String[]{"mary", "56"}, csvReader.readRow());
			assertArrayEquals(new String[]{"john", "18"}, csvReader.readRow());
			assertArrayEquals(new String[]{"yule", "25"}, csvReader.readRow());
			assertArrayEquals(new String[]{"jiexi", "32"}, csvReader.readRow());
			assertArrayEquals(new String[]{"lauren", "35"}, csvReader.readRow());
		}

		// Test line endings (\r\n)
		try (CharacterReader characterReader = new CharacterReader(new StringReader(csvContentWindows))) {
			CSVReader csvReader = new CSVReader(characterReader);
			assertArrayEquals(new String[]{"name", "age"}, csvReader.readRow());
			assertArrayEquals(new String[]{"hannah", "20"}, csvReader.readRow());
			assertArrayEquals(new String[]{"harry", "40"}, csvReader.readRow());
			assertArrayEquals(new String[]{"mary", "56"}, csvReader.readRow());
			assertArrayEquals(new String[]{"john", "18"}, csvReader.readRow());
			assertArrayEquals(new String[]{"yule", "25"}, csvReader.readRow());
			assertArrayEquals(new String[]{"jiexi", "32"}, csvReader.readRow());
			assertArrayEquals(new String[]{"lauren", "35"}, csvReader.readRow());
		}

		// Test Mixed line endings (\n and \r\n)
		try (CharacterReader characterReader = new CharacterReader(new StringReader(csvContentMixed))) {
			CSVReader csvReader = new CSVReader(characterReader);
			assertArrayEquals(new String[]{"name", "age"}, csvReader.readRow());
			assertArrayEquals(new String[]{"hannah", "20"}, csvReader.readRow());
			assertArrayEquals(new String[]{"harry", "40"}, csvReader.readRow());
			assertArrayEquals(new String[]{"mary", "56"}, csvReader.readRow());
			assertArrayEquals(new String[]{"john", "18"}, csvReader.readRow());
			assertArrayEquals(new String[]{"yule", "25"}, csvReader.readRow());
			assertArrayEquals(new String[]{"jiexi", "32"}, csvReader.readRow());
			assertArrayEquals(new String[]{"lauren", "35"}, csvReader.readRow());

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

			String[] row4 = csvReader.readRow();
			assertArrayEquals(new String[]{"mary", "56"}, row4);

			String[] row5 = csvReader.readRow();
			assertArrayEquals(new String[]{"john", "18"}, row5);

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

			// Test easy1.csv
			String csvFilePathEasy1 = Paths.get("easy1.csv").toString();
			try (CharacterReader characterReader = new CharacterReader(new FileReader(csvFilePathEasy1))) {
				CSVReader csvReader = new CSVReader(characterReader);

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



			}
		}

	@Test
	void testSpeed() {
		// TODO 
		// There are *** NO *** required tests for this method and they will *** NOT *** be checked by the autograder
		// However, it is good practice to consider how your code will do on large inputs
		// Consider cases that might result in inefficient run times
		// Also consider ways to measure your run time and determine if it can be improved
	}
}


