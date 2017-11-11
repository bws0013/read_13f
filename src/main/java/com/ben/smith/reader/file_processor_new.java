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

        List<String> lines = get_valuable_lines(filename);

        build_xml_document(lines);


    }

    public static void build_xml_document(List<String> xml_lines) {
        StringBuilder sb = new StringBuilder();

        for(String line : xml_lines) { sb.append(line); }
        String xml_string = sb.toString();

        try {
            DocumentBuilder db = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xml_string));

            Document doc = db.parse(is);

            NodeList nodeList = doc.getElementsByTagName("infoTable");

            System.out.println(nodeList.getLength());

            Node n = nodeList.item(0);
            NamedNodeMap map = n.getAttributes();

            for(int i = 0; i < map.getLength(); i++) {
                System.out.println(map.item(i));
            }

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        }

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
