package main;

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

    private static Map<String, String> map = new HashMap<>();

    private Tokenizer tokenizer;

    public HaikuMaster() {
        this.tokenizer = new TokenizerImpl();
    }

    @RequestMapping(path = "/generateHaiku", method = RequestMethod.GET, produces = {MediaType.TEXT_PLAIN_VALUE})
    String process(@RequestParam(value = "seedWord") String seedWord) {
        BufferedReader br = null;
        int lineNumber = 0;
        try {
            br = new BufferedReader(new FileReader("c:\\Users\\oled\\Documents\\test.txt"));
        } catch (final FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            String trainingDataRow = br.readLine();
            while (trainingDataRow != null) {
                lineNumber++;
                System.out.println(trainingDataRow);
                trainingDataRow = br.readLine();
            }
        } catch (final IOException e) {
            e.printStackTrace();
        } finally {
            try {
                br.close();
            } catch (final IOException e) {
                e.printStackTrace();
            }
        }
        List<String> tokens = tokenizer.getTokens("this is test");
        System.out.println(tokens.get(0));
        System.out.println("Model contains foo: " + map.get("foo"));
        return "Seed word is: " + seedWord;
    }

    public static void main(String[] args) throws Exception {
        init();
        SpringApplication.run(HaikuMaster.class, args);
    }

    private static void init() {
        map.put("foo", "bar");
    }
}
