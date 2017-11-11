package com.ben.smith.reader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ben.smith.reader.read_unknown_file.read_file;

/**
 * Created by bensmith on 11/7/17.
 */
public class file_processors {

    public static void main(String[] args) {

        List<String> valuable_lines = read_old_1("./storage/old_1.txt");
//        process_old_1(valuable_lines);

        int guessed_offset = collect_possible_cusip_offsets(valuable_lines);
        print_cusip_at_offset(guessed_offset, valuable_lines);

//        int smallest = get_name_length(valuable_lines);
//        System.out.println(smallest);

    }

    // Get/Print the cusips at a particular offset (assume 9 char cusip)
    public static void print_cusip_at_offset(int offset, List<String> lines) {

        for(String line : lines) {

            System.out.println(line.substring(offset, offset + 9));

        }

    }





    // Analyze the frequency of 9 character sequences to determine the cusip.
    public static int collect_possible_cusip_offsets(List<String> lines) {

        Map<Integer, Integer> offset_to_count = new HashMap<>();

        for(String line : lines) {
            List<Integer> possible_offsets = regex_offset(line);
            for(int offset : possible_offsets) {
                if(offset_to_count.containsKey(offset)) {
                    int count = offset_to_count.get(offset);
                    count++;
                    offset_to_count.put(offset, count);
                } else {
                    offset_to_count.put(offset, 1);
                }
            }
        }

        List<Integer> offsets = new ArrayList<>(offset_to_count.keySet());

        if(offsets.size() == 0) {
            System.out.println("No offsets found!");
            System.exit(1);
        }

        Collections.sort(offsets);


        int offset_with_highest_count = offsets.get(0);

        for(int i = 1; i < offsets.size(); i++) {
            if(offset_to_count.get(offsets.get(i)) > offset_to_count.get(offset_with_highest_count)) {
                offset_with_highest_count = offsets.get(i);
            }
        }

        return offset_with_highest_count;
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

    public static List<Integer> regex_offset(String line) {

        List<Integer> found_offsets = new ArrayList<>();

        String pattern = "[A-Za-z0-9]{9}";
        Pattern finder = Pattern.compile(pattern);
        Matcher matcher = finder.matcher(line);


        while (matcher.find()) {
            found_offsets.add(matcher.start());
        }

        return found_offsets;
    }


}


