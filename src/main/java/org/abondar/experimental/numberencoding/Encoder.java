package org.abondar.experimental.numberencoding;

import java.util.*;
import java.util.stream.Collectors;

/**
 * The class responsible for actual encoding
 */
public class Encoder {

    private HashMap<Integer, String> codes;

    private List<String> numbers;
    private List<String> dictionary;
    private HashMap<String,String> encodedDictionary;
    private int minCodeLen;

    public Encoder(List<String> numbers, List<String> dictionary) {
        codes = new HashMap<>();
        codes.put(0, "e");
        codes.put(1, "jnq");
        codes.put(2, "rwx");
        codes.put(3, "dsy");
        codes.put(4, "ft");
        codes.put(5, "am");
        codes.put(6, "civ");
        codes.put(7, "bku");
        codes.put(8, "lop");
        codes.put(9, "ghz");

        this.numbers = numbers;
        this.dictionary = dictionary;
        this.encodedDictionary = encodeDictionary(this.dictionary);

    }


    public HashMap<Integer, String> getCodes() {
        return codes;
    }

    public List<String> doEncoding() {
        List<String> results = new ArrayList<>();
        numbers.forEach(num -> {
            if (checkNumberFormat(num)) {
                getCodedNumbers(num);
            }
        });

        return results;
    }


    /**
     * Code cities from dictionary into numbers
     * */
    private HashMap<String,String> encodeDictionary(List<String>dictionary){
        HashMap<String,String> result = new HashMap<>();

        for (String str:dictionary){
            StringBuilder cityCode = new StringBuilder();
            for (Character c: str.toLowerCase().toCharArray()){
                codes.forEach((key, value) -> {
                    if (codeCheck(c, value)) {
                        cityCode.append(key);
                    }
                });
            }

            result.put(cityCode.toString(),str);
        }

        result.forEach((k,v)->{
            System.out.println(k +":"+v);
        });

        return result;
    }


    /**
     * According to the task
     * if number len is <3 - it's incorrect
     * if number len is >6 and no - or / - it's incorrect
     */
    private boolean checkNumberFormat(String number) {

        if (number.length() <= 3) {
            return false;
        }

        if (number.length() >= 4 && number.length() < 6) {
            return true;
        }

        if (number.length() >= 6) {

            return number.contains("/") || number.contains("-");
        }


        return false;
    }


    private void getCodedNumbers(String number){
        System.out.println(number);

        if (codeCheck('-', number)) {
            number = number.replace("-", "");
        }

        if (codeCheck('/', number)) {
            number = number.replace("/", "");
        }

        final String num = number;


        encodedDictionary.forEach((key,val)->{

        });
    }



    /**
     * helper method for checking if a string contains a char
     */
    private boolean codeCheck(char chr, String str) {
        return str.indexOf(chr) != -1;

    }

    private int[] parseNumberToArray(String number) {
        int[] intNum = new int[number.length()];

        for (int i = 0; i < number.length(); i++) {
            if (!Character.isDigit(number.charAt(i))) {
                System.out.println("Contains an invalid digit");
                break;
            }
            intNum[i] = Integer.parseInt(String.valueOf(number.charAt(i)));
        }

        return intNum;
    }

}
