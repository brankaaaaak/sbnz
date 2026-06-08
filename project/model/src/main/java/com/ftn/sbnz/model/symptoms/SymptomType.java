package com.ftn.sbnz.model.symptoms;
import java.lang.annotation.*;


@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface SymptomType {
    String value();
}
