package com.mishmash.alpha;

import static org.junit.Assert.*;

import javax.swing.JOptionPane;
import org.junit.Test;

import com.mishmash.alpha.jsonhandling.ItemGatherer;

public class ItemGathererTest {

    private static final String DATA_SOURCE = "http://www.atlanticbt.com/mobiletest/json.txt";

    @Test
    public void testGetSmallAmountOfTextDataFromSource() {
        String testString = ItemGatherer.getSmallAmountOfTextDataFromSource(DATA_SOURCE);
        String [] lines = testString.split("\n");
        StringBuilder sb = new StringBuilder();
        String firstMessage = "";
        for (int i = 0; i < 10; ++i) {
            if (i < lines.length) {
                sb.append(lines[i]);
                sb.append("\n");
            } else {
                break;
            }
        }
        firstMessage = sb.toString();
        int answer = JOptionPane.showConfirmDialog(null, firstMessage, 
                "Does this look okay?", JOptionPane.YES_NO_OPTION);
        assertEquals(0, answer);
        
        if (lines.length > 20) {
            sb = new StringBuilder();
            String secondMessage = "";
            for (int i = lines.length - 10; i < lines.length; ++i) {
                sb.append(lines[i]);
                sb.append("\n");
            }
            secondMessage = sb.toString();
            int secondAnswer = JOptionPane.showConfirmDialog(null, secondMessage, 
                    "How about this?", JOptionPane.YES_NO_OPTION);
            assertEquals(0, secondAnswer);
        }
    }

}
