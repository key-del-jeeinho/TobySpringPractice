package com.xylope.toby_spring_practice.user.templateexam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        return fileReadTemplate(path, br-> {
            String line;
            int sum = 0;
            while ((line = br.readLine()) != null) {
                sum += Integer.parseInt(line);
            }

            return sum;
        });
    }

    public int calcMultiple(String path) throws IOException {
        return fileReadTemplate(path, br-> {
            String line;
            int sum = 1;
            while ((line = br.readLine()) != null) {
                sum *= Integer.parseInt(line);
            }

            return sum;
        });
    }

    public Integer fileReadTemplate(String path, BufferReaderCallback callback) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path));
            return callback.doSomethingWithReader(br);
        } catch (IOException e) {
            e.printStackTrace();
            throw e;
        } finally {
            if(br != null) {
                try { br.close(); }
                catch (IOException e) { e.printStackTrace();}
            }
        }
    }
}
