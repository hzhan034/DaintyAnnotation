package com.example.processor;

import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.util.ArrayList;
import java.util.List;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;

public class DaintyAnnotionCollection {
    private String transTarget;
    private List<DaintyAnnotatedField> fieldList = new ArrayList<>();
    public TypeElement mClassElement;
    public Elements mElementUtils;
    public DaintyAnnotatedClass mDaintyAnnotatedClass;
    public TypeElement mTypeElement;

    public DaintyAnnotionCollection(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
    }

    public DaintyAnnotionCollection() {
    }

    public String getTransTarget() {
        return transTarget;
    }

    public void setTransTarget(String transTarget) {
        this.transTarget = transTarget;
    }

    public List<DaintyAnnotatedField> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<DaintyAnnotatedField> fieldList) {
        this.fieldList = fieldList;
    }

    public void addField(DaintyAnnotatedField field) {
        fieldList.add(field);
    }

    public JavaFile genCode() {


        TypeElement superClassName = mElementUtils.getTypeElement(transTarget);
//        PackageElement pkg = mElementUtils.getPackageOf(superClassName);

        MethodSpec main = null;


        MethodSpec.Builder transMethodBuilder = MethodSpec.methodBuilder("transCat2Dog")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.OBJECT)
                .addParameter(TypeName.get(mTypeElement.asType()), "cat")
                .addStatement(mDaintyAnnotatedClass.getQualifiedFactoryGroupName() + " newdog = new " + mDaintyAnnotatedClass.getQualifiedFactoryGroupName() + "()");

//                .addStatement("newdog.dongName = \"www\"");
//                    .addStatement("$T.out.println($S)", System.class, ""+ annotatedField.getName())

        for (DaintyAnnotatedField daintyAnnotatedField : fieldList) {
            transMethodBuilder.addStatement("newdog."+daintyAnnotatedField.getAnnotationName()+" = " + "cat."+daintyAnnotatedField.getFieldName());
            transMethodBuilder.addStatement("$T.out.println($S)", System.class, "" + daintyAnnotatedField.getFieldName()+ "  "+ daintyAnnotatedField.getAnnotationName());
        }


        transMethodBuilder.addStatement("return newdog");
        main =  transMethodBuilder.build();

//                    .addStatement("$T.out.println($S)", System.class, ""+ field.getFieldName())
////                    .addStatement("return newdog")
//                    .build();
//
//            TypeSpec helloWorld = TypeSpec.classBuilder("BeanUtils")
//                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                    .addMethod(main)
//                    .build();
//
//            JavaFile javaFile = JavaFile.builder("com.hugo.daintyannotation", helloWorld)
//                    .build();
//
//            try {
//                javaFile.writeTo(processingEnv.getFiler());
//            } catch (IOException e) {
//                e.printStackTrace();
//            }


        TypeSpec helloWorld = TypeSpec.classBuilder("BeanUtils")
                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                .addMethod(main)
                .build();

        JavaFile javaFile = JavaFile.builder("com.hugo.daintyannotation", helloWorld)
                .build();

        return javaFile;


    }


}
