package com.example.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Modifier;

/**
 * Created by hugo on 2016/10/12.
 */

public class DaintyCodeGenerator {

    private Map<String, DaintyAnnotionCollection> daintyAnnotionColectionMap;
    private Filer filer;
    List<MethodSpec> methodList = new ArrayList<>();

    public DaintyCodeGenerator(Map<String, DaintyAnnotionCollection> daintyAnnotionColectionMap, Filer filer) {
        this.filer = filer;
        this.daintyAnnotionColectionMap = daintyAnnotionColectionMap;
    }

    public void genCode() {
        for (DaintyAnnotionCollection c : daintyAnnotionColectionMap.values()) {
            methodList.add(c.genMothed());
        }
        try {
            genFile().writeTo(filer);
        } catch (IOException e) {
            e.printStackTrace();
        }
//                mDaintyAnnotionCollection.genMothed().writeTo(processingEnv.getFiler());
    }

    public JavaFile genFile() {
        TypeSpec.Builder builder = TypeSpec.classBuilder("BeanUtils")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL);
        for (MethodSpec methodSpec : methodList) {
            builder.addMethod(methodSpec);
        }
        TypeSpec helloWorld = builder.build();
        JavaFile javaFile = JavaFile.builder("com.hugo.daintyannotation", helloWorld)
                .build();
        return javaFile;
    }
}
