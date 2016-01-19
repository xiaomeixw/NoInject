package com.sabria.noinject.process;


import com.sabria.noinject.InjectView;
import com.squareup.javawriter.JavaWriter;

import java.io.IOException;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.NoType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

@SupportedAnnotationTypes("com.sabria.noinject.InjectView")
public class NoKnifeProcessor extends AbstractProcessor {
    private static final String LITTLE_KNIFE_TAG = "$$NoKnife";


    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        Elements elementUtils = processingEnv.getElementUtils();
        Types typeUtils = processingEnv.getTypeUtils();
        Filer filer = processingEnv.getFiler();
        Messager messager = processingEnv.getMessager();


    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        final Set<? extends Element> annotatedElements = roundEnv
                .getElementsAnnotatedWith(InjectView.class);
        if (annotatedElements.size() > 0) {
            final Element firstAnnotatedElement = annotatedElements.iterator().next();
            final String packageName = processingEnv.getElementUtils()
                    .getPackageOf(firstAnnotatedElement).toString();
            final String hostActivityName = firstAnnotatedElement.getEnclosingElement()
                    .getSimpleName().toString();
            final String newSourceName = hostActivityName + LITTLE_KNIFE_TAG;
            try {
                JavaWriter writer = createWriter(newSourceName);
                createHeader(annotatedElements, packageName, writer);
                beginType(newSourceName, writer);
                beginMethod(hostActivityName, writer);
                emitStatements(annotatedElements, hostActivityName, writer);
                emitClose(writer);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    private JavaWriter createWriter(String newSourceName) throws IOException {
        final JavaFileObject sourceFile = processingEnv.getFiler()
                .createSourceFile(newSourceName);
        return new JavaWriter(sourceFile.openWriter());
    }

    private void createHeader(Set<? extends Element> annotatedElements, String packageName,
                              JavaWriter writer)
            throws IOException {
        writer.emitPackage(packageName);

        Map<String, Element> nonRepeatedImports = new HashMap<>();

        for (Element element : annotatedElements) {
            TypeMirror elementType = element.asType();

            if (isSubtypeOfType(elementType, "android.view.View")) {
                nonRepeatedImports.put(element.asType().toString(), element);
            } else {
                processingEnv.getMessager()
                        .printMessage(Diagnostic.Kind.ERROR, String.format(
                                "Variable: %s, is not of a type that subclasses android.view.View. @%s can only be used with Views",
                                element.getSimpleName().toString(),
                                InjectView.class.getSimpleName()));
            }
        }
        for (String importString : nonRepeatedImports.keySet()) {
            writer.emitImports(importString);
        }
    }

    private boolean isSubtypeOfType(TypeMirror typeMirror, String otherType) {


        if (typeMirror instanceof NoType) {

            return false;
        }

        if (otherType.equals(typeMirror.toString())) {

            return true;
        }

        DeclaredType declaredType = (DeclaredType) typeMirror;
        Element element = declaredType.asElement();
        TypeElement typeElement = (TypeElement) element;
        TypeMirror superType = typeElement.getSuperclass();
        if (isSubtypeOfType(superType, otherType)) {

            return true;
        }
        return false;
    }

    private void beginType(String newSourceName, JavaWriter writer) throws IOException {
        writer.beginType(newSourceName, "class", EnumSet.of(Modifier.PUBLIC));
    }

    private void beginMethod(String hostActivityname, JavaWriter writer) throws IOException {
        writer.beginMethod("void", "inject", EnumSet.of(Modifier.PUBLIC), hostActivityname,
                "target");
    }

    private void emitStatements(Set<? extends Element> annotatedElements, String hostActivityname,
                                JavaWriter writer) throws IOException {
        for (Element element : annotatedElements) {
            writer.emitStatement("((" + hostActivityname + ")target)." + element
                    .getSimpleName().toString() + " = " + "(" + element.asType()
                    .toString() + ")target.findViewById(" + element.getAnnotation(InjectView.class)
                    .value() + ")");
        }
    }

    private void emitClose(JavaWriter writer) throws IOException {
        writer.endMethod();
        writer.endType();
        writer.close();
    }
}