package com.example.processor;

import com.example.DaintyClass;
import com.example.DaintyField;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.TypeName;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;

@SupportedAnnotationTypes({
        "com.example.DaintyField",
        "com.example.DaintyClass"
})
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@AutoService(Processor.class)
public class DaintyProcessor extends AbstractProcessor {


//    public static final String SUFFIX = "AutoGenerate";
//    public static final String PREFIX = "My_";
    private Messager messager;
    private Types typeUtils;
    private Elements elementUtils;
    private Filer filer;
    DaintyAnnotionCollection mDaintyAnnotionCollection;

    private Map<String, DaintyAnnotionCollection> daintyAnnotionColectionMap = new HashMap<>();

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);
        messager = processingEnv.getMessager();
        typeUtils = processingEnv.getTypeUtils();
        elementUtils = processingEnv.getElementUtils();
        filer = processingEnv.getFiler();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {

        processField(roundEnv);
        processClass(roundEnv);

        try {
            mDaintyAnnotionCollection.genCode().writeTo(processingEnv.getFiler());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return true;
    }


    private void processField(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(DaintyField.class)) {
            DaintyAnnotionCollection annotatedClass = getDaintyAnnotionCollection(element);
            DaintyAnnotatedField field = new DaintyAnnotatedField(element);
            annotatedClass.addField(field);
        }

    }

    private DaintyAnnotionCollection getDaintyAnnotionCollection(Element element) {
        TypeElement classElement = (TypeElement) element.getEnclosingElement();
        String fullClassName = classElement.getQualifiedName().toString();
        DaintyAnnotionCollection mDaintyAnnotionCollection = daintyAnnotionColectionMap.get(fullClassName);
        if (mDaintyAnnotionCollection == null) {
            mDaintyAnnotionCollection = new DaintyAnnotionCollection(classElement, elementUtils);
            daintyAnnotionColectionMap.put(fullClassName, mDaintyAnnotionCollection);
        }
        return mDaintyAnnotionCollection;
    }

    private boolean processClass(RoundEnvironment roundEnv) {

        boolean resultBoolean = false;
        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(DaintyClass.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        DaintyClass.class.getSimpleName());
                resultBoolean = true;
                break; // Exit processing
            }

            TypeElement typeElement = (TypeElement) annotatedElement;
            mDaintyAnnotionCollection = getDaintyAnnotionColection4Class(typeElement);

        }

        return resultBoolean;
    }

    private DaintyAnnotionCollection getDaintyAnnotionColection4Class(TypeElement element) {


        DaintyAnnotatedClass annotatedClass = null;
        try {
            annotatedClass = new DaintyAnnotatedClass(
                    element); // throws IllegalArgumentException

//                if (!isValidClass(annotatedClass)) {
//                    return true; // Error message printed, exit processing
//                }

        } catch (IllegalArgumentException e) {
            // @Factory.id() is empty
            error(element, e.getMessage());
        }

        String annotatedFullClassName = annotatedClass.getQualifiedFactoryGroupName().toString();
        DaintyAnnotionCollection mDaintyAnnotionCollection = daintyAnnotionColectionMap.get(TypeName.get(element.asType()).toString());
        if (mDaintyAnnotionCollection == null) {
            //TODO
            System.out.println("mDaintyAnnotionCollection is null --------------------------------------------------------------");
            System.out.println("mDaintyAnnotionCollection is null" + TypeName.get(element.asType()).toString());
//            mDaintyAnnotionCollection  = new DaintyAnnotionCollection();
        }
        mDaintyAnnotionCollection.setAnnotatedClassFullName(annotatedFullClassName);
        mDaintyAnnotionCollection.mTypeElement = element;
        mDaintyAnnotionCollection.setTransTarget(TypeName.get(element.asType()).toString());
        return mDaintyAnnotionCollection;

    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }
}
