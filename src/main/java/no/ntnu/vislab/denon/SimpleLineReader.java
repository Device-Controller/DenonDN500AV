package no.ntnu.vislab.denon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class SimpleLineReader extends BufferedReader {

    public SimpleLineReader(InputStreamReader inputStreamReader) {
        super(inputStreamReader);
    }

    @Override
    public String readLine() throws IOException {
        StringBuilder str = new StringBuilder();
        while (ready() && dataAvailable()) {
            int c = read();
            if (c != -1) {
                str.append((char) c);
            }
        }
        return str.toString();
    }

    private boolean dataAvailable() throws IOException {
        mark(10);
        int c =  read();
        reset();
        return c != -1;
    }
}
