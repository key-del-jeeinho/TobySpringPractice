package com.xylope.toby_spring_practice.user.templateexam;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public int calcSum(String path) throws IOException {
        return lineReadTemplate(path, (line, res)-> res + Integer.parseInt(line), 0);
    }

    public int calcMultiple(String path) throws IOException {
        return lineReadTemplate(path, (line, res)-> res * Integer.parseInt(line), 1);
    }

    public String concatenate(String path) throws IOException {
        return lineReadTemplate(path, (line, res) -> res + line, "");
    }

    public <T>T fileReadTemplate(String path, BufferedReaderCallback<T> callback) throws IOException {
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

    public <T>T lineReadTemplate(String path, BufferedReaderLineCallback<T> callback, T initVal) throws IOException {
        return fileReadTemplate(path, br -> {
            String line;
            T res = initVal;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        });
    }
}
