package org.example;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(WestminsterSkinConsultationManagerTest.class);
        System.out.println("Result: " + result.wasSuccessful());
    }
}
