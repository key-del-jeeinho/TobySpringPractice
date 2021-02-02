package com.xylope.toby_spring_practice.user.templateexam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        BufferedReader br = null;
        String line;
        int sum = 0;

        try {
            br = new BufferedReader(new FileReader(path));

            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(br != null) {
                try { br.close(); }
                catch (IOException e) { e.printStackTrace();}
            }
        }
        return sum;
    }
}
