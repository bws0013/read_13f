package com.ben.smith.reader;

import javafx.beans.binding.StringBinding;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bensmith on 11/13/17.
 */
public class output {


    public static void main(String[] args) {

        String output_dir = "./output_for_csvs/";
        String output_file = output_dir + "first.csv";

        List<String> files_to_csvify = new ArrayList<>();
        files_to_csvify.add("./storage/old_1.txt");
        files_to_csvify.add("./storage/new_1.txt");

        create_csv_from_multiple_files(files_to_csvify, output_file);
    }

    public static void create_csv_from_multiple_files(List<String> filenames, String output_file_name) {
        for(String filename : filenames) {
            List<Asset> assets = read_unknown_file.pass_to_processors(filename);
            print_to_csv(output_file_name, assets);
        }
    }

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
        return line;
    }

    public static void print_to_csv(String output_filename, List<Asset> assets) {

        StringBuilder sb;

        File f = new File(output_filename);
        if(f.exists() && !f.isDirectory()) {
            sb = new StringBuilder();
        } else {
            sb = new StringBuilder(get_headers());
            sb.append("\n");
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