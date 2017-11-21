package com.ben.smith.reader;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.*;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Created by bensmith on 11/11/17.
 * Used for getting lists of assets to be passed to a sql database later
 */
public class File_Processor_New {

    public static void main(String[] args) {

//        String filename = "./storage/new_1.txt";
//        String addition = "";
//
//        List<Asset> assets = get_assets(filename, addition);
//
//        for(Asset a : assets) {
//            a.print_all_fields();
//        }

    }

    // Abstraction of getting the list of assets, without calling a bunch of other methods
    static List<Asset> get_assets(List<String> text_lines, String addition) {
        List<String> lines = get_valuable_lines(text_lines);
        NodeList nodes = get_node_list(lines, addition);
        return get_asset_list(nodes);
    }

    // Get all of the xml holdings objects from our previously read xml lines
    // If there is another tag on the xml objects we use that as the addition
    private static NodeList get_node_list(List<String> xml_lines, String addition) {
        StringBuilder sb = new StringBuilder();

        for(String line : xml_lines) { sb.append(line); }
        String xml_string = sb.toString();

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml_string));

            Document doc = db.parse(is);

            if(!addition.equals("")) {
                addition = addition + ":";
            }
            return doc.getElementsByTagName(addition + "infoTable");

        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    // Convert the xml objects we obtain earlier into a list of Asset objects
    private static List<Asset> get_asset_list(NodeList nodes) {

        List<Asset> assets = new ArrayList<>();

        String name;
        String title;
        String cusip;
        String cash_value;
        String num_shares;
        String type;
        String discretion;

        for(int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);

            NodeList nl = node.getChildNodes();

            // System.out.println(nl.getLength());

            // num_shares and type are nested so we just bypass this by removing the messed up spacing
            String[] num_shares_and_type = nl.item(9).getTextContent()
                    .trim().replaceAll("\\s+"," ").split(" ");

            // for all unnested objects we can just use .item(index)
            name = nl.item(1).getTextContent();
            title = nl.item(3).getTextContent();
            cusip = nl.item(5).getTextContent().toUpperCase();
            cash_value = nl.item(7).getTextContent();
            num_shares = num_shares_and_type[0];
            type = num_shares_and_type[1];
            discretion = nl.item(11).getTextContent();

            // Create and add our new asset to our list of assets
            Asset a = new Asset(name, title, cusip, cash_value, num_shares, type, discretion);
            assets.add(a);
        }

        return assets;
    }

    // Get those lines that contain the xml containing a firms holdings
    private static List<String> get_valuable_lines(List<String> text_lines) {

        List<String> valuable_lines = new ArrayList<>();

        boolean seen_xml_before = false;

        for(int i = 0; i < text_lines.size(); i++) {

            String line = text_lines.get(i).replaceAll("\\s+","");
            if(line.equals("<XML>") && seen_xml_before) {
                valuable_lines = text_lines.subList(i + 1, text_lines.size() - 4);
            }
            if(line.equals("<XML>")) seen_xml_before = true;
        }

        return valuable_lines;
    }
}
