package com.nossogame.bancoimobiliario.service;

import com.nossogame.bancoimobiliario.AbstractTest;
import io.github.classgraph.ClassGraph;
import io.github.classgraph.ClassInfo;
import io.github.classgraph.ScanResult;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServiceInheritanceTest {

//    @Test
    public void testAllControllersExtendAbstractController() {
        try (ScanResult scanResult = new ClassGraph()
                .acceptPackages("com.seuprojeto.controller")
                .enableClassInfo()
                .scan()) {

            for (ClassInfo classInfo : scanResult.getAllClasses()) {
                Class<?> clazz = classInfo.loadClass();

                if (!classInfo.isAbstract() && !classInfo.isInterface()) {
                    assertTrue(
                            AbstractTest.class.isAssignableFrom(clazz),
                            "A classe " + clazz.getName() + " não herda de AbstractTest"
                    );
                }
            }
        }
    }
}
