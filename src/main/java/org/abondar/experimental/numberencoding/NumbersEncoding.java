package org.abondar.experimental.numberencoding;

import java.util.List;

public class NumbersEncoding {


    private static String NUMBERS_FILENAME = "test_input.txt";
    private static String DICTIONARY_FILENAME = "test_dictionary.txt";

    public static void main(String[] args) {
        ReadingUtil util = new ReadingUtil();

        List<String> numbers = util.readTextFile(NUMBERS_FILENAME);
        List<String> dictionary = util.readTextFile(DICTIONARY_FILENAME);

        Encoder encoder = new Encoder(numbers,dictionary);
        encoder.doEncoding();

    }


}
