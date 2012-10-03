package com.mishmash.alpha.vehicleparts;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mishmash.alpha.PartPosition;
import com.mishmash.alpha.VehicleType;

/**
 * This is the most complicated of the validators, so it gets its own tests.
 * The tests also helped drive development of the class.
 */
public class WheelPositionValidatorTest {

    WheelPositionValidator wpv1;
    WheelPositionValidator wpv2;
    
    @Before
    public void setUp() {
        wpv1 = new WheelPositionValidator();
        wpv2 = new WheelPositionValidator();
    }
    @Test
    public void testEquals() {
        assertTrue(wpv1.equals(wpv2));
        List<VehicleType> types = Lists.newArrayList();
        types.add(VehicleType.BICYCLE);
        wpv1.setValidTypesForPosition(PartPosition.FRONT, types);
        wpv2.setValidTypesForPosition(PartPosition.FRONT, types);
        assertTrue(wpv2.equals(wpv1));
    }
    
    @Test
    public void testNotEquals() {
        List<VehicleType> types = Lists.newArrayList();
        types.add(VehicleType.BICYCLE);
        wpv1.setValidTypesForPosition(PartPosition.FRONT, types);
        wpv2.setValidTypesForPosition(PartPosition.REAR, types);
        assertFalse(wpv1.equals(wpv2));
        
        wpv1.setValidTypesForPosition(PartPosition.REAR, types);
        assertFalse(wpv2.equals(wpv1));
    }
    
    @Test 
    public void testGetNumberOfValidPositions() {
        assertEquals(0, wpv1.getNumberOfValidPositions());
        List<VehicleType> types = Lists.newArrayList();
        types.add(VehicleType.BICYCLE);
        wpv1.setValidTypesForPosition(PartPosition.FRONT, types);
        assertEquals(1, wpv1.getNumberOfValidPositions());
        wpv1.setValidTypesForPosition(PartPosition.REAR, types);
        assertEquals(2, wpv1.getNumberOfValidPositions());
        // Adding invalid position validation has no effect.
        wpv1.setValidTypesForPosition(PartPosition.INVALID, types);
        assertEquals(2, wpv1.getNumberOfValidPositions());
    }

}
