package com.ben.smith.reader;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by bensmith on 11/14/17.
 */
public class web_scraper {

    // This web scraper isnt attached to anything right now. I am working on it.

    // We are accounting for 13f-hr/a inside of read_unknown_file

    public static void main(String[] args) {
        // Example cik numbers
        // 1633716
        // 0001332905
        // 1008877 1541353 1608057

        String cik = "0001607863";

        createFinDocs(cik);

    }

    public static List<String> get_file_contents(String string_url) {
        List<String> lines = new ArrayList<>();
        if(global_constants.wait_time < 500) {
            return lines;
        }

        try {
            URL oracle = new URL(string_url);
            BufferedReader in = new BufferedReader(new InputStreamReader(oracle.openStream()));

            String inputLine;
            while ((inputLine = in.readLine()) != null)
                lines.add(inputLine);
            in.close();

            Thread.sleep(global_constants.wait_time);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static List<String> createFinDocs(String cik) {
        String[] arr = getAllPageLinks(cik);
        List<String> urls_to_return = new ArrayList<>();

        List<String> file_content = new ArrayList<>();
        for (int i = 0; i < arr.length; i++) {
            urls_to_return.add(generateUrl(cik, arr[i]));


        }

        return urls_to_return;
    }

    /*
        Creates a url to look at for retieving web pages.
        Intended format:
        https://www.sec.gov/Archives/edgar/data/1332905/000117266117000402/0001172661-17-000402.txt
    */
    public static String generateUrl(String cik, String accountNumber) {
        String fixed = fixAccountNumber(accountNumber);

        StringBuilder sb = new StringBuilder();
        sb.append("https://www.sec.gov/Archives/edgar/data/");
        sb.append(cik);
        sb.append("/");
        sb.append(fixed);
        sb.append("/");
        sb.append(accountNumber);
        sb.append(".txt");

        return sb.toString();
    }

    // Gets a list of document ids for company 13f forms for a specific cik
    public static String[] getAllPageLinks(String cik) {

        Document doc;
        try {

            // String url = "https://www.sec.gov/cgi-bin/browse-edgar?action=getcompany&CIK=0001332905&type=13f&dateb=&owner=include&count=40";

            // Start of all web pages
            String domain = "https://www.sec.gov";

            // Creates an address using the cik

            String pageAddress = "/cgi-bin/browse-edgar?action=getcompany&" +
                    "CIK=" + cik + "&type=13f&dateb=&owner=include&count=" + global_constants.numDocs;
            String url = domain + pageAddress;


            // Get the specific doc
            doc = Jsoup.connect(url).get();

            // All id numbers are stored in <tr> tage
            Elements rows = doc.getElementsByTag("tr");

            // The first 3 tags contain useless info
            String[] accountNumbers = new String[rows.size() - 3];

            // Tags 3->N contain id numbers that we need to return.
            for(int i = 3; i < rows.size(); i++) {
                String brokenRow = rows.get(i).text();

                String accNumA = getAccountNumber(brokenRow);
                accountNumbers[i - 3] = accNumA;
            }
            accountNumbers = checkArr(accountNumbers);

            return accountNumbers;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
        Returns the first 20 characters as this is the max length of an account number
        including dashes (-).
        Format: 0123456789-01-012345
        0001332905-11-000013
    */
    public static String getAccountNumber(String input) {
        // Returns # if no account number is found.
        String accountNumber = "#";
        if(input.startsWith("13F-HR/A")) {
            return accountNumber;
        }
        String[] elements = input.split(" ");
        for(int i = 0; i < elements.length - 1; i++) {
            if(elements[i].equals("Acc-no:")) {
                accountNumber = elements[i + 1];
            }
        }
        try {
            return accountNumber.substring(0, 20);
        } catch(StringIndexOutOfBoundsException e) {
            return accountNumber;
        }
    }

    // We need to remove the dashes in the account num to get the url.
    // 0123456789-01-012345 -> 012345678901012345
    public static String fixAccountNumber(String input) {
        if(input.length() == 1) {
            return input;
        }
        String[] elements = input.split("-");
        return elements[0] + elements[1] + elements[2];
    }

    // Verifies that only correct id numbers will be processed.
    public static String[] checkArr(String[] input) {

        int totalSize = 0;
        List<String> list = new ArrayList<String>();

        // Incorrect info starts with a #
        for(int i = 0; i < input.length; i++) {
            if(input[i].charAt(0) == '#') {

            } else {
                totalSize++;
                list.add(input[i]);
            }
        }

        // Return an array of correct elements.
        String[] ret = new String[totalSize];
        list.toArray(ret);

        return ret;
    }


}
