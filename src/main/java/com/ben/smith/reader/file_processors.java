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

}
