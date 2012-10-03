package com.mishmash.alpha.vehicleparts;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import com.google.common.collect.Lists;
import com.mishmash.alpha.VehicleType;

public class EqualityAndHashCodeTest {

    List<VehicleType> allTypes = Lists.newArrayList(VehicleType.validValues());
    List<VehicleType> pedalOnly = Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE);
    List<VehicleType> scooterOnly = Lists.newArrayList(VehicleType.SCOOTER);
    List<VehicleType> bikeOnly = Lists.newArrayList(VehicleType.BICYCLE);
    
    @SuppressWarnings("unchecked")
    @Test
    public void testWheels() {

        Wheel w1 = new Wheel("Same", 155.12345678901, 200, allTypes, allTypes);
        Wheel w2 = new Wheel("Same", 155.12345679999, 200.0000000009, allTypes, allTypes);
        Wheel w3 = new Wheel("Diff", 155.12345678901, 200, allTypes, allTypes);
        Wheel w4 = new Wheel("Same", 155.1234500, 200, allTypes, allTypes);
        Wheel w5 = new Wheel("Same", 155.12345678901, 200.000004, allTypes, allTypes);
        Wheel w6 = new Wheel("Same", 155.12345678901, 200, pedalOnly, allTypes);
        Wheel w7 = new Wheel("Same", 155.12345678901, 200, allTypes, scooterOnly);
        Wheel w8 = new Wheel("Same", 155.12345678901, 200, allTypes, allTypes, allTypes);
        
        assertEquals(w1, w2);
        assertFalse(w1.equals(w3));
        assertFalse(w1.equals(w4));
        assertFalse(w1.equals(w5));
        assertFalse(w1.equals(w6));
        assertFalse(w1.equals(w7));
        assertFalse(w1.equals(null));
        /*
         * This was by accident, but for now it is correct. A wheel validator doesn't store
         * any unnecessary type lists. If the PartPositions enum isn't changed, then 
         * excess validity lists are essentially ignored, so this is okay as 
         * long as their hashcodes are equal. Testing both directions in case a 
         * bug is introduced where .equals only counts to as many valid lists as it is fed
         * and ignores the rest, even when it shouldn't.
         */
        assertTrue(w8.equals(w1));
        assertTrue(w1.equals(w8));
        
        assertEquals(w1.hashCode(), w2.hashCode());
        assertEquals(w1.hashCode(), w8.hashCode());
    }
    
    @Test
    public void testPowerPlants() {
        PowerPlant p1 = new PowerPlant("Same", 20, allTypes);
        PowerPlant p2 = new PowerPlant("Same", 20 - 1.0e-8, allTypes);
        PowerPlant p3 = new PowerPlant("Diff", 20, allTypes);
        PowerPlant p4 = new PowerPlant("Same", 20 + 1.0e-7, allTypes);
        PowerPlant p5 = new PowerPlant("Same", 20, scooterOnly);
        
        assertEquals(p1, p2);
        assertFalse(p1.equals(null));
        assertFalse(p1.equals(p3));
        assertFalse(p2.equals(p4));
        assertFalse(p5.equals(p1));
        assertFalse(p1.equals(p5));
        
        
        assertEquals(p1.hashCode(), p2.hashCode());
    }
    
    @Test
    public void testFrames() {
        VehicleFrame f1 = new VehicleFrame("Same", 10, 10, allTypes);
        VehicleFrame f2 = new VehicleFrame("Same", 10-1.0e-8, 10, allTypes);
        VehicleFrame f3 = new VehicleFrame("Same", 10, 10+1.0e-8, allTypes);
        VehicleFrame f4 = new VehicleFrame("Diff", 10, 10, allTypes);
        VehicleFrame f5 = new VehicleFrame("Same", 10, 10, null);
        VehicleFrame f6 = new VehicleFrame(null, 10, 10, allTypes);
        VehicleFrame f7 = new VehicleFrame("Same", 10, 10, bikeOnly);
        
        assertEquals(f1, f2);
        assertEquals(f2, f3);
        assertFalse(f1.equals(f4));
        assertFalse(f2.equals(f5));
        assertFalse(f3.equals(f6));
        assertFalse(f1.equals(f7));
        
        assertEquals(f1.hashCode(), f2.hashCode());
        assertEquals(f2.hashCode(), f3.hashCode());
    }
    
    @Test
    public void testRiders() {
        Rider r1 = new Rider("Tom", 50);
        Rider r2 = new Rider("Tom", 50 + 1.0e-8);
        Rider r3 = new Rider("Tom", 50 + 1.0e-7);
        Rider r4 = new Rider("Bill", 50);
        
        assertEquals(r1, r2);
        assertFalse(r1.equals(null));
        assertFalse(r1.equals(r3));
        assertFalse(r1.equals(r4));
        
        assertEquals(r1.hashCode(), r2.hashCode());
    }

}
