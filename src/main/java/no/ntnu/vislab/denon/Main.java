package no.ntnu.vislab.denon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 23);
            PrintWriter pw = new PrintWriter(s.getOutputStream(), true);
            BufferedReader br = new BufferedReader(new InputStreamReader(s.getInputStream()));
            String line;
            pw.println("TEST");
            pw.println("TEST");
            pw.println("TEST");
            pw.println("TEST");
            pw.println("TEST");
            pw.println("TEST");
            pw.println("TEST");
            pw.println("TEST");
            while((line = br.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
