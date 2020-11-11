package org.abondar.experimental.numberencoding;

import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class EncoderTest {

    private Encoder encoder = new Encoder();

    @Test
    public void testGetCodedNumbers() {

        encoder.prepareToEncoding("test_input.txt", "test_dictionary.txt");
        List<String> actualResults = encoder.getCodedNumbers("5624-82");
        List<String> expectedResults = Arrays.asList("5624-82: mir Tor", "5624-82: Mix Tor");

        Assert.assertEquals(expectedResults, actualResults);
    }


    @Test
    public void testGetNumberCombos() {

        encoder.prepareToEncoding("test_input.txt", "test_dictionary.txt");
        List<String> actualResults = encoder.getNumberCombos("107835");
        List<String> expectedResults = Arrays.asList("10", "07", "78", "83", "35", "107", "078",
                "783", "835", "1078", "0783", "7835", "10783", "07835", "107835");

        Assert.assertEquals(expectedResults, actualResults);
    }

    @Test
    public void testGetExistingCombos() {

        encoder.prepareToEncoding("test_input.txt", "test_dictionary.txt");
        List<String> combos = Arrays.asList("10", "07", "78", "83", "35", "107", "078",
                "783", "835", "1078", "0783", "7835", "10783", "07835", "107835");
        Set<String> actualResults = encoder.getExistingCombos(combos);
        List<String> expectedCombos = Arrays.asList("10", "35", "78", "83", "107", "783");
        Set expectedResults = new HashSet<>(expectedCombos);

        Assert.assertEquals(expectedResults, actualResults);
    }


    @Test
    public void testGetCitiesForCombo() {

        encoder.prepareToEncoding("test_input.txt", "test_dictionary.txt");

        List<String> actualResults = encoder.getCitiesForCombo("562");
        List<String> expectedResults = Arrays.asList("mir","Mix");

        Assert.assertEquals(expectedResults, actualResults);
    }

    @Test
    public void testGetCalcDiff() {

        encoder.prepareToEncoding("test_input.txt", "test_dictionary.txt");

        List<String> cityList = Arrays.asList("neu", "o\"d");
        List<String> actualResults = encoder.calcDiff("107835",cityList);
        List<String> expectedResults = Arrays.asList("neu", "o\"d","5");

        Assert.assertEquals(expectedResults,actualResults);
    }


}

