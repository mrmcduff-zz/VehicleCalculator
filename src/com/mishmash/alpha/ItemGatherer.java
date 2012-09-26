package com.mishmash.alpha;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;



public class ItemGatherer {

    private static final String CHAR_SET = "UTF-8";

    public static String getSmallAmountOfTextDataFromSource(String source) {
        InputStream in = null;
        String dataString = "";
        try {
            in = new URL(source).openStream();
            dataString = IOUtils.toString(in, CHAR_SET);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            IOUtils.closeQuietly(in);
        }
        
        return dataString;
    }
}
