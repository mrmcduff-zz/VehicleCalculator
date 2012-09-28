package com.mishmash.alpha.jsonhandling;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.JsonStreamParser;
import com.google.gson.JsonObject;

public class DataParser {
    
    public static Map<String, JsonArray> seperateCategories(String rawData, List<String> categoryTitles) {
        Map<String, JsonArray> nameCategoryMap = new HashMap<String, JsonArray>();
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
