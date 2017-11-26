package com.ben.smith.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by bensmith on 11/13/17.
 *
 * Outputting as csv and json (maybe) will be here.
 * Later on I will see about adding the ability to export the data in individual files,
 * instead of all the output going to a single file, but just use excel to figure that out
 */
public class Output_To_Csv {


    public static void main(String[] args) {

        String output_dir = "./output_for_csvs/";
        String output_file = output_dir + "first.csv";

        List<String> files_to_csvify = new ArrayList<>();
        files_to_csvify.add("./storage/old_1.txt");
        files_to_csvify.add("./storage/new_1.txt");

        create_csv_from_multiple_files(files_to_csvify, output_file);
    }

    // Create a single csv file containing info from all the individual 13f filings.
    public static void create_csv_from_multiple_files(List<String> filenames, String output_file_name) {

        // The below removes all duplicates from the filenames list before proceeding
        Set<String> files_set = new HashSet<>();
        for(String filename : filenames) { files_set.add(filename); }
        List<String> temp_list = new ArrayList<>();
        temp_list.addAll(files_set);
        filenames = temp_list;

        // This actually processes the files and sends them to the output csv
        for(String filename : filenames) {
            List<String> text_lines = Main.read_file(filename);
            List<Asset> assets = Main.pass_to_processors(text_lines);
            print_to_csv(output_file_name, assets);
        }
    }

    // Get the headers for our csv file, only add them if the file doesnt already exist
    public static String get_headers() {
        String line = "";
        line += "\"cik\",";
        line += "\"confirmation_period\",";
        line += "\"name\",";
        line += "\"title\",";
        line += "\"cusip\",";
        line += "\"excel_cusip\",";
        line += "\"cash_value\",";
        line += "\"num_shares\",";
        line += "\"type\",";
        line += "\"discretion\",";
        line += "\"submit_date\"";
        return line;
    }

    // Add all of our assets to a csv file that we output
    public static void print_to_csv(String output_filename, List<Asset> assets) {

        StringBuilder sb;

        File f = new File(output_filename);
        if(f.exists() && !f.isDirectory()) {
            sb = new StringBuilder();
        } else {
            sb = new StringBuilder(get_headers());
            sb.append("\n");
        }
        if(assets.isEmpty()) {
            return;
        }

        try(FileWriter fw = new FileWriter(output_filename, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw)) {

            for (int i = 0; i < assets.size() - 1; i++) {
                sb.append(assets.get(i).get_csv_line());
                sb.append("\n");
            }
            sb.append(assets.get(assets.size() - 1).get_csv_line());

            out.println(sb.toString());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}