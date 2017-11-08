package com.ben.smith.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import static org.junit.Assert.assertEquals;

/**
 * Created by bensmith on 11/6/17.
 */

public class read_unknown_file_Test {

    public String file_storage_location = "./storage/";

    @Test
    public void determine_file_type() {

        List<String> old_1 = read_unknown_file.read_file(file_storage_location + "old_1.txt");
        List<String> new_1 = read_unknown_file.read_file(file_storage_location + "new_1.txt");
        List<String> new_2 = read_unknown_file.read_file(file_storage_location + "new_2.txt");
        List<String> new_3 = read_unknown_file.read_file(file_storage_location + "new_3.txt");


        String result_old_1 = read_unknown_file.determine_file_type(old_1);
        String result_new_1 = read_unknown_file.determine_file_type(new_1);
        String result_new_2 = read_unknown_file.determine_file_type(new_2);
        String result_new_3 = read_unknown_file.determine_file_type(new_3);

        String expected_result_old_1 = "old_1";
        String expected_result_new_1 = "";
        String expected_result_new_2 = "ns1";
        String expected_result_new_3 = "n1";

        assertEquals(expected_result_old_1, result_old_1);
        assertEquals(expected_result_new_1, result_new_1);
        assertEquals(expected_result_new_2, result_new_2);
        assertEquals(expected_result_new_3, result_new_3);

    }

    @Test
    public void read_file() {

    }

    @Test
    public void xml_type_getter() {

    }

    @Test
    public void document_header_getter() {

    }

    @Test
    public void print_files_in_directory() {

    }




}