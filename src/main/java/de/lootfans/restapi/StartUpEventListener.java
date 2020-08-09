package de.lootfans.restapi;

import de.lootfans.restapi.exception.ControllerAdvisor;
import de.lootfans.restapi.service.ArchiveService;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class StartUpEventListener {

    private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(StartUpEventListener.class);

    @Autowired
    ArchiveService archiveService;

    @EventListener(ApplicationReadyEvent.class)
    public void doSetUpArchive() {
        LOGGER.info("Executing Task: Set up Archive");
        archiveService.setUp();
        LOGGER.info("Set up Archive - finished");
    }
}
