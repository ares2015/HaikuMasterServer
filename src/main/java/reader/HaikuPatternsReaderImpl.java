package reader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Oliver on 3/13/2017.
 */
public class HaikuPatternsReaderImpl implements HaikuPatternsReader {

    private String inputFilePath = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\HaikuPatterns.txt";

    @Override
    public List<String> read() throws IOException {
        List<String> haikuPatterns = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(inputFilePath));
        String trainingDataRowAsString = br.readLine();
        while (trainingDataRowAsString != null) {
            haikuPatterns.add(trainingDataRowAsString);
            trainingDataRowAsString = br.readLine();
        }
        return haikuPatterns;
    }

}