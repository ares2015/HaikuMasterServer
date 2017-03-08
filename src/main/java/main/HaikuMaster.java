package main;

import composer.HaikuComposer;
import composer.HaikuComposerImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tokenizer.Tokenizer;
import tokenizer.TokenizerImpl;


import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by oled on 2/28/2017.
 */
@RestController
@EnableAutoConfiguration
public class HaikuMaster {

    private HaikuComposer haikuComposer;

    public HaikuMaster() throws IOException {
        this.haikuComposer = new HaikuComposerImpl();
    }

    @RequestMapping(path = "/generateHaiku", method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    String process(@RequestParam(value = "seedWord") String seedWord) {
        return haikuComposer.compose(seedWord);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HaikuMaster.class, args);
    }


}
