package ro.ase.data;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public abstract class Reader {
    public abstract List read(String path);

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

    protected String[] splitLineByComma(String line) {
        return line.split(",");
    }
}
