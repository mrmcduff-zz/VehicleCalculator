package com.mishmash.alpha;

import static org.junit.Assert.*;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ItemGathererTest {

    private static final String DATA_SOURCE = "http://www.atlanticbt.com/mobiletest/json.txt";
    private JFrame myFrame;
    @Before
    public void setUp() throws Exception {
        myFrame = new JFrame();
        myFrame.show();
    }

    @After
    public void tearDown() throws Exception {
    }

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
        int answer = JOptionPane.showConfirmDialog(myFrame, firstMessage, 
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
            int secondAnswer = JOptionPane.showConfirmDialog(myFrame, secondMessage, 
                    "How about this?", JOptionPane.YES_NO_OPTION);
            assertEquals(0, secondAnswer);
        }
    }

}
