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


    public HashMap<Integer, String> getCodes() {
        return codes;
    }

    public void doEncoding() {
        numbers.forEach(num -> {
            List<String> results = new ArrayList<>();
            if (checkNumberFormat(num)) {
                getCodedNumbers(num);
            }
            results.forEach(System.out::println);
        });


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


    public List<String> getCodedNumbers(String number) {
        String cleanNumber = number;
        if (codeCheck('-', cleanNumber)) {
            cleanNumber = cleanNumber.replace("-", "");
        }

        if (codeCheck('/', number)) {
            cleanNumber = cleanNumber.replace("/", "");
        }


        List<Combo> combos = getNumberCombos(cleanNumber);

        combos.forEach(this::getCities);

        combos = combos.stream()
                .filter(c -> !c.getCities().isEmpty())
                .collect(Collectors.toList());

        HashMap<Integer, List<String>> positionCity = getPositionsOfCities(combos);

        return getCityLists(positionCity, cleanNumber, number);
    }

    /**
     * Gets lists of cities combined for position 0
     */
    public List<String> getCityLists(HashMap<Integer, List<String>> positionCity, final String cleanNumber, String number) {
        List<List<String>> tmpResults = new ArrayList<>();

        positionCity.forEach((k, v) -> {
            if (positionCity.containsKey(0)) {
                if (k == 0) {
                    v.forEach(val -> {
                        List<String> numberStr = new ArrayList<>();
                        numberStr.add(val);
                        tmpResults.add(numberStr);
                    });
                }
            } else {
                List<String> numberStr = new ArrayList<>();
                numberStr.add(cleanNumber.substring(0, 1));
                tmpResults.add(numberStr);
            }
        });


        tmpResults.forEach(tr -> {

            if (tr.get(0).length() != cleanNumber.length()) {

                positionCity.forEach((k, v) -> {
                    if (k != 0) {
                        tr.addAll(v);
                    }

                });
            }
        });

        return createStrings(tmpResults, number, cleanNumber);

    }

    private List<String> createStrings(List<List<String>> tmpResults, final String number, final String cleanNumber) {

        List<String> res = new ArrayList<>();


        tmpResults = tmpResults.stream()
                .filter(tr -> !tr.isEmpty())
                .collect(Collectors.toList());


        tmpResults.forEach(tr -> {
            StringBuilder sb = new StringBuilder();
            sb.append(number);
            sb.append(": ");
            sb.append(tr.get(0));

            if (!codeCheck('"', tr.get(0))) {
                if (cleanNumber.length() - tr.get(0).length() == 1) {
                    sb.append(" ");
                    sb.append(cleanNumber.substring(cleanNumber.length() - 1, cleanNumber.length()));
                    res.add(sb.toString());
                }
            } else if (cleanNumber.length() - tr.get(0).length() - 1 == 1) {
                sb.append(" ");
                sb.append(cleanNumber.substring(cleanNumber.length() - 1, cleanNumber.length()));
                res.add(sb.toString());
            }

            if (tr.size() > 1) {
                int overallLen = tr.get(0).length();
                for (int i = 1; i < tr.size(); i++) {
                    if (!codeCheck('"', tr.get(i))) {
                        overallLen += tr.get(i).length();
                    } else {
                        overallLen += tr.get(i).length() - 1;
                    }
                    if (encodedDictionary.containsKey(tr.get(i - 1))) {
                        if (!encodedDictionary.get(tr.get(i - 1)).contains(tr.get(i))) {
                            if (!codeCheck(tr.get(i).charAt(0), tr.get(i - 1))) {
                                if (overallLen == cleanNumber.length()) {
                                    sb.append(" ");
                                    sb.append(tr.get(i));
                                    sb.append(" ");
                                    res.add(sb.toString());
                                } else if (cleanNumber.length() - overallLen == 1) {
                                    sb.append(" ");
                                    sb.append(cleanNumber.substring(i, cleanNumber.length()));
                                    sb.append(" ");
                                    res.add(sb.toString());
                                }
                            }
                        }
                    }

                }
            }

        });

        res.forEach(System.out::println);

        return res;
    }

    /**
     * Returns all possible code combinations for the number
     */
    public List<Combo> getNumberCombos(String number) {
        List<Combo> combos = new ArrayList<>();

        int window = MIN_CODE_LEN;

        while (window < number.length() + 1) {
            for (int i = 0; i < number.length() - window + 1; i++) {
                String combo = number.substring(i, i + window);
                combos.add(new Combo(combo, i));
            }
            window++;
        }


        return combos;
    }

    /**
     * Fills each combo with corresponding cities
     */
    public Combo getCities(Combo combo) {
        List<String> cities = new ArrayList<>();

        encodedDictionary.forEach((k, v) -> {
            if (v.equals(combo.getCombo())) {
                cities.add(k);
            }
        });

        combo.setCities(cities);
        return combo;
    }


    private HashMap<Integer, List<String>> getPositionsOfCities(List<Combo> combos) {
        HashMap<Integer, List<String>> positionCity = new HashMap<>();

        combos.stream().sorted((cL, cR) -> cL.getPos().compareTo(cR.getPos()))
                .forEach(c -> positionCity.put(c.getPos(), new ArrayList<>()));


        combos.forEach(c -> positionCity.forEach((k, v) -> {
            if (k == c.getPos()) {
                v.addAll(c.getCities());
            }
        }));

        return positionCity;
    }

    /**
     * helper method for checking if a string contains a char
     */
    private boolean codeCheck(char chr, String str) {
        return str.indexOf(chr) != -1;

    }

}
