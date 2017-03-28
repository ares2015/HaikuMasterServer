package reader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 3/13/2017.
 */
public class HaikuPatternsReaderImpl implements HaikuPatternsReader {

//    private String inputFilePath = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\HaikuPatterns.txt";

    private ResourceLoader resourceLoader;

    public HaikuPatternsReaderImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public List<String> read() throws IOException {
        List<String> haikuPatterns = new ArrayList<>();
        Resource resource = resourceLoader.getResource("classpath:HaikuPatterns.txt");
        File file = resource.getFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String trainingDataRowAsString = br.readLine();
        while (trainingDataRowAsString != null) {
            haikuPatterns.add(trainingDataRowAsString);
            trainingDataRowAsString = br.readLine();
        }
        return haikuPatterns;
    }

}