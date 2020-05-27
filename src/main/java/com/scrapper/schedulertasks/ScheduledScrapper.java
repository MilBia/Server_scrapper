package com.scrapper.schedulertasks;

import com.scrapper.infections.InfectionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
public class ScheduledScrapper {

    private static final Logger log = LoggerFactory.getLogger(ScheduledScrapper.class);

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private InfectionService infectionService;

    @Scheduled(fixedRate = 300000)
    public void reportCurrentTime() {
        try {
            long executionTime = System.nanoTime();
            infectionService.addNewInfections();
            executionTime = System.nanoTime() - executionTime;
            log.info("New data are scrapped at {} in {}ms", dateFormat.format(new Date()), executionTime/1000000);
        }
        catch (Exception e) {
            log.error(e.toString());
        }
    }
}
