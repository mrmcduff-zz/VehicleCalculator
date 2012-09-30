package com.mishmash.alpha.vehicleparts;

import static org.junit.Assert.*;

import java.util.List;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mishmash.alpha.VehicleType;

public class UnaryValidatorTest {

    UnaryValidator u1 = new UnaryValidator();
    UnaryValidator u2 = new UnaryValidator();

    @Test
    public void testEquals() {
        assertEquals(u1, u2);
        List<VehicleType> bikeList = Lists.newArrayList();
        bikeList.add(VehicleType.BICYCLE);
        u1.setValidTypes(bikeList);
        u2.setValidTypes(bikeList);
        
        assertEquals(u1, u2);
        assertFalse(u1.equals(null));
    }
    
    @Test
    public void testTriangleEquals() {
        List<VehicleType> vehicleList = Lists.newArrayList();
        vehicleList.add(VehicleType.SCOOTER);
        u1.setValidTypes(vehicleList);
        u2.setValidTypes(vehicleList);
        UniversalValidator universal = new UniversalValidator();

        IVehicleTypeValidator ivt1 = (IVehicleTypeValidator) u1;
        IVehicleTypeValidator ivt2 = (IVehicleTypeValidator) u2;
        IVehicleTypeValidator ivt3 = (IVehicleTypeValidator) universal;
        
        assertTrue(ivt1.equals(ivt2));
        assertTrue(ivt1.equals(u2));
        assertTrue(u1.equals(ivt2));
        assertFalse(ivt1.equals(ivt3));
        assertFalse(ivt2.equals(ivt3));
    }

}
