package com.scrapper.utilities;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper {
    public static void ReadCovid(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements mta_class = doc.getElementsByClass("mt_a");
        ArrayList<String> countries = new ArrayList<String>();
        ArrayList<String> allDataRead = new ArrayList<String>();
        ArrayList<String> totalCases = new ArrayList<String>();
        ArrayList<String> newCases = new ArrayList<String>();
        ArrayList<String> totalDeaths = new ArrayList<String>();
        ArrayList<String> newDeaths = new ArrayList<String>();
        ArrayList<String> totalRecovered = new ArrayList<String>();
        ArrayList<String> activeCases = new ArrayList<String>();
        ArrayList<String> critical = new ArrayList<String>();
        ArrayList<String> totalCasesPer1MPop = new ArrayList<String>();
        ArrayList<String> deathsCasesPer1MPop = new ArrayList<String>();
        ArrayList<String> totalTests = new ArrayList<String>();
        ArrayList<String> testsPer1MPop = new ArrayList<String>();
        ArrayList<String> continent = new ArrayList<String>();


        Elements htmlLines = doc.select("td");
        Boolean helperFlag = false;

        //Read proper data to one array
        for(Element el : htmlLines) {
            if(helperFlag == true)
                allDataRead.add(el.getElementsByTag("td").text());

            if(el.getElementsByTag("td").text().equals("All"))
                helperFlag = true;
        }

        // Divide data. TODO: convert numbers to integers
        for(int i = 0; i < allDataRead.size() / 2  - 160; i++) {
            countries.add(allDataRead.get(i++));
            totalCases.add(allDataRead.get(i++));
            newCases.add(allDataRead.get(i++));
            totalDeaths.add(allDataRead.get(i++));
            newDeaths.add(allDataRead.get(i++));
            totalRecovered.add(allDataRead.get(i++));
            activeCases.add(allDataRead.get(i++));
            critical.add(allDataRead.get(i++));
            totalCasesPer1MPop.add(allDataRead.get(i++));
            deathsCasesPer1MPop.add(allDataRead.get(i++));
            totalTests.add(allDataRead.get(i++));
            testsPer1MPop.add(allDataRead.get(i++));
            continent.add(allDataRead.get(i++));
            i--;
        }


        for(int i = 0; i <totalCases.size(); i++ ) {

            System.out.println(countries.get(i) + " | " + totalCases.get(i) + " | " + newCases.get(i) + " | " + totalDeaths.get(i)+ " | "
                    + newDeaths.get(i) + " | " + totalRecovered.get(i) + " | " + activeCases.get(i) + " | " + critical.get(i) + " | " +
                    totalCasesPer1MPop.get(i)+ " | " + deathsCasesPer1MPop.get(i) + " | " + totalTests.get(i) + " | " + testsPer1MPop.get(i) + " | " +
                    continent.get(i));

        }
    }
}
