package org.abondar.experimental.numberencoding;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class EncoderTest {
    private static String NUMBERS_FILENAME = "test_input.txt";
    private static String DICTIONARY_FILENAME = "test_dictionary.txt";
    private ReadingUtil util = new ReadingUtil();

    private List<String> numbers = util.readTextFile(NUMBERS_FILENAME);
    private List<String> dictionary = util.readTextFile(DICTIONARY_FILENAME);

    private Encoder encoder = new Encoder(numbers, dictionary);

    @Test
    public void getCities() throws Exception {
        String number = "4824";
        List<String> expectedCities = Arrays.asList("Tor", "fort", "Torf", "Ort");

        List<String> cities = new ArrayList<>();
        List<Combo> combos = encoder.getNumberCombos(number);
        assertFalse(combos.isEmpty());
        combos.forEach(combo -> {

            encoder.getCities(combo);
            combo.getCities().forEach(c -> {
                assertTrue(expectedCities.contains(c));
            });

            cities.addAll(combo.getCities());
        });


        assertEquals(expectedCities.size(), cities.size());

    }

    @Test
    public void getCombos() throws Exception {
        String number = "4824";
        List<String> expectedCombos = Arrays.asList("48", "82", "24", "482", "824", "4824");
        List<Combo> combos = encoder.getNumberCombos(number);
        combos.forEach(System.out::println);
        assertEquals(expectedCombos.size(), combos.size());
    }
}