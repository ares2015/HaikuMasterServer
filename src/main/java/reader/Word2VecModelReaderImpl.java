package reader;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Oliver on 3/1/2017.
 */
public class Word2VecModelReaderImpl implements Word2VecModelReader {

//    private String inputFilePath = "C:\\Users\\Oliver\\Documents\\NlpTrainingData\\HaikuMasterTrainingData\\HaikuMasterSortedWord2VecData.txt";

    private ResourceLoader resourceLoader;

    public Word2VecModelReaderImpl(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @Override
    public Map<String, List<String>> read() throws IOException {
        Map<String, List<String>> word2VecModel = new HashMap<String, List<String>>();
        Resource resource = resourceLoader.getResource("classpath:HaikuMasterSortedWord2VecData.txt");
        File file = resource.getFile();
        BufferedReader br = new BufferedReader(new FileReader(file));
        String trainingDataRowAsString = br.readLine();
        while (trainingDataRowAsString != null) {
            System.out.println("Reading: " + trainingDataRowAsString);
            String[] split = trainingDataRowAsString.split("#");
            String keyToken = split[0];
            if (!word2VecModel.containsKey(keyToken)) {
                List<String> words = new ArrayList<>();
                word2VecModel.put(keyToken, words);
                populateWordsList(keyToken, word2VecModel, split);
            }
            trainingDataRowAsString = br.readLine();
        }
        return word2VecModel;
    }

    private void populateWordsList(String keyToken, Map<String, List<String>> word2VecModel, String[] split) {
        for (int i = 1; i < split.length; i++) {
            if (!"".equals(split[i]) && !" ".equals(split[i])) {
                word2VecModel.get(keyToken).add(split[i]);
            }
        }
    }
}
