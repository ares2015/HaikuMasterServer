package composer;


import data.Word2VecTokenTagData;
import factory.Word2VecTokenTagDataFactory;
import factory.Word2VecTokenTagDataFactoryImpl;
import reader.TokenTagDataModelReader;
import reader.TokenTagDataModelReaderImpl;
import reader.Word2VecModelReader;
import reader.Word2VecModelReaderImpl;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Oliver on 2/24/2017.
 */
public class HaikuComposerImpl implements HaikuComposer {

    private TokenTagDataModelReader tokenTagDataModelReader;

    private Word2VecModelReader word2VecModelReader;

    private Word2VecTokenTagDataFactory word2VecTokenTagDataFactory;

    private HaikuSentenceCreator haikuSentenceCreator;

    private Map<String, Set<String>> tokenTagDataModel;

    private Map<String, List<String>> word2VecModel;

    private List<String> haikuPatterns;

    public HaikuComposerImpl() throws IOException {
        this.tokenTagDataModelReader = new TokenTagDataModelReaderImpl();
        this.word2VecModelReader = new Word2VecModelReaderImpl();
        this.word2VecTokenTagDataFactory = new Word2VecTokenTagDataFactoryImpl();
        this.haikuSentenceCreator = new HaikuSentenceCreatorImpl();
        tokenTagDataModel = tokenTagDataModelReader.read();
        word2VecModel = word2VecModelReader.read();
    }

    @Override
    public String compose(String seedWord) {
        StringBuilder stringBuilder = new StringBuilder();
        List<String> word2VecDataForToken = word2VecModel.get(seedWord);
        String[] seedWords = seedWord.split("#");
        for (String word : seedWords) {
            if (tokenTagDataModel.containsKey(word) && tokenTagDataModel.get(word).contains("N")) {
                seedWord = word;
                break;
            }
        }
        if (word2VecDataForToken != null) {
            Word2VecTokenTagData word2VecTokenTagData = word2VecTokenTagDataFactory.create(seedWord, word2VecDataForToken, tokenTagDataModel);
//        String haikuPattern = haikuPatterns.get(0);
            String haikuPattern = "@N V @the V N ; on the N @ AJ N V  . @";
            String[] haikuPatternSentences = haikuPattern.split("@");
            int haikuIndex = 0;
            for (String haikuPatternSentence : haikuPatternSentences) {
                if (!"".equals(haikuPatternSentence)) {
                    boolean isFirstSentence = haikuIndex == 0;
                    String haikuSentence = haikuSentenceCreator.create(haikuPatternSentence, word2VecTokenTagData, isFirstSentence);
                    stringBuilder.append(haikuSentence);
                    stringBuilder.append(System.getProperty("line.separator"));

//                stringBuilder.append("\n");
                    haikuIndex++;
                }
            }
            return stringBuilder.toString();
        }
        return "no haiku could be created";
    }


}
