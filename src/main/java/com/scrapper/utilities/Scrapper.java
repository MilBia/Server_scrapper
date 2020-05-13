package com.scrapper.utilities;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;

import com.scrapper.country.model.Country;
import com.scrapper.infections.model.Infection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Scrapper {
    private static Integer ParseToInteger(String text){
        try
        {
            return Integer.parseInt(text.replace(",","").replace("+",""));
        }
        catch (NumberFormatException e)
        {
            return  0;
        }
    }

    public static Collection<Infection> ReadCovid(){
        Document doc = null;
        try {
            doc = Jsoup.connect("https://www.worldometers.info/coronavirus/").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Element table = doc.getElementById("main_table_countries_today");
        Elements table_data = table.select("tbody").first().getElementsByTag("tr");
        Collection<Infection> infections = new ArrayList<Infection>();
        for (Element el : table_data) {
            if(!el.className().contains("total_row_world")) {
                Elements row = el.getElementsByTag("td");
                Country country = new Country();
                country.setName(row.get(0).text());
                Infection infection = new Infection();
                infection.setCountry(country);
                infection.setCreationTime(LocalDateTime.now());
                infection.setTotalCases(Scrapper.ParseToInteger(row.get(1).text()));
                infection.setNewCases(Scrapper.ParseToInteger(row.get(2).text()));
                infection.setTotalDeaths(Scrapper.ParseToInteger(row.get(3).text()));
                infection.setNewDeaths(Scrapper.ParseToInteger(row.get(4).text()));
                infection.setTotalRecovered(Scrapper.ParseToInteger(row.get(5).text()));
                infection.setActiveCases(Scrapper.ParseToInteger(row.get(6).text()));
                infection.setSeriousCritical(Scrapper.ParseToInteger(row.get(7).text()));
                infection.setTotCases1Mpop(Scrapper.ParseToInteger(row.get(8).text()));
                infection.setDeaths1Mpop(Scrapper.ParseToInteger(row.get(9).text()));
                infection.setTotalTests(Scrapper.ParseToInteger(row.get(10).text()));
                infection.setTests1Mpop(Scrapper.ParseToInteger(row.get(11).text()));
                infections.add(infection);
            }
        }
        return infections;
    }
}
