package com.example.processor;

import com.example.DaintyClass;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;

public class DaintyAnnotatedClass {

    private final String id;
    private String fullName;
    private String qualifiedSuperClassName;
    private String simpleTypeName;
    public TypeElement annotatedClassElement;
    private Class<?> clazz;

    public DaintyAnnotatedClass(TypeElement classElement) throws IllegalArgumentException {
        this.annotatedClassElement = classElement;
        DaintyClass annotation = classElement.getAnnotation(DaintyClass.class);
        id = annotation.id();

        if ("".equals(id)) {
            throw new IllegalArgumentException(
                    String.format(
                            "id() in @%s for class %s is null or empty! that's not allowed",
                            DaintyClass.class.getSimpleName(),
                            classElement.getQualifiedName().toString()));
        }

        try {
            Class<?> clazz = annotation.type();
            this.clazz = clazz;
            qualifiedSuperClassName = clazz.getCanonicalName();
            simpleTypeName = clazz.getSimpleName();
            fullName = clazz.getName();
        } catch (MirroredTypeException mte) {
            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
            TypeElement classTypeElement = (TypeElement) classTypeMirror.asElement();
            qualifiedSuperClassName = classTypeElement.getQualifiedName().toString();
            simpleTypeName = classTypeElement.getSimpleName().toString();
        }
    }


    public String getId() {
        return id;
    }

    public String getQualifiedFactoryGroupName() {
        return qualifiedSuperClassName;
    }

    public String getSimpleFactoryGroupName() {
        return simpleTypeName;
    }
    public TypeElement getTypeElement() {
        return annotatedClassElement;
    }
}
