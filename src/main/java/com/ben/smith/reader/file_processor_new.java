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
 */
public class file_processor_new {

    public static void main(String[] args) {

        String filename = "./storage/new_1.txt";
        String addition = "";

        List<Asset> assets = get_assets(filename, addition);

        for(Asset a : assets) {
            a.print_all_fields();
        }

    }

    public static List<Asset> get_assets(String filename, String addition) {
        List<String> lines = get_valuable_lines(filename);
        NodeList nodes = get_node_list(lines, addition);
        return get_asset_list(nodes);
    }

    public static NodeList get_node_list(List<String> xml_lines, String addition) {
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
            NodeList nodeList = doc.getElementsByTagName(addition + "infoTable");
            return nodeList;

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Asset> get_asset_list(NodeList nodes) {

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

            String[] num_shares_and_type = nl.item(9).getTextContent()
                    .trim().replaceAll("\\s+"," ").split(" ");

            name = nl.item(1).getTextContent();
            title = nl.item(3).getTextContent();
            cusip = nl.item(5).getTextContent().toUpperCase();
            cash_value = nl.item(7).getTextContent();
            num_shares = num_shares_and_type[0];
            type = num_shares_and_type[1];
            discretion = nl.item(11).getTextContent();

            Asset a = new Asset(name, title, cusip, cash_value, num_shares, type, discretion);
            assets.add(a);
        }

        return assets;
    }


    public static List<String> get_valuable_lines(String filename) {

        List<String> valuable_lines = new ArrayList<>();

        List<String> text_lines = read_unknown_file.read_file(filename);

        boolean seen_xml_before = false;

        for(int i = 0; i < text_lines.size(); i++) {

            String line = text_lines.get(i).replaceAll("\\s+","");
            if(line.equals("<XML>") && seen_xml_before == true) {
                valuable_lines = text_lines.subList(i + 1, text_lines.size() - 4);
            }
            if(line.equals("<XML>")) {
                seen_xml_before = true;
            }
        }

        return valuable_lines;
    }


    public static List<String> read_new_1(String filename) {




        return null;
    }




}
