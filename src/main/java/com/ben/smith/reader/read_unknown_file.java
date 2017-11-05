package com.ben.smith.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bensmith on 11/4/17.
 */
public class read_unknown_file {

    /*
        Simplified version of a program I previously wrote to read and process the contents
        of 13f's into a database.
    */

    public static void main(String[] args) {

//        print_files_in_directory("./storage");
        List<String> file_text = read_file("./storage/old_1.txt");
        determine_file_type(file_text);

        file_text = read_file("./storage/new_1.txt");
        document_header_getter(file_text);

        file_text = read_file("./storage/new_2.txt");
        xml_type_getter(file_text);

    }

    // Used for determining what we will need to read the document with
    public static String determine_file_type(List<String> text_lines) {

        // Check for old_1
        for(String line : text_lines) {
            if (line.trim().equals("FORM 13F INFORMATION TABLE")) {
                return "old_1";
            }
        }



        return "unknown";
    }

    // Read a file into a string list
    public static List<String> read_file(String filename) {
        List<String> text_lines = new ArrayList<String>();

        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line = br.readLine();

            while (line != null) {
                text_lines.add(line);
                line = br.readLine();
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return text_lines;
    }

    // Get the type xml variant used in the 13f
    public static void xml_type_getter(List<String> text_lines) {
        int text_lines_length = text_lines.size();
        text_lines = text_lines.subList(text_lines_length - 10, text_lines_length);

        for(String line : text_lines) {
            System.out.println(line);
        }
    }


    // Get the cik and the CONFORMED PERIOD OF REPORT from a 13f
    public static void document_header_getter(List<String> text_lines) {
        text_lines = text_lines.subList(0, 20);
        for(int i = 0; i < text_lines.size(); i++) {
            text_lines.set(i, text_lines.get(i).replaceAll("\\s+",""));
        }

        String cik = "#";
        String report_period = "#";

        for(String line : text_lines) {
            String[] elements = line.split(":");
            if(elements[0].equals("CENTRALINDEXKEY")) {
                cik = elements[1];
            } else if(elements[0].equals("CONFORMEDPERIODOFREPORT")) {
                report_period = elements[1];
            }
        }

        System.out.printf("cik: %s, report period: %s\n", cik, report_period);
    }


    // This is being used for testing later on
    // Print the contents of a directory
    public static void print_files_in_directory(String directory) {
        File folder = new File(directory);
        File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
                System.out.println("File " + listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
                System.out.println("Directory " + listOfFiles[i].getName());
            }
        }
    }


}
