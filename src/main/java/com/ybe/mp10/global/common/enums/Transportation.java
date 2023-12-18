package com.ybe.mp10.global.common.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.ybe.mp10.domain.payment.exception.NoSuchTransportationException;
import lombok.Getter;

@Getter
public enum Transportation {


    CAR("차량"), WALK("도보");

    private final String name;

    Transportation(String name) {
        this.name = name;
    }

    @JsonCreator
    public static Transportation from(String name){
        for(Transportation transportation : Transportation.values()){
            if(transportation.name.equals(name)){
                return transportation;
            }
        }

        throw NoSuchTransportationException.create();
    }
}
