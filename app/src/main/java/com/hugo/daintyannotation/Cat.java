package com.hugo.daintyannotation;

import com.example.DaintyClass;
import com.example.DaintyField;

@DaintyClass(type = Dog.class, id = "cat")
public class Cat {

    @DaintyField(fieldName = "dogName")
    public String catName;

    @DaintyField(fieldName = "dogId")
    public String catId;

    @DaintyField(fieldName = "dogAge")
    public int catAge;

    @DaintyField(fieldName = "canTrans")
    public boolean canTrans;

}
