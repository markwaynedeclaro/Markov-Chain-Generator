package com.irvin.markov.controller;

import com.irvin.markov.entity.Message;
import com.irvin.markov.service.TextUtilityService;
import org.springframework.beans.factory.annotation.Autowired;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@CrossOrigin(origins = "http://localhost:3000", maxAge = 3600)
@RestController
@RequestMapping(value = "/v1/markov", produces = { "application/json", "application/xml" })
public class MarkovController {

    private static final Logger logger = LoggerFactory.getLogger(MarkovController.class);

    @Autowired
    private TextUtilityService textUtilityService;

    @PostMapping("/output")
    public Message generateMarkovChainOutput(@RequestParam("file") MultipartFile file,
                                             @RequestParam Integer prefixLength,
                                             @RequestParam Integer suffixLength,
                                             @RequestParam Integer outputSize) {

        logger.debug("Called generateMarkovChainOutput()");

        Message message = null;
        try {
            message = textUtilityService.getMarkovString(new String(file.getBytes()), prefixLength, suffixLength, outputSize);
        } catch (IOException e) {
            logger.debug(e.getLocalizedMessage());
            message = new Message(false, " File reading error occurred ");
        }
        return message;
    }

}
