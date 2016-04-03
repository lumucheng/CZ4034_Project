package com.cz4034.Manager;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TextAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.util.CoreMap;
import java.util.ArrayList;
import org.apache.commons.lang3.StringUtils;

public class NLPManager {
    
    static StanfordCoreNLP pipeline;
    
    public static void init() {
        pipeline = new StanfordCoreNLP("nlp.properties");
    }

    public static String lemmatize(String text) {

        Annotation document = pipeline.process(text);
        ArrayList<String> wordList = new ArrayList<>();
        
        for (CoreMap sentence : document.get(SentencesAnnotation.class)) {
            for (CoreLabel token : sentence.get(TokensAnnotation.class)) {
                String word = token.get(TextAnnotation.class);
                String lemma = token.get(LemmaAnnotation.class);
                wordList.add(lemma);
            }
        }
        
        wordList.remove(0);
        wordList.remove(wordList.size() - 1);
        String formattedText = StringUtils.join(wordList);
        formattedText = StringUtils.remove(formattedText, ",");
        return formattedText;
    }
}
