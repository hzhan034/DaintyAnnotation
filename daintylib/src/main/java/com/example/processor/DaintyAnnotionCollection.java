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

    public static final String INPUT_TYPE = "inputType";
    private String transTarget;
    private List<DaintyAnnotatedField> fieldList = new ArrayList<>();
    public TypeElement mClassElement;
    public Elements mElementUtils;
//    public DaintyAnnotatedClass mDaintyAnnotatedClass;
    public TypeElement mTypeElement;
    private String annotatedClassFullName;

    public void setAnnotatedClassFullName(String annotatedClassFullName) {
        this.annotatedClassFullName = annotatedClassFullName;
    }



    public DaintyAnnotionCollection(TypeElement classElement, Elements elementUtils) {
        this.mClassElement = classElement;
        this.mElementUtils = elementUtils;
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

    public void addField(DaintyAnnotatedField field) {
        fieldList.add(field);
    }

    public JavaFile genCode() {


//        TypeElement superClassName = mElementUtils.getTypeElement(transTarget);
//        PackageElement pkg = mElementUtils.getPackageOf(superClassName);

        MethodSpec main = null;


        MethodSpec.Builder transMethodBuilder = MethodSpec.methodBuilder("transMethod_" + TypeName.get(mTypeElement.asType()).toString())
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(TypeName.OBJECT)
                .addParameter(TypeName.get(mTypeElement.asType()), INPUT_TYPE)
                .addStatement(annotatedClassFullName + " newdog = new " + annotatedClassFullName + "()");

//                .addStatement("newdog.dongName = \"www\"");
//                    .addStatement("$T.out.println($S)", System.class, ""+ annotatedField.getName())

        for (DaintyAnnotatedField daintyAnnotatedField : fieldList) {
            if (TypeName.get(daintyAnnotatedField.getFieldType()).toString().equals("java.lang.String")){
                transMethodBuilder.beginControlFlow("if("+ INPUT_TYPE +"."+ daintyAnnotatedField.getFieldName() + " != null)")
                        .addStatement("newdog."+daintyAnnotatedField.getAnnotationName()+" = " + INPUT_TYPE +"."+ daintyAnnotatedField.getFieldName())
                        .endControlFlow();
            }else if(TypeName.get(daintyAnnotatedField.getFieldType()).toString().equals("int")){
                transMethodBuilder.beginControlFlow("if("+ INPUT_TYPE +"."+ daintyAnnotatedField.getFieldName() + " != 0)")
                        .addStatement("newdog."+daintyAnnotatedField.getAnnotationName()+" = " + INPUT_TYPE +"."+ daintyAnnotatedField.getFieldName())
                        .endControlFlow();
            }
//            transMethodBuilder.addStatement("$T.out.println($S)", System.class, "" + daintyAnnotatedField.getFieldName()+ "  "+ daintyAnnotatedField.getAnnotationName());
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
