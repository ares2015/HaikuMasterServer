package composer;


import data.Word2VecTokenTagData;
import factory.Word2VecTokenTagDataFactory;
import factory.Word2VecTokenTagDataFactoryImpl;
import reader.*;

import java.io.IOException;
import java.util.*;

/**
 * Created by Oliver on 2/24/2017.
 */
public class HaikuComposerImpl implements HaikuComposer {

    private TokenTagDataModelReader tokenTagDataModelReader;

    private Word2VecModelReader word2VecModelReader;

    private HaikuPatternsReader haikuPatternsReader;

    private Word2VecTokenTagDataFactory word2VecTokenTagDataFactory;

    private HaikuSentenceCreator haikuSentenceCreator;

    private Map<String, Set<String>> tokenTagDataModel;

    private Map<String, List<String>> word2VecModel;

    private List<String> haikuPatterns;

    private Random randomGenerator;

    public HaikuComposerImpl() throws IOException {
        this.tokenTagDataModelReader = new TokenTagDataModelReaderImpl();
        this.word2VecModelReader = new Word2VecModelReaderImpl();
        this.haikuPatternsReader = new HaikuPatternsReaderImpl();
        this.word2VecTokenTagDataFactory = new Word2VecTokenTagDataFactoryImpl();
        this.haikuSentenceCreator = new HaikuSentenceCreatorImpl();
        tokenTagDataModel = tokenTagDataModelReader.read();
        word2VecModel = word2VecModelReader.read();
        haikuPatterns = haikuPatternsReader.read();
        randomGenerator = new Random();
    }

    @Override
    public String compose(String seedWord) {

        String haikuPattern = haikuPatterns.get(randomGenerator.nextInt(haikuPatterns.size()));
        String[] haikuPatternSentences = haikuPattern.split("@");

        StringBuilder stringBuilder = new StringBuilder();
        String[] seedWords = seedWord.split("@");
        List<String> nounsList = new ArrayList<>();
        for (String word : seedWords) {
            if (tokenTagDataModel.containsKey(word) && tokenTagDataModel.get(word).contains("N")) {
                nounsList.add(word);
            }
        }

        int haikuIndex = 0;
        for (String haikuPatternSentence : haikuPatternSentences) {
            if (haikuIndex == 0 || haikuIndex == 2) {
                seedWord = nounsList.get(0);
            } else if (haikuIndex == 1 && nounsList.size() > 1) {
                int index = randomGenerator.nextInt(nounsList.size());
                seedWord = nounsList.get(index);
            }
            List<String> word2VecDataForToken = word2VecModel.get(seedWord);
            Word2VecTokenTagData word2VecTokenTagData = word2VecTokenTagDataFactory.create(seedWord, word2VecDataForToken, tokenTagDataModel);
            if (!"".equals(haikuPatternSentence)) {
                String haikuSentence = haikuSentenceCreator.create(haikuPatternSentence, word2VecTokenTagData, haikuIndex == 0);
                stringBuilder.append(haikuSentence);
                stringBuilder.append(System.getProperty("line.separator"));

//                stringBuilder.append("\n");
                haikuIndex++;
            }
        }
        if (stringBuilder.length() > 0) {
            return stringBuilder.toString();
        } else {
            return "no haiku could be created";
        }

    }


}
