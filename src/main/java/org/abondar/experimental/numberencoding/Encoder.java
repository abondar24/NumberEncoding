package org.abondar.experimental.numberencoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
* The class responsible for actual encoding
* */
public class Encoder {

    private HashMap<Integer, String> codes;

    private List<String> numbers;
    private List<String> dictionary;

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

    }


    public HashMap<Integer, String> getCodes() {
        return codes;
    }

    public List<String> doEncoding() {
        List<String> results = new ArrayList<>();
        numbers.forEach(num -> {
            if (checkNumberFormat(num)) {
                String code = getCodedNumber(num);
            }
        });

        return results;
    }


    /**
     * */
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


    private String getCodedNumber(String number) {
        System.out.println(number);

        if (codeCheck('-', number)) {
            number = number.replace("-", "");
        }

        if (codeCheck('/', number)) {
            number = number.replace("/", "");
        }


        int [] intNum = parseNumberToArray(number);


        List<String> locations = dictionary.stream().filter(val -> codeCheck(val.toLowerCase().charAt(0), codes.get(intNum[0])))
                .filter(loc -> codeCheck(loc.toLowerCase().charAt(1), codes.get(intNum[1])))
                .collect(Collectors.toList());

      //  for (int  i=2;i<intNum.length;i++){
      //      final int index = i;
       //     locations = locations.stream()
        //            .filter(loc-> loc.length()<=intNum.length)
          //          .filter(loc -> codeCheck(loc.toLowerCase().charAt(index), codes.get(intNum[index])))
            //        .collect(Collectors.toList());
       // }

        locations.forEach(System.out::println);


        return "";
    }

    /**
     * helper method for checking if a string contains a char
     **/
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
