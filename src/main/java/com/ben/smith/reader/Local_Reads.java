package com.ben.smith.reader;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Created by bensmith on 5/19/18.
 */
public class Local_Reads {

    // Given a cik folder with 13f's to read, get the path of all of them
    static List<String> extract_files_from_folder(String folder_path) {
        File aDirectory = new File(folder_path);
        String[] filesInDir = aDirectory.list();
        List<String> files = new ArrayList<String>(Arrays.asList(filesInDir));
        return files;
    }

    // Read a file into a string list
    static List<String> read_file(String file_name) {
        List<String> text_lines = new ArrayList<String>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file_name));
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

}
