package com.example.processor;

import com.example.DaintyClass;
import com.example.DaintyField;
import com.google.auto.service.AutoService;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

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
import javax.lang.model.element.Modifier;
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
//        processDebug(roundEnv);
//        TypeElement superClassName = elementUtils.getTypeElement(transTarget);
//        PackageElement pkg = elementUtils.getPackageOf(superClassName);
//
//        MethodSpec main = null;
//        main = MethodSpec.methodBuilder("transCat2Dog")
//                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                .returns(TypeName.OBJECT)
//                .addParameter(TypeName.get(typeElement.asType()), "cat")
//                .addStatement(annotatedClass.getQualifiedFactoryGroupName() + " newdog = new " + annotatedClass.getQualifiedFactoryGroupName() + "()")
//                .addStatement("newdog.dongName = \"www\"")
////                    .addStatement("$T.out.println($S)", System.class, ""+ annotatedField.getName())
//                .addStatement("return newdog")
//                .build();
//
//        TypeSpec helloWorld = TypeSpec.classBuilder("BeanUtils")
//                .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
//                .addMethod(main)
//                .build();
//
//        JavaFile javaFile = JavaFile.builder("com.hugo.daintyannotation", helloWorld)
//                .build();

//        System.out.println("------------------------------");
//        for (Element element : roundEnv.getElementsAnnotatedWith(DaintyMethod.class)) {
//            if (element.getKind() == ElementKind.METHOD) {
//                ExecutableElement executableElement = (ExecutableElement) element;
//                System.out.println(executableElement.getSimpleName());
//
//                System.out.println(executableElement.getReturnType().toString());
//
//                List<? extends VariableElement> params = executableElement.getParameters();
//                for (VariableElement variableElement : params) {
//                    System.out.println(variableElement.getSimpleName());
//                }
//                System.out.println(executableElement.getAnnotation(DaintyMethod.class).value());
//            }
//            System.out.println("------------------------------");
//        }


//        for (TypeElement te : annotations) {
//            for (Element e : roundEnv.getElementsAnnotatedWith(te)) {
//                Messager messager = processingEnv.getMessager();
//                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.toString());
//                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getSimpleName());
//                messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + e.getEnclosingElement().toString());
//
//                TestAnnotation annotation = e.getAnnotation(TestAnnotation.class);
//
//                String name = e.getSimpleName().toString();
//                char c = Character.toUpperCase(name.charAt(0));
//                name = String.valueOf(c+name.substring(1));
//
//                Element enclosingElement = e.getEnclosingElement();
//                String enclosingQualifiedName;
//                if(enclosingElement instanceof PackageElement){
//                    enclosingQualifiedName = ((PackageElement)enclosingElement).getQualifiedName().toString();
//                }else {
//                    enclosingQualifiedName = ((TypeElement)enclosingElement).getQualifiedName().toString();
//                }
//                try {
//                    String genaratePackageName = enclosingQualifiedName.substring(0, enclosingQualifiedName.lastIndexOf('.'));
//                    String genarateClassName = PREFIX + enclosingElement.getSimpleName() + SUFFIX;
//
//                    JavaFileObject f = processingEnv.getFiler().createSourceFile(genarateClassName);
//                    messager.printMessage(Diagnostic.Kind.NOTE, "Printing: " + f.toUri());
//                    Writer w = f.openWriter();
//                    try {
//                        PrintWriter pw = new PrintWriter(w);
//                        pw.println("package " + genaratePackageName + ";");
//                        pw.println("\npublic class " + genarateClassName + " { ");
//                        pw.println("\n    /**  */");
//                        pw.println("    public static void print" + name + "() {");
//                        pw.println("        //  " + enclosingElement.toString());
//                        pw.println("        System.out.println(\" "+f.toUri()+"\");");
//                        pw.println("        System.out.println(\": "+e.toString()+"\");");
//                        pw.println("        System.out.println(\": "+annotation.value()+"\");");
//                        pw.println("    }");
//                        pw.println("}");
//                        pw.flush();
//                    } finally {
//                        w.close();
//                    }
//                } catch (IOException x) {
//                    processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR,
//                            x.toString());
//                }
//            }
//        }

        return true;
    }

    private void processDebug(RoundEnvironment roundEnv) {

        for (Element annotatedElement : roundEnv.getElementsAnnotatedWith(DaintyClass.class)) {
            if (annotatedElement.getKind() != ElementKind.CLASS) {
                error(annotatedElement, "Only classes can be annotated with @%s",
                        DaintyClass.class.getSimpleName());
                break; // Exit processing
            }

            TypeElement typeElement = (TypeElement) annotatedElement;
            DaintyAnnotatedClass annotatedClass = null;
            try {
                annotatedClass = new DaintyAnnotatedClass(
                        typeElement); // throws IllegalArgumentException

//                if (!isValidClass(annotatedClass)) {
//                    return true; // Error message printed, exit processing
//                }

            } catch (IllegalArgumentException e) {
                // @Factory.id() is empty
                error(typeElement, e.getMessage());
            }

//            TypeElement superClassName = elementUtils
//                    .getTypeElement(annotatedClass.getQualifiedFactoryGroupName());
//            PackageElement pkg = elementUtils.getPackageOf(superClassName);
            String fullClassName4Field = null;
            for (Element element : roundEnv.getElementsAnnotatedWith(DaintyField.class)) {

                TypeElement classElement = (TypeElement) element.getEnclosingElement();
                fullClassName4Field = classElement.getQualifiedName().toString();
//                DaintyAnnotatedField field = new DaintyAnnotatedField(element);
//                annotatedClass.addField(field);

                //TODO move to colection to gen code
//            MethodSpec main = null;
//            main = MethodSpec.methodBuilder("transCat2Dog")
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                    .returns(TypeName.VOID)
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
            }

            MethodSpec main = null;
            main = MethodSpec.methodBuilder("transCat2Dog")
                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                    .returns(TypeName.OBJECT)
                    .addParameter(TypeName.get(typeElement.asType()), "cat")
                    .addStatement(annotatedClass.getQualifiedFactoryGroupName() + " newdog = new " + annotatedClass.getQualifiedFactoryGroupName() + "()")
                    .addStatement("newdog.dongName = \"www\"")
                    .addStatement("$T.out.println($S)", System.class, "" + TypeName.get(typeElement.asType()).toString())
                    .addStatement("$T.out.println($S)", System.class, "" + fullClassName4Field)
                    .addStatement("return newdog")
                    .build();

            TypeSpec helloWorld = TypeSpec.classBuilder("BeanUtils")
                    .addModifiers(Modifier.PUBLIC, Modifier.FINAL)
                    .addMethod(main)
                    .build();

            JavaFile javaFile = JavaFile.builder("com.hugo.daintyannotation", helloWorld)
                    .build();

            try {
                javaFile.writeTo(processingEnv.getFiler());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void processField(RoundEnvironment roundEnv) {
        for (Element element : roundEnv.getElementsAnnotatedWith(DaintyField.class)) {

            DaintyAnnotionCollection annotatedClass = getDaintyAnnotionColection(element);
            DaintyAnnotatedField field = new DaintyAnnotatedField(element);
            annotatedClass.addField(field);

            //TODO move to colection to gen code
//            MethodSpec main = null;
//            main = MethodSpec.methodBuilder("transCat2Dog")
//                    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
//                    .returns(TypeName.VOID)
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
        }

    }

    private DaintyAnnotionCollection getDaintyAnnotionColection(Element element) {
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

        String fullClassName = annotatedClass.getQualifiedFactoryGroupName().toString();
        DaintyAnnotionCollection mDaintyAnnotionCollection = daintyAnnotionColectionMap.get(TypeName.get(element.asType()).toString());
        if (mDaintyAnnotionCollection == null) {
            //TODO
            System.out.println("mDaintyAnnotionCollection is null --------------------------------------------------------------");
            System.out.println("mDaintyAnnotionCollection is null" + TypeName.get(element.asType()).toString());
//            mDaintyAnnotionCollection  = new DaintyAnnotionCollection();
        }
        mDaintyAnnotionCollection.mDaintyAnnotatedClass = annotatedClass;
        mDaintyAnnotionCollection.mTypeElement = element;
        mDaintyAnnotionCollection.setTransTarget(fullClassName);
        return mDaintyAnnotionCollection;

    }

    private void error(Element e, String msg, Object... args) {
        messager.printMessage(
                Diagnostic.Kind.ERROR,
                String.format(msg, args),
                e);
    }


}
