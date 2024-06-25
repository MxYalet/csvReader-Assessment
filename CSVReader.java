/*
 * I attest that the code in this file is entirely my own except for the starter
 * code provided with the assignment and the following exceptions:
 * <Enter all external resources and collaborations here. Note external code may
 * reduce your score but appropriate citation is required to avoid academic
 * integrity violations. Please see the Course Syllabus as well as the
 * university code of academic integrity:
 *  https://catalog.upenn.edu/pennbook/code-of-academic-integrity/ >
 * Signed,
 * Author: YOUR NAME HERE
 * Penn email: <YOUR-EMAIL-HERE@seas.upenn.edu>
 * Date: YYYY-MM-DD
 */

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * {@code CSVReader} provides a stateful API for streaming individual CSV rows
 * as arrays of strings that have been read from a given CSV file.
 *
 * @author YOUR-NAME-HERE
 */
public class CSVReader {
    @SuppressWarnings("unused")
    private static final long serialVersionUID = 5130409650040L;
    private final CharacterReader reader;

    public CSVReader(CharacterReader reader) {
        this.reader = reader;
    }

    /**
     * This method uses the class's {@code CharacterReader} to read in just enough
     * characters to process a single valid CSV row, represented as an array of
     * strings where each element of the array is a field of the row. If formatting
     * errors are encountered during reading, this method throws a
     * {@code CSVFormatException} that specifies the exact point at which the error
     * occurred.
     *
     * @return a single row of CSV represented as a string array, where each
     *         element of the array is a field of the row; or {@code null} when
     *         there are no more rows left to be read.
     * @throws IOException when the underlying reader encountered an error
     * @throws CSVFormatException when the CSV file is formatted incorrectly
     */
    public String[] readRow() throws IOException, CSVFormatException {
        ArrayList<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuote = false;
        boolean fieldStarted = false;

        int ch;
        int line = 1, column = 0, field = 0;
        Integer lookahead = null;

        while (true) {
            if (lookahead != null) {
                ch = lookahead;
                lookahead = null;
            } else {
                ch = reader.read();
            }

            if (ch == -1) break;

            column++;

            switch (ch) {
                case '\n':
                    if (!insideQuote) {
                        if (fieldStarted || currentField.length() > 0) {
                            fields.add(currentField.toString());
                        }
                        return fields.toArray(new String[0]);
                    } else {
                        currentField.append((char) ch);
                        line++;
                        column = 0;
                    }
                    break;
                case '\r':
                    // Ignore carriage return
                    break;
                case ',':
                    if (!insideQuote) {
                        fields.add(currentField.toString());
                        currentField.setLength(0);
                        fieldStarted = false;
                        field++;
                    } else {
                        currentField.append((char) ch);
                    }
                    break;
                case '"':
                    if (insideQuote) {
                        ch = reader.read();
                        column++;
                        if (ch == '"') {
                            currentField.append('"'); // Escaped quote
                        } else {
                            insideQuote = false;
                            lookahead = ch;
                            column--;
                        }
                    } else {
                        if (currentField.length() > 0) {
                            throw new CSVFormatException("Unexpected quote inside unquoted field", line, column, line, field);
                        }
                        insideQuote = true;
                        fieldStarted = true;
                    }
                    break;
                default:
                    if (insideQuote || !Character.isWhitespace(ch) || fieldStarted) {
                        currentField.append((char) ch);
                        fieldStarted = true;
                    }
                    break;
            }
        }

        if (insideQuote) {
            throw new CSVFormatException("Unterminated quoted field", line, column, line, field);
        }

        if (currentField.length() > 0 || fieldStarted) {
            fields.add(currentField.toString());
        }

        if (fields.isEmpty()) {
            return null;
        }

        return fields.toArray(new String[0]);
    }
    /**
     * Feel free to edit this method for your own testing purposes. As given, it
     * simply processes the CSV file supplied on the command line and prints each
     * resulting array of strings to standard out. Any reading or formatting errors
     * are printed to standard error.
     *
     * @param args command line arguments (1 expected)
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("usage: CSVReader <filename.csv>");
            return;
        }

        /*
         * This block of code demonstrates basic usage of CSVReader's row-oriented API:
         * initialize the reader inside try-with-resources, initialize the CSVReader
         * using the reader, and repeatedly call readRow() until null is encountered. Since
         * CharacterReader implements AutoCloseable, the reader will be automatically
         * closed once the try block is exited.
         */
        var filename = args[0];
        try (var reader = new CharacterReader(filename)) {
            var csvReader = new CSVReader(reader);
            String[] row;
            while ((row = csvReader.readRow()) != null) {
                System.out.println(Arrays.toString(row));
            }
        } catch (IOException | CSVFormatException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}
