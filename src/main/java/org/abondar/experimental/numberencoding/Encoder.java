package org.abondar.experimental.numberencoding;

import java.util.HashMap;

/*
* The class responsible for actual encoding
* */
public class Encoder {

    private HashMap<Integer,String> codes;


    public Encoder(){
        codes = new HashMap<>();
        codes.put(0,"Ee");
        codes.put(1,"JNQjnq");
        codes.put(2,"RWXrwx");
        codes.put(3,"DSYdsy");
        codes.put(4,"FTft");
        codes.put(5,"AMam");
        codes.put(6,"CIVciv");
        codes.put(7,"BKUbku");
        codes.put(8,"LOPlop");
        codes.put(9,"GHZghz");

    }


    public HashMap<Integer, String> getCodes() {
        return codes;
    }
}
