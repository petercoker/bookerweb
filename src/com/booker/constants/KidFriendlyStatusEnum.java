package com.booker.constants;

public enum KidFriendlyStatusEnum {

	//its private because it does not need to instantiated  
	
	APPROVED("approved"),
	REJECTED( "rejected"),
	UNKNOWN("unknown");
	
    private KidFriendlyStatusEnum (String name) {
    	this.name = name;
    }
    
    private String name;
    public String getName() {
    	return name;
    }
	
}
