package com.mishmash.alpha;

public class VehicleFactory {

    private static VehicleFactory instance;
    
    private VehicleFactory() {
        
    }
    
    public static VehicleFactory getInstance() {
        if (instance == null) {
            synchronized(VehicleFactory.class) {
                if (instance == null) {
                    instance = new VehicleFactory();
                }
            }
        }
        
        return instance;
    }
}
