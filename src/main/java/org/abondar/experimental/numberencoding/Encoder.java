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
    private HashMap<String, String> encodedDictionary;
    private int MIN_CODE_LEN;

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
        this.MIN_CODE_LEN = calcMinCodeLen(dictionary);
      //  encodedDictionary.forEach((k,v)->System.out.println(k+":"+v));

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
     * Returns min length of city and code
     */
    private int calcMinCodeLen(List<String> dictionary) {
        List<Integer> len = dictionary.stream().map(String::length).collect(Collectors.toList());

        return Collections.min(len);
    }


    /**
     * Code cities from dictionary into numbers
     */
    private HashMap<String, String> encodeDictionary(List<String> dictionary) {
        HashMap<String, String> result = new HashMap<>();

        for (String str : dictionary) {
            StringBuilder cityCode = new StringBuilder();
            for (Character c : str.toLowerCase().toCharArray()) {
                codes.forEach((key, value) -> {
                    if (codeCheck(c, value)) {
                        cityCode.append(key);
                    }
                });
            }

            result.put(str, cityCode.toString());
        }

        return result;
    }


    /**
     * According to the task
     * if number len is <3 - it's incorrect
     * if number len is >6 and no - or / - it's incorrect
     */
    private boolean checkNumberFormat(String number) {

        return number.length() > 3 && (number.length() < 6 || number.contains("/") || number.contains("-"));


    }


    private void getCodedNumbers(String number) {
        String cleanNumber = number;
        if (codeCheck('-', cleanNumber)) {
            cleanNumber = cleanNumber.replace("-", "");
        }

        if (codeCheck('/', number)) {
            cleanNumber = cleanNumber.replace("/", "");
        }


             List<Combo> combos = getNumberCombos(cleanNumber);

        combos.forEach(this::getCities);

        combos=combos.stream()
                .filter(c->!c.getCities().isEmpty())
                .collect(Collectors.toList());

        List<StringBuilder> sbList = new ArrayList<>();
        combos.forEach(c->{
            if (c.getPos()==0){
                  for (int i = 0; i < c.getCities().size(); i++) {
                      StringBuilder sb = new StringBuilder();
                      sb.append(number);
                      sb.append(": ");
                      sb.append(c.getCities().get(i));
                      sb.append(" ");
                      sbList.add(sb);
                  }

            }

        });

        sbList.forEach(sb->{
            System.out.println(sb.toString());
        });

    }

    /**
     * Returns all possible code combinations for the number
     */
    public List<Combo> getNumberCombos(String number) {
        List<Combo> combos = new ArrayList<>();

        int window = MIN_CODE_LEN;

        while (window < number.length()+1) {
            for (int i = 0; i < number.length() - window+1; i++) {
                String combo = number.substring(i, i + window);
                combos.add(new Combo(combo,i));
            }
            window++;
        }


        return combos;
    }

    public Combo getCities(Combo combo){
        List<String>cities = new ArrayList<>();

        encodedDictionary.forEach((k,v)->{
           if(v.equals(combo.getCombo())){
               cities.add(k);
           }
        });

        combo.setCities(cities);
        return combo;
    }

    /**
     * helper method for checking if a string contains a char
     */
    private boolean codeCheck(char chr, String str) {
        return str.indexOf(chr) != -1;

    }

}
