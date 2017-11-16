package com.ben.smith.reader;

import java.io.*;
import java.util.*;

/**
 * Created by bensmith on 11/4/17.
 * Main file for reading stuff
 */
public class read_unknown_file {

    /*
        Simplified version of a program I previously wrote to read and process the contents
        of 13f's into a database.
    */

    public static void main(String[] args) {

        String db_name = "filings.db";

        List<String> ciks = new ArrayList<>();
        ciks.add("0001607863");

        String[] urls = web_scraper.createFinDocs(ciks.get(0));
        List<String> file_text = web_scraper.get_file_contents(urls[0]);

        add_to_database(file_text, db_name);
    }

    // This is to be used later when users can select what they are looking to do
    public static void menu() {

        Scanner sc = new Scanner(System.in);

        String intro = "\nEnter the (c)haracter from the options listed";
        String options =
            "Read data into a new (d)atabase\n" +
            "Read data into an (e)xisting database\n" +
            "Read data into a new (c)sv\n" +
            "Read data into an e(x)isting csv" +
            "(q)uit";
        String write_char = ">";

    }

    // Add all of the data from a file to our database
    public static void add_to_database(List<String> text_lines, String db_name) {
        Map<String, Set<String>> cik_to_conf_period
                = database_layer.get_added_files(db_name);

        List<Asset> assets = pass_to_processors(text_lines);
        if(assets.size() == 0) return;

        String cik = assets.get(0).getCik();
        String conf_period = assets.get(0).getConfirmation_period();

        if(cik_to_conf_period.containsKey(cik)) {
            Set<String> conf_periods = cik_to_conf_period.get(cik);
            if(conf_periods.contains(conf_period)) {
                System.out.println("not added");
                return;
            }
        }

        assets = pass_to_processors(text_lines);
        database_layer.add_date(db_name, assets);
        System.out.println("added");
    }

    // Determine which file processor we need to pass our file to and get the assets from that file
    public static List<Asset> pass_to_processors(List<String> text_lines) {
        List<Asset> assets = new ArrayList<>();

        String file_type = determine_file_type(text_lines);
        if (file_type.equals("#")) {
            System.out.println("File type unknown!");
            return assets;
        } else if (file_type.equals("old_1")) {
            assets = file_processor_old.get_assets(text_lines);
            assets = add_asset_headers(assets, text_lines);
        } else {
            assets = file_processor_new.get_assets(text_lines, file_type);
            assets = add_asset_headers(assets, text_lines);
        }
        return assets;
    }

    // Add the cik and confirmation period to all of the assets
    private static List<Asset> add_asset_headers(List<Asset> assets, List<String> text_lines) {

        String[] header = document_header_getter(text_lines);

        List<Asset> assets_fixed = new ArrayList<>();

        for(Asset a : assets) {
            a.add_identifying_info(header[0], header[1]);
            assets_fixed.add(a);
        }

        return assets_fixed;
    }

    // Used for determining what we will need to read the document with
    static String determine_file_type(List<String> text_lines) {

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
    static List<String> read_file(String filename) {
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
    private static String xml_type_getter(List<String> text_lines) {
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


    /*
     Gets the cik and the CONFORMED PERIOD OF REPORT from a 13f

     We are also addressing ammended (13f-hr/a) files here,
     we just want to skip them as for now, we may want to keep later
     in the future there may be an option involving changing global variables
     */
    private static String[] document_header_getter(List<String> text_lines) {
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
            if(line.equals("CONFORMEDSUBMISSIONTYPE:13F-HR/A")) {
                return new String[]{"#", "#"};
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
