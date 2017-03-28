package main;

import composer.HaikuComposer;
import composer.HaikuComposerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * Created by oled on 2/28/2017.
 */
@RestController
@EnableAutoConfiguration
public class HaikuMaster {

    private HaikuComposer haikuComposer;

    private ResourceLoader resourceLoader;

    @Autowired
    public HaikuMaster(ResourceLoader resourceLoader) throws IOException {
        this.resourceLoader = resourceLoader;
        this.haikuComposer = new HaikuComposerImpl(resourceLoader);
    }

    @RequestMapping(path = "/generateHaiku", method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    String process(@RequestParam(value = "seedWord") String seedWord) {
        return haikuComposer.compose(seedWord);
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(HaikuMaster.class, args);
    }


}
