package com.mishmash.alpha.vehicleparts;

import static org.junit.Assert.*;

import org.junit.Test;

public class PartUtilsTest {

    // These differ at the 8th decimal place.
    double a = 1.234567890;
    double b = 1.234567811;
    // c differs at the seventh decimal place
    double c = 1.2345;
    // These items will be above the hashing caps.
    double bigGun = Integer.MAX_VALUE + 0.1234567;
    double similarGun = Integer.MAX_VALUE + 0.1234555;
    double negativeGun = Integer.MIN_VALUE - 10000.0;
    
    @Test
    public void testDoubleEquals() {
        assertTrue(PartUtils.doubleEquals(a, b));
        assertFalse(PartUtils.doubleEquals(a, c));
    }

    @Test
    public void testRoundToDelta() {

        double ra = PartUtils.roundToDelta(a);
        double rb = PartUtils.roundToDelta(b);
        assertEquals(ra, rb, 1.0e-20);
        double rc = PartUtils.roundToDelta(c);
        assertFalse(PartUtils.doubleEquals(ra, rc));
    }
    
    /**
     * The most important part of this test is that it must
     * ALWAYS be the case that PartUtils.doubleEquals(a, b) 
     * implies PartUtils.getHashableValueFromDouble(a) == 
     * PartUtils.getHashableValueFromDouble(b).
     * 
     * The not-equals case is of lesser importance, as it only
     * helps make for a good hashing function. The equals case
     * is required to make a valid hashing function.
     */
    @Test
    public void testGetHashableValueFromDouble() {
        int ra = PartUtils.getHashableValueFromDouble(a);
        int rb = PartUtils.getHashableValueFromDouble(b);
        assertTrue(PartUtils.doubleEquals(a, b));
        assertEquals(ra, rb);
        int rc = PartUtils.getHashableValueFromDouble(c);
        
        assertFalse(ra == rc);
        assertFalse(PartUtils.doubleEquals(bigGun, similarGun));
        int rdb = PartUtils.getHashableValueFromDouble(bigGun);
        int rsb = PartUtils.getHashableValueFromDouble(similarGun);
        assertTrue(rdb == rsb);
        
        double x = 20;
        double y = 20 - 1.0e-8;
        double z = 20 - 1.0e-10;
        assertTrue(PartUtils.doubleEquals(x, z));
        assertEquals(PartUtils.getHashableValueFromDouble(x), PartUtils.getHashableValueFromDouble(z));
        assertTrue(PartUtils.doubleEquals(x, y));
        assertEquals(PartUtils.getHashableValueFromDouble(x), PartUtils.getHashableValueFromDouble(y));

        
    }
    
    @Test
    public void testHashingCaps() {
        int rdb = PartUtils.getHashableValueFromDouble(bigGun);
        int rnegative = PartUtils.getHashableValueFromDouble(negativeGun);
        
        assertEquals(rdb, Integer.MAX_VALUE);
        assertEquals(rnegative, Integer.MIN_VALUE);
    }

}
