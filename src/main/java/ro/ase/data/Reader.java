package ro.ase.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base CSV reader with utilities for reading files and splitting lines.
 *
 * @param <T> type produced by the reader
 */
public abstract class Reader<T> {
    /** Reads objects of type T from a path. */
    public abstract List<T> read(String path);

    /**
     * Reads all lines from the provided path.
     *
     * @param path file path
     * @return non-empty list of lines
     * @throws IllegalArgumentException if the file is empty
     */
    protected List<String> readFromFile(String path) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(path))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        if(lines.isEmpty()) {
            throw new IllegalArgumentException("Empty file");
        }
        return lines;
    }

    /** Splits a CSV line by comma. */
    protected String[] splitLineByComma(String line) {
        return line.split(",");
    }
}
