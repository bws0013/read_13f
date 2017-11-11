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

        String filename = "./storage/old_1.txt";
        List<Asset> assets = pass_to_processors(filename);

        for(Asset a : assets) {
            a.print_all_fields();
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        filename = "./storage/new_1.txt";
        assets = pass_to_processors(filename);

        for(Asset a : assets) {
            a.print_all_fields();
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        filename = "./storage/new_2.txt";
        assets = pass_to_processors(filename);

        for(Asset a : assets) {
            a.print_all_fields();
        }

        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");

        filename = "./storage/new_3.txt";
        assets = pass_to_processors(filename);

        for(Asset a : assets) {
            a.print_all_fields();
        }
    }

    public static List<Asset> pass_to_processors(String filename) {
        List<Asset> assets = null;

        String file_type = determine_file_type(filename);
        if (file_type.equals("#")) {
            System.out.println("File type unknown!");
            System.exit(1);
        } if (file_type.equals("old_1")) {
            assets = file_processor_old.get_assets(filename);
            assets = add_asset_headers(assets, filename);
        } else {
            assets = file_processor_new.get_assets(filename, file_type);
            assets = add_asset_headers(assets, filename);
        }

        return assets;

    }

    public static List<Asset> add_asset_headers(List<Asset> assets, String filename) {

        List<String> file_text = read_file(filename);
        String[] header = document_header_getter(file_text);

        List<Asset> assets_fixed = new ArrayList<>();

        for(Asset a : assets) {
            a.add_identifying_info(header[0], header[1]);
            assets_fixed.add(a);
        }

        return assets_fixed;
    }

    public static String determine_file_type(String filename) {
        List<String> text_lines = read_file(filename);
        return determine_file_type(text_lines);
    }

    // Used for determining what we will need to read the document with
    public static String determine_file_type(List<String> text_lines) {

        // Check for old_1
        for(String line : text_lines) {
            if (line.trim().equals("FORM 13F INFORMATION TABLE")) {
                return "old_1";
            }
        }
        String xml_type = xml_type_getter(text_lines);

        return xml_type;
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
    public static String xml_type_getter(List<String> text_lines) {
        int text_lines_length = text_lines.size();
        text_lines = text_lines.subList(text_lines_length - 10, text_lines_length);
        for(int i = 0; i < text_lines.size(); i++) {
            text_lines.set(i, text_lines.get(i).replaceAll("\\s+",""));
        }

        String xml_type = "#";

        for(String line : text_lines) {
            String[] elements = line.split(":");
            if (elements[0].equals("</informationTable>")) {
                xml_type = "";
                break;
            } else if (elements[0].equals("</n1")) {
                xml_type = "n1";
                break;
            } else if (elements[0].equals("</ns1")) {
                xml_type = "ns1";
                break;
            }
        }

        return xml_type;
    }


    // Get the cik and the CONFORMED PERIOD OF REPORT from a 13f
    public static String[] document_header_getter(List<String> text_lines) {
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

        return new String[]{cik, report_period};
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
