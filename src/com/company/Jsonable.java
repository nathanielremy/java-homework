package com.company;

public interface Jsonable {
    boolean writeJsonToFile(String filename);
    Jsonable readFromJsonFile(String filename);
}