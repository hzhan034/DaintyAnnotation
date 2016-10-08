package com.example.processor;

import com.example.DaintyField;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Name;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class DaintyAnnotatedField {

    String annotationName;

//    private String mFieldName;
//
//    public DaintyAnnotatedField(Element variableElement) throws IllegalArgumentException {
//        DaintyField annotation = variableElement.getAnnotation(DaintyField.class);
//
//        if (variableElement.getKind() != ElementKind.FIELD) {
//            throw new IllegalArgumentException(
//                    String.format("Only fields can be annotated with @%s", DaintyField.class.getSimpleName()));
//        }
//
//        try {
//            String fieldName = annotation.fieldName();
//            mFieldName = fieldName;
//        } catch (MirroredTypeException mte) {
////            DeclaredType classTypeMirror = (DeclaredType) mte.getTypeMirror();
////            VariableElement classTypeElement = (VariableElement) classTypeMirror.asElement();
////            mFieldName = classTypeElement.getSimpleName().toString();
//        }
//    }
//
//    public String getName() {
//        return mFieldName;
//    }
//
//

    private VariableElement mFieldElement;

    public DaintyAnnotatedField(Element element) throws IllegalArgumentException {
        if (element.getKind() != ElementKind.FIELD) {
            throw new IllegalArgumentException(
                    String.format("Only fields can be annotated with @%s", DaintyField.class.getSimpleName()));
        }

        mFieldElement = (VariableElement) element;
        DaintyField bindView = mFieldElement.getAnnotation(DaintyField.class);
        annotationName = bindView.fieldName();

    }

    public String getAnnotationName(){
        return annotationName;
    }


    public Name getFieldName() {
        return mFieldElement.getSimpleName();
    }

    public TypeMirror getFieldType() {
        return mFieldElement.asType();
    }

}
