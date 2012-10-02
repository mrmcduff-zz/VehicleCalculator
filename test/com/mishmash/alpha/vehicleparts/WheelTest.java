package com.mishmash.alpha.vehicleparts;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mishmash.alpha.VehicleType;

public class WheelTest {

    Wheel w1;
    Wheel w2;
    Wheel w3;
    Wheel w4;
    List<VehicleType> bikeList;
    List<VehicleType> trikeList;
    List<VehicleType> nonMotoredList;
    List<VehicleType> motoredList;
    
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        bikeList = Lists.newArrayList();
        trikeList = Lists.newArrayList();
        nonMotoredList = Lists.newArrayList();
        motoredList = Lists.newArrayList();
        
        bikeList.add(VehicleType.BICYCLE);
        trikeList.add(VehicleType.TRICYCLE);
        nonMotoredList.add(VehicleType.BICYCLE);
        nonMotoredList.add(VehicleType.TRICYCLE);
        motoredList.add(VehicleType.SCOOTER);
        w1 = new Wheel("Tom", 10, 20, nonMotoredList, bikeList);
        w2 = new Wheel("Tom", 10, 20, nonMotoredList, bikeList);
        w3 = new Wheel("Tom", 100000.1, 20, nonMotoredList, bikeList);
        w4 = new Wheel("Tom", 10, -120, nonMotoredList);
        

    }
    
    @Test
    public void testEquals() {
        assertTrue(w1.equals(w2));
        assertTrue(w2.equals(w1));
        assertFalse(w1.equals(w3));
        assertFalse(w1.equals(w4));
        assertFalse(w4.equals(null));
    }
    @Test
    public void testHashCode() {
        // This is the only actual requirement of implementing hashCode
        assertTrue(w1.equals(w2) && w1.hashCode() == w2.hashCode());
    }

    @Test
    public void testHasAllValidModifiers() {
        assertTrue(w1.hasValidAttributes());
        assertTrue(w3.hasValidAttributes());
        assertFalse(w4.hasValidAttributes());
    }

}
