package com.hugo.daintyannotation;

import com.example.DaintyClass;
import com.example.DaintyField;

/**
 * Created by hugo on 2016/10/12.
 */
@DaintyClass(type = Dog.class, id = "fish")
public class Fish {

    @DaintyField(fieldName = "dogName")
    public String fishName;

    @DaintyField(fieldName = "dogId")
    public String fishId;

    @DaintyField(fieldName = "dogAge")
    public int fishAge;

    @DaintyField(fieldName = "canTrans")
    public boolean canTrans;
}
