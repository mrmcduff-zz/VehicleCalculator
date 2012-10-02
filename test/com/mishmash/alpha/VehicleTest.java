/**
 * 
 */
package com.mishmash.alpha;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;
import com.mishmash.alpha.vehicleparts.IVehiclePart;
import com.mishmash.alpha.vehicleparts.PartUtils;
import com.mishmash.alpha.vehicleparts.PowerPlant;
import com.mishmash.alpha.vehicleparts.Rider;
import com.mishmash.alpha.vehicleparts.VehicleFrame;
import com.mishmash.alpha.vehicleparts.Wheel;

/**
 * Test class for vehicle construction (not from JSON). Because this needed to test construction
 * and validity of so many different kind of parts, this ended up being the test for Wheels, 
 * Riders, Frames, and PowerPlants as well.
 *
 */
public class VehicleTest {

    VehicleFrame goodFrame;
    VehicleFrame badFrame;
    VehicleFrame negativeFrame;
    VehicleFrame tricycleOnlyFrame;
    VehicleFrame speedyFrame;
    VehicleFrame slowFrame;
    
    Wheel goodWheel;
    Wheel badWheel;
    Wheel negativeWheel;
    Wheel scooterOnlyWheel;
    Wheel speedyWheel;
    Wheel squareWheel;
    
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
        List<VehicleType> allVehicles = Lists.newArrayList(VehicleType.validValues());
        goodFrame = new VehicleFrame("Good Frame", 10, 10, Lists.newArrayList(VehicleType.BICYCLE, 
                VehicleType.TRICYCLE, VehicleType.SCOOTER, VehicleType.INVALID));
        badFrame = new VehicleFrame("Bad Frame", 10, 10, Lists.newArrayList(VehicleType.INVALID));
        speedyFrame = new VehicleFrame("Speedy Frame", 30, 50, Lists.newArrayList(VehicleType.BICYCLE, 
                VehicleType.TRICYCLE, VehicleType.SCOOTER));
        slowFrame = new VehicleFrame("Good Frame", -40, -60, Lists.newArrayList(VehicleType.BICYCLE, 
                VehicleType.TRICYCLE, VehicleType.SCOOTER));
        
        negativeFrame = new VehicleFrame("Negative Frame", -120, 10, Lists.newArrayList(VehicleType.SCOOTER, VehicleType.BICYCLE, VehicleType.TRICYCLE));
        tricycleOnlyFrame = new VehicleFrame("Trike Frame", 10, 10, Lists.newArrayList(VehicleType.TRICYCLE));
        
        goodWheel = new Wheel("Normal Wheel", 0, 0, Lists.newArrayList(VehicleType.BICYCLE), Lists.newArrayList(VehicleType.BICYCLE));
        badWheel = new Wheel("Impossible Wheel", 0, 0, Lists.newArrayList(VehicleType.INVALID), Lists.newArrayList(VehicleType.INVALID));
        negativeWheel = new Wheel("Negative Wheel", 10, -200, Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE, VehicleType.SCOOTER), Lists.newArrayList(VehicleType.BICYCLE, VehicleType.SCOOTER));
        scooterOnlyWheel = new Wheel("Scooter Wheel", 20, 30, Lists.newArrayList(VehicleType.SCOOTER), Lists.newArrayList(VehicleType.SCOOTER));
        speedyWheel = new Wheel("Speedy Wheel", 60, 70, allVehicles, allVehicles);
        squareWheel = new Wheel("Square Wheel", -90, -95, allVehicles, allVehicles);
        
        goodPlant = new PowerPlant("Super pedals", 30, Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE));
        badPlant = new PowerPlant("Alien pedals", 50, Lists.newArrayList(VehicleType.INVALID));
        negativePowerPlant = new PowerPlant("Negative pedals", -5, Lists.newArrayList(VehicleType.BICYCLE, VehicleType.TRICYCLE));
        scooterPlant = new PowerPlant("Motor", 80, Lists.newArrayList(VehicleType.SCOOTER));
        
        joe = new Rider("Joe", 60);
        littleTimmy = new Rider("Timmy", 10);
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
        Vehicle invalidFrameScooter = new Vehicle(VehicleType.SCOOTER, joe, tricycleOnlyFrame, scooterPlant,
                Lists.newArrayList(scooterOnlyWheel, scooterOnlyWheel));
        
        
        assertFalse(badFrameVehicle.isValid());
        assertFalse(bikeForScooterPlant.isValid());
        assertFalse(badWheelBike.isValid());
        assertFalse(invalidFrameScooter.isValid());
        
    }
    
    @Test
    public void testNegativeParts() {
        Vehicle negativeRiderBike = new Vehicle(VehicleType.BICYCLE, negativeMan, goodFrame, goodPlant, 
                Lists.newArrayList(goodWheel, goodWheel));
        Vehicle negativePlantBike = new Vehicle(VehicleType.BICYCLE, joe, goodFrame, negativePowerPlant, 
                Lists.newArrayList(goodWheel, goodWheel));
        Vehicle negativeWheelBike = new Vehicle(VehicleType.BICYCLE, joe, goodFrame, goodPlant, 
                Lists.newArrayList(negativeWheel, goodWheel));
        Vehicle negativeFrameBike = new Vehicle(VehicleType.BICYCLE, joe, negativeFrame, goodPlant,
                Lists.newArrayList(goodWheel, goodWheel));
        
        assertFalse(negativeRiderBike.isValid());
        assertFalse(negativePlantBike.isValid());
        assertFalse(negativeWheelBike.isValid());
        assertFalse(negativeFrameBike.isValid());

        
    }

    /**
     * Test method for {@link com.mishmash.alpha.Vehicle#getDistance()}.
     */
    @Test
    public void testGetDistance() {
        /*
         * Here come the hand calculations
         */
        
        Vehicle normalVehicle = new Vehicle(VehicleType.BICYCLE, joe, goodFrame, goodPlant, 
                Lists.newArrayList(goodWheel, goodWheel));
        Vehicle joeOnAScooter = new Vehicle(VehicleType.SCOOTER, joe, goodFrame, scooterPlant, 
                Lists.newArrayList(scooterOnlyWheel, scooterOnlyWheel));
        Vehicle tourDeFrance = new Vehicle(VehicleType.SCOOTER, lance, speedyFrame, scooterPlant,
                Lists.newArrayList(speedyWheel, speedyWheel));
        Vehicle babySteps = new Vehicle(VehicleType.TRICYCLE, littleTimmy, slowFrame, goodPlant, 
                Lists.newArrayList(squareWheel, squareWheel));
        
        ModifierStackingOperation add = ModifierStackingOperation.ADD;
        ModifierStackingOperation mult = ModifierStackingOperation.MULTIPLY;
        
        double addNormal = 36.3;
        double multNormal = 36.3; 
        double addJoe = 204;
        double multJoe = 235.57248;
        double addTour = 34800;
        double multTour = 69249.024;
        double addBaby = 0;
        double multBaby = 0.00003;
        
        // 60*30*1.1*1.1/60
        assertEquals(addNormal, normalVehicle.getDistance(add), IVehiclePart.DELTA);
        // 60*30*1.1*1.1/60
        assertEquals(multNormal, normalVehicle.getDistance(mult), IVehiclePart.DELTA);
        // 60*80*(1+0.2+0.2+0.1)*(1+0.3+0.3+0.1)/60
        assertEquals(addJoe, joeOnAScooter.getDistance(add), IVehiclePart.DELTA);
        // 60*80*(1.2*1.2*1.1)*(1.3*1.3*1.1)/60
        assertEquals(multJoe, joeOnAScooter.getDistance(mult), IVehiclePart.DELTA);
        // 3600*80*(1 + 0.6 + 0.6 + 0.3)*(1 + 0.7 + 0.7 + 0.5)/60
        assertEquals(addTour, tourDeFrance.getDistance(add), IVehiclePart.DELTA);
        // 3600*80*1.6*1.6*1.7*1.7*1.3*1.5/60
        assertEquals(multTour, tourDeFrance.getDistance(mult), IVehiclePart.DELTA);
        // Set to zero because two square wheels would give the vehicle both negative time 
        // and negative speed with additive stacking of modifiers
        assertEquals(addBaby, babySteps.getDistance(add), IVehiclePart.DELTA);
        // 10*30*0.1*0.1*0.05*0.05*0.6*0.4/60
        assertEquals(multBaby, babySteps.getDistance(mult), IVehiclePart.DELTA);
    }
    
    @Test
    public void testInvalidDistances() {
        Vehicle badFrameVehicle = new Vehicle(VehicleType.SCOOTER, joe, badFrame, scooterPlant, 
                Lists.newArrayList(scooterOnlyWheel, scooterOnlyWheel));
        Vehicle negativeWheelBike = new Vehicle(VehicleType.BICYCLE, joe, goodFrame, goodPlant, 
                Lists.newArrayList(negativeWheel, goodWheel));
        
        double addFrameDistance = badFrameVehicle.getDistance(ModifierStackingOperation.ADD);
        double multFrameDistance = badFrameVehicle.getDistance(ModifierStackingOperation.MULTIPLY);
        
        double addWheelDistance = negativeWheelBike.getDistance(ModifierStackingOperation.ADD);
        double multWheelDistance = negativeWheelBike.getDistance(ModifierStackingOperation.MULTIPLY);
        
        assertTrue(PartUtils.doubleEquals(0, addFrameDistance));
        assertTrue(PartUtils.doubleEquals(0, multFrameDistance));
        assertTrue(PartUtils.doubleEquals(0, addWheelDistance));
        assertTrue(PartUtils.doubleEquals(0, multWheelDistance));
    }

}
