/**
 * 
 */
package com.mishmash.alpha;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mishmash.alpha.vehicleparts.PowerPlant;
import com.mishmash.alpha.vehicleparts.Rider;
import com.mishmash.alpha.vehicleparts.VehicleFrame;
import com.mishmash.alpha.vehicleparts.Wheel;

/**
 * @author mrmcduff
 *
 */
public class VehicleTest {

    VehicleFrame goodFrame;
    VehicleFrame badFrame;
    VehicleFrame negativeFrame;
    VehicleFrame tricycleOnlyFrame;
    
    Wheel goodWheel;
    Wheel badWheel;
    Wheel negativeWheel;
    Wheel scooterOnlyWheel;
    
    PowerPlant goodPlant;
    PowerPlant badPlant;
    PowerPlant negativePowerPlant;
    PowerPlant scooterPlant;
    
    Rider joe;
    Rider littleTimmy;
    Rider lance;
    Rider negativeMan;
    
    @SuppressWarnings("unchecked")
    @Before
    public void setUp() {
        goodFrame = new VehicleFrame("Good Frame", 10, 10, Lists.newArrayList(VehicleType.BICYCLE, 
                VehicleType.TRICYCLE, VehicleType.SCOOTER, VehicleType.INVALID));
        badFrame = new VehicleFrame("Bad Frame", 10, 10, Lists.newArrayList(VehicleType.INVALID));
        negativeFrame = new VehicleFrame("Negative Frame", -120, 10, Lists.newArrayList(VehicleType.SCOOTER, VehicleType.BICYCLE, VehicleType.TRICYCLE));
        tricycleOnlyFrame = new VehicleFrame("Trike Frame", 10, 10, Lists.newArrayList(VehicleType.TRICYCLE));
        
        goodWheel = new Wheel("Normal Wheel", 0, 0, Lists.newArrayList(VehicleType.BICYCLE), Lists.newArrayList(VehicleType.BICYCLE));
        badWheel = new Wheel("Impossible Wheel", 0, 0, Lists.newArrayList(VehicleType.INVALID), Lists.newArrayList(VehicleType.INVALID));
        negativeWheel = new Wheel("Negative Wheel", 10, -200, Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE, VehicleType.SCOOTER), Lists.newArrayList(VehicleType.BICYCLE, VehicleType.SCOOTER));
        scooterOnlyWheel = new Wheel("Scooter Wheel", 20, 30, Lists.newArrayList(VehicleType.SCOOTER), Lists.newArrayList(VehicleType.SCOOTER));
        
        goodPlant = new PowerPlant("Super pedals", 50, Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE));
        badPlant = new PowerPlant("Alien pedals", 50, Lists.newArrayList(VehicleType.INVALID));
        negativePowerPlant = new PowerPlant("Negative pedals", -5, Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE));
        scooterPlant = new PowerPlant("Motor", 700, Lists.newArrayList(VehicleType.SCOOTER));
        
        joe = new Rider("Joe", 60);
        littleTimmy = new Rider("Timmy", 1);
        lance = new Rider("Lance", 3600);
        negativeMan = new Rider("Captain Negative", -1000);
    }
    /**
     * Test method for {@link com.mishmash.alpha.Vehicle#isValid()}.
     */
    @Test
    public void testIsValid() {
        
        Vehicle greenTestVehicle = new Vehicle(VehicleType.BICYCLE, joe, goodFrame, goodPlant, 
                Lists.newArrayList(goodWheel, goodWheel));
        assertTrue(greenTestVehicle.isValid());
    }
    
    /**
     * You can add extra wheels, but it won't make a valid vehicle.
     */
    @Test
    public void testWheelCount() {
        Vehicle monstrosity = new Vehicle(VehicleType.BICYCLE, joe, goodFrame, goodPlant, 
                Lists.newArrayList(goodWheel, goodWheel, goodWheel, goodWheel, goodWheel));
        assertFalse(monstrosity.isValid());
        
        Vehicle accidentalUnicycle = new Vehicle(VehicleType.TRICYCLE, joe, goodFrame, goodPlant,
                Lists.newArrayList(goodWheel));
        assertFalse(accidentalUnicycle.isValid());
    }
    
    @Test
    public void testInvalidPartsForType() {
        Vehicle badFrameVehicle = new Vehicle(VehicleType.SCOOTER, joe, badFrame, scooterPlant, 
                Lists.newArrayList(scooterOnlyWheel, scooterOnlyWheel));
        Vehicle bikeForScooterPlant = new Vehicle(VehicleType.SCOOTER, joe, goodFrame, goodPlant,
                Lists.newArrayList(goodWheel, goodWheel));
        Vehicle badWheelBike = new Vehicle(VehicleType.BICYCLE, joe, goodFrame, goodPlant,
                Lists.newArrayList(goodWheel, badWheel));
        Vehicle negativeRiderBike = new Vehicle(VehicleType.BICYCLE, negativeMan, goodFrame, goodPlant, 
                Lists.newArrayList(goodWheel, goodWheel));
        
        assertFalse(badFrameVehicle.isValid());
        assertFalse(bikeForScooterPlant.isValid());
        assertFalse(badWheelBike.isValid());
        assertFalse(negativeRiderBike.isValid());
    }

    /**
     * Test method for {@link com.mishmash.alpha.Vehicle#getDistance()}.
     */
    @Test
    public void testGetDistance() {
        fail("Not yet implemented");
    }

}
