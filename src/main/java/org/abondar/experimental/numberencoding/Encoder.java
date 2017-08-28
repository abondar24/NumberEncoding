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

    }


    public void doEncoding() {
        numbers.forEach(num -> {

            if (checkNumberFormat(num)) {
                getCodedNumbers(num).forEach(System.out::println);
            }
        });
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


    public List<String> getCodedNumbers(String number) {
        String cleanNumber = number;

        if (codeCheck('-', cleanNumber)) {
            cleanNumber = cleanNumber.replace("-", "");
        }

        if (codeCheck('/', number)) {
            cleanNumber = cleanNumber.replace("/", "");
        }

        Set<String> existingCombos = getExistingCombos(getNumberCombos(cleanNumber));

        TreeMap<Integer, List<String>> posCity = convertExistingCombosToCities(existingCombos, cleanNumber);

        List<List<String>> tmpResults = new ArrayList<>();

        posCity.forEach((k, v) -> {
            fillTmpResults(tmpResults, v);
        });

        List<String> results = new ArrayList<>();


        System.out.println(posCity);
        System.out.println(tmpResults);


        //  System.out.println(results);
        return new ArrayList<>();
    }

    private void fillTmpResults(List<List<String>> tmpResults, List<String> cities) {
        List<List<String>> res = new ArrayList<>();
        if (tmpResults.isEmpty()) {
            cities.forEach(c -> {
                List<String> tCities = new ArrayList<>();
                tCities.add(c);
                tmpResults.add(tCities);
            });

            return;
        }

        cities.forEach(c -> {
            tmpResults.forEach(l -> {
                List<String> tCities = new ArrayList<>();
                tCities.addAll(l);
                if (!codeCheck(encodedDictionary.get(c).charAt(0), encodedDictionary.get(l.get(l.size() - 1)))) {

                    tCities.add(c);
                }
                res.add(tCities);
            });

        });
        tmpResults.clear();
        tmpResults.addAll(res);
    }


    /**
     * Returns all possible code combinations for the number
     */
    public List<String> getNumberCombos(String number) {
        List<String> combos = new ArrayList<>();

        int window = MIN_CODE_LEN;

        while (window < number.length() + 1) {
            for (int i = 0; i < number.length() - window + 1; i++) {
                String combo = number.substring(i, i + window);
                combos.add(combo);
            }
            window++;
        }


        return combos;
    }

    /**
     * Fills each combo with corresponding cities
     */
    public List<String> getCitiesForCombo(String combo) {
        List<String> cities = new ArrayList<>();

        encodedDictionary.forEach((k, v) -> {
            if (v.equals(combo)) {
                cities.add(k);
            }
        });

        return cities;
    }


    public Set<String> getExistingCombos(List<String> numberCombos) {
        Set<String> existingCombos = new HashSet<>();
        numberCombos.forEach(c -> encodedDictionary.forEach((k, v) -> {
            if (v.equals(c)) {
                existingCombos.add(c);
            }
        }));

        return existingCombos;
    }


    public TreeMap<Integer, List<String>> convertExistingCombosToCities(Set<String> existingCombos, String cleanNumber) {
        TreeMap<Integer, List<String>> posCity = new TreeMap<>();
        existingCombos.forEach(ec -> {
            int pos = cleanNumber.indexOf(ec);
            posCity.put(pos, new ArrayList<>());
        });

        existingCombos.forEach(ec -> {
            int pos = cleanNumber.indexOf(ec);
            posCity.forEach((k, v) -> {
                if (pos == k) {
                    v.addAll(getCitiesForCombo(ec));
                }
            });
        });
        return posCity;
    }

    /**
     * According to the task
     * if number len is <3 - it's incorrect
     * if number len is >6 and no - or / - it's incorrect
     */
    private boolean checkNumberFormat(String number) {
        return number.length() >= 3 && (number.length() <= 6 || number.contains("/") || number.contains("-"));
    }


    /**
     * Returns min length of city and code
     */
    private int calcMinCodeLen(List<String> dictionary) {
        List<Integer> len = dictionary.stream().map(String::length).collect(Collectors.toList());
        return Collections.min(len);
    }

    /**
     * helper method for checking if a string contains a char
     */
    private boolean codeCheck(char chr, String str) {
        return str.indexOf(chr) != -1;

    }

}
