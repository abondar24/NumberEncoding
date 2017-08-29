package org.abondar.experimental.numberencoding;

import org.apache.commons.io.IOUtils;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/*
* Class resposible for reading data
* */
public class ReadingUtil {

    // needs to be slightly changed if we want to remove commons-io
    public  static List<String> readTextFile(String fileName){
        List<String> data = new ArrayList<>();
        try {
            InputStream inputStream = ReadingUtil.class.getClassLoader().getResourceAsStream(fileName);
            data = IOUtils.readLines(inputStream, "UTF-8");
        } catch (IOException e){
            System.err.println(e.getMessage());
        }

        return data;
    }


}
