package org.abondar.experimental.numberencoding;

import java.util.List;

public class NumbersEncoding {


    private static String NUMBERS_FILENAME = "input.txt";
    private static String DICTIONARY_FILENAME = "dictionary.txt";

    public static void main(String[] args){
          ReadingUtil util = new ReadingUtil();
          Encoder encoder = new Encoder();

          List<String> numbers = util.readTextFile(NUMBERS_FILENAME);
          List<String> dictionary = util.readTextFile(DICTIONARY_FILENAME);

    }




}
