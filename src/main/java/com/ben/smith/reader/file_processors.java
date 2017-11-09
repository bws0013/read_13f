package com.ben.smith.reader;

import java.util.ArrayList;
import java.util.List;

import static com.ben.smith.reader.read_unknown_file.read_file;

/**
 * Created by bensmith on 11/7/17.
 */
public class file_processors {

    public static void main(String[] args) {

        List<String> valuable_lines = read_old_1("./storage/old_1.txt");
//        process_old_1(valuable_lines);

        int smallest = get_name_length(valuable_lines);
        System.out.println(smallest);

    }

    public static List<String> read_old_1(String filename) {

        List<String> text_lines = read_file(filename);

        List<String> lines_we_care_about = new ArrayList<>();

        for(int i = 0; i < text_lines.size(); i++) {
            if(text_lines.get(i).startsWith("<S>")) {
                for(int j = i + 1; j < text_lines.size(); j++) {
                    if(text_lines.get(j).startsWith("Report Summary")) {
                        break;
                    }
                    lines_we_care_about.add(text_lines.get(j));
                }
                break;
            }
        }
        return lines_we_care_about;
    }

    public static void process_old_1(List<String> security_content) {

        for(String security : security_content) {

            String[] arr = security.split("\t");

            for(String a : arr) {
                System.out.print(a + ",");
            }
            System.out.println();
        }

    }

    public static int get_name_length(List<String> lines) {
        List<String> elements = new ArrayList<>();

        List<Integer> possible_lengths = new ArrayList<>();

        for(String line : lines) {
            int letter_count = 0;
            int space_count = 0;
            for(int i = 0; i < line.length(); i++) {

                if (line.charAt(i) == ' ' && line.charAt(i - 1) != ' ') {
                    letter_count++;
                } else if (line.charAt(i) == ' ') {
                    letter_count++;
                    space_count++;
                } else if (line.charAt(i) != ' ' && space_count > 1) {
                    possible_lengths.add(letter_count);
                    break;
                } else {
                    letter_count++;
                }
            }
        }

        int smallest = possible_lengths.get(0);
        for(int i : possible_lengths) {
            if(i < smallest) {
                i = smallest;
            }
        }
        return smallest;
    }
}


