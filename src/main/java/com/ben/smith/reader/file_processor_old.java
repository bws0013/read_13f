package com.ben.smith.reader;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by bensmith on 11/7/17.
 * Used for getting lists of assets to be passed to a sql database later
 */
class file_processor_old {

    // Takes in a filename and returns the assets from that file
    static List<Asset> get_assets(String filename) {
        file_processor_old f = new file_processor_old();

        List<String> valuable_lines = f.read_old_1(filename);
        int guessed_offset = f.collect_possible_cusip_offsets(valuable_lines);
        return f.create_assets(guessed_offset, valuable_lines);
    }

    // Get a list of Assets based on the information lines and the offset of the cusip
    private List<Asset> create_assets(int offset, List<String> lines) {

        List<Asset> assets = new ArrayList<>();

        for(String line : lines) {

            String name = "";
            String title;
            String cusip = line.substring(offset, offset + 9);
            String cash_value;
            String num_shares;
            String type;
            String discretion;

            List<String> elements_we_care_about = new ArrayList<>();
            String[] line_elements = line.split(" ");
            for (String element : line_elements) {
                if (element.length() != 0) {
                    elements_we_care_about.add(element);
                }
            }
            int cusip_index = -1;

            for (int i = 0; i < elements_we_care_about.size(); i++) {
                String element = elements_we_care_about.get(i);
                if (element.equals(cusip)) {
                    cusip_index = i;
                }
            }

            List<String> name_list = elements_we_care_about.subList(0, cusip_index - 1);
            for (String name_element : name_list) {
                name += name_element + " ";
            }

//            name = name;
            title = elements_we_care_about.get(cusip_index - 1);
            cusip = cusip.toUpperCase();
            cash_value = elements_we_care_about.get(cusip_index + 1);
            num_shares = elements_we_care_about.get(cusip_index + 2);
            type = elements_we_care_about.get(cusip_index + 3);
            discretion = elements_we_care_about.get(cusip_index + 4);

            Asset a = new Asset(name, title, cusip, cash_value, num_shares, type, discretion);
            assets.add(a);
        }

        return assets;
    }

    // Analyze the frequency of 9 character sequences to determine the cusip.
    private int collect_possible_cusip_offsets(List<String> lines) {

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

        if (offset_to_count.get(offset_with_highest_count) != lines.size()) {
            System.out.println("Fewer cusips than cusip lines. Check the data!");
            System.exit(1);
        }

        return offset_with_highest_count;
    }


    private List<String> read_old_1(String filename) {

        List<String> text_lines = read_unknown_file.read_file(filename);

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

    private List<Integer> regex_offset(String line) {

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


