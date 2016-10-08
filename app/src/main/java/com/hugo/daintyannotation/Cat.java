package com.hugo.daintyannotation;

import com.example.DaintyClass;
import com.example.DaintyField;

@DaintyClass(type = Dog.class, id = "dog")
public class Cat {

    @DaintyField(fieldName = "dogName")
    public String catName;

    @DaintyField(fieldName = "dogId")
    public String catId;
}
