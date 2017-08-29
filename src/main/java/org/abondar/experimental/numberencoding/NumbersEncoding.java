package org.abondar.experimental.numberencoding;

public class NumbersEncoding {


    public static void main(String[] args) {
        Encoder encoder = new Encoder();
        encoder.prepareToEncoding("input.txt","dictionary.txt");
        encoder.doEncoding();

    }


}
