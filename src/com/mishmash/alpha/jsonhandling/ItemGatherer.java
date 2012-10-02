package com.mishmash.alpha.jsonhandling;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;

import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonStreamParser;

/**
 * Util-type class used to get our data from a remote source. 
 * For expediency's sake, we are gathering everything all at once into a String.
 * If the goal of the application was to search through larger JSON DB's, we should
 * add new methods to this class that parse the data in smaller bites.
 */
public class ItemGatherer {

    private static final String CHAR_SET = "UTF-8";

    /**
     * Gets all of the data from the source destination and returns it as a String.
     * This method is NOT SAFE for large data sets.
     * 
     * @param source
     * The location of the data.
     * 
     * @return
     * A string containing the data found at the source.
     */
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
    
    /**
     * Separates the categories from the input JsonArray as determined by the input category titles.
     * 
     * @param rawData
     * The raw JSON data.
     * 
     * @param categoryTitles
     * A list of titles to look for in the data.
     * 
     * @return
     * Arrays of each of the input types, keyed by the type names.
     */
    public static Map<String, JsonArray> seperateCategories(String rawData, List<String> categoryTitles) {
        Map<String, JsonArray> nameCategoryMap = Maps.newHashMap();
        JsonStreamParser parser = new JsonStreamParser(rawData);
        JsonElement totalStuff = null;
        JsonObject dbObject = null;
        if (parser.hasNext()) {
            totalStuff = parser.next();
            if (totalStuff.isJsonObject()) {
                dbObject = totalStuff.getAsJsonObject();
            }
        }
        
        if (dbObject != null) {
            for(String categoryName : categoryTitles) {
                if (dbObject.has(categoryName) && dbObject.get(categoryName).isJsonArray()) {
                    nameCategoryMap.put(categoryName, dbObject.getAsJsonArray(categoryName));
                }
            }
        }
        
        return nameCategoryMap;
    }
    
}
