package com.ben.smith.reader;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;


/**
 * Created by bensmith on 11/7/17.
 */

public class Test_Runner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(read_unknown_file_Test.class);

        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }

        System.out.println(result.wasSuccessful());
    }
}
