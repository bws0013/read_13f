package com.ben.smith.reader;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by bensmith on 11/6/17.
 */
class read_unknown_file_Test {
    @org.junit.jupiter.api.Test
    void determine_file_type() {

        List<String> old_1 = old_1();



    }

    @org.junit.jupiter.api.Test
    void read_file() {

    }

    @org.junit.jupiter.api.Test
    void xml_type_getter() {

    }

    @org.junit.jupiter.api.Test
    void document_header_getter() {

    }

    @org.junit.jupiter.api.Test
    void print_files_in_directory() {

    }

    /**************************************************************************
     *
     * Setup methods for getting read in files
     *
     */

    private String file_storage_location = "./storage/";

    public List<String> old_1() {
        read_unknown_file r = new read_unknown_file();
        List<String> file_text = r.read_file(file_storage_location + "old_1.txt");
        return file_text;
    }

    public List<String> new_1() throws Exception {
        read_unknown_file r = new read_unknown_file();
        List<String> file_text = r.read_file(file_storage_location + "new_1.txt");
        return file_text;
    }

    public List<String> new_2() throws Exception {
        read_unknown_file r = new read_unknown_file();
        List<String> file_text = r.read_file(file_storage_location + "new_2.txt");
        return file_text;
    }

    public List<String> new_3() throws Exception {
        read_unknown_file r = new read_unknown_file();
        List<String> file_text = r.read_file(file_storage_location + "new_3.txt");
        return file_text;
    }




}