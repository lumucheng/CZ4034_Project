/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz4034.Manager;

import emoji4j.EmojiUtils;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.commons.lang3.StringUtils;
import twitter4j.MediaEntity;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 *
 * @author Lu
 */
public class TwitterManager {

    public static final String WORKING_DIRECTORY = System.getProperty("user.dir") + File.separator + "data" + File.separator;
    public static final String DELIMITER = ",";
    public static final String[] MAIN_TOPICS = { "Virtual Reality", "Augmented Reality", "Hologram", "Vive", "Oculus Rift"};
    public static final String[] SUB_TOPICS1 = { "iot", "internetofthings", "c#", "java", "programming" };
    public static final String[] SUB_TOPICS2 = { "coding", "artificialintelligence", "alphago", "deeplearning", "machinelearning" }; 
    public static final String[] SUB_TOPICS3 = { "nlp", "naturallanguage", "neuralnetworks", "ios", "android" };
    public static final String[] SUB_TOPICS4 = { "iphone", "tablet", "ipad", "samsung", "htc" };
    public static final String[] SUB_TOPICS5 = { "facebook", "twitter", "socialmedia", "engadget", "bigdata" };
    public static final String[] SUB_TOPICS6 = { "datamining", "dataanalytics", "google", "microsoft", "informationtech" }; 
    public static final String[] SUB_TOPICS7 = { "informationtechnology", "webservices", "amazonwebservices", "php", "python" };
    public static final String[] SUB_TOPICS8 = { "development", "software", "hardware", "arduino", "c++" }; 
    public static final String[] SUB_TOPICS9 = { "raspberrypi", "linux", "unix" };
    
    public static final String HEADER = "id, screenname, profileURL, tweet, formattedtweet, rtcount, favcount, created, photo";

    public void searchTwitterRESTAPI() {
        //crawlMainTopics();
        crawlSubTopics();
    }

    private void crawlMainTopics() {
        BufferedWriter txtWriter = null, csvWriter = null;
        Twitter twitter = TwitterFactory.getSingleton();
        HashSet hs = new HashSet();

        for (String topic : MAIN_TOPICS) {
            Query query = new Query(topic);
            query.setLang("en");
            QueryResult result;

            try {

                csvWriter = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(topic + ".csv"), "utf-8"));
                txtWriter = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(topic + ".txt"), "utf-8"));

                csvWriter.write(HEADER);
                csvWriter.newLine();
                do {
                    result = twitter.search(query);
                    List<Status> tweets = result.getTweets();

                    for (final Status tweet : tweets) {

                        int size = hs.size();
                        hs.add(tweet.getText());

                        if (size < hs.size()) {

                            String textToWrite = tweet.getId() + DELIMITER;
                            textToWrite += tweet.getUser().getScreenName() + DELIMITER;
                            textToWrite += tweet.getUser().getProfileImageURL() + DELIMITER;
                            textToWrite += StringEscapeUtils.escapeCsv(tweet.getText()) + DELIMITER;

                            String tweetText = formatTextForIndexing(tweet.getText());
                            textToWrite += StringEscapeUtils.escapeCsv(tweetText) + DELIMITER;

                            textToWrite += tweet.getRetweetCount() + DELIMITER;
                            textToWrite += tweet.getFavoriteCount() + DELIMITER;
                            textToWrite += StringEscapeUtils.escapeCsv(tweet.getCreatedAt().toString());

                            MediaEntity[] mediaArray = tweet.getMediaEntities();
                            if (mediaArray.length > 0) {
                                textToWrite += DELIMITER + mediaArray[0].getMediaURL();
                            }

                            csvWriter.write(textToWrite);
                            csvWriter.newLine();

                            String formattedText = formatTextForSentiment(tweet.getText());
                            txtWriter.write(formattedText);
                            txtWriter.newLine();
                        }
                    }
                } while ((query = result.nextQuery()) != null);
            } catch (TwitterException | IOException ex) {
                System.out.println(ex.getMessage());
                // Do logger here
            } finally {
                try {
                    txtWriter.close();
                    csvWriter.close();
                } catch (Exception ex) {/*ignore*/ }
            }
        }
    }

    private void crawlSubTopics() {
        BufferedWriter txtWriter = null, csvWriter = null;
        Twitter twitter = TwitterFactory.getSingleton();
        HashSet hs = new HashSet();

        try {

            csvWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("tweets9" + ".csv"), "utf-8"));
            txtWriter = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream("tweets9" + ".txt"), "utf-8"));

            for (String topic : SUB_TOPICS9) {
                Query query = new Query(topic);
                query.setLang("en");
                QueryResult result;

                csvWriter.write(HEADER);
                csvWriter.newLine();

                do {
                    result = twitter.search(query);
                    List<Status> tweets = result.getTweets();

                    for (final Status tweet : tweets) {

                        int size = hs.size();
                        hs.add(tweet.getText());

                        if (size < hs.size()) {

                            String textToWrite = tweet.getId() + DELIMITER;
                            textToWrite += tweet.getUser().getScreenName() + DELIMITER;
                            textToWrite += tweet.getUser().getProfileImageURL() + DELIMITER;
                            textToWrite += StringEscapeUtils.escapeCsv(tweet.getText()) + DELIMITER;

                            String tweetText = formatTextForIndexing(tweet.getText());
                            textToWrite += StringEscapeUtils.escapeCsv(tweetText) + DELIMITER;

                            textToWrite += tweet.getRetweetCount() + DELIMITER;
                            textToWrite += tweet.getFavoriteCount() + DELIMITER;
                            textToWrite += StringEscapeUtils.escapeCsv(tweet.getCreatedAt().toString());

                            MediaEntity[] mediaArray = tweet.getMediaEntities();
                            if (mediaArray.length > 0) {
                                textToWrite += DELIMITER + mediaArray[0].getMediaURL();
                            }

                            csvWriter.write(textToWrite);
                            csvWriter.newLine();

                            String formattedText = formatTextForSentiment(tweet.getText());
                            txtWriter.write(formattedText);
                            txtWriter.newLine();
                        }
                    }
                } while ((query = result.nextQuery()) != null);
            }
        } catch (TwitterException | IOException ex) {
            System.out.println(ex.getMessage());
            // Do logger here
        } finally {
            try {
                txtWriter.close();
                csvWriter.close();
            } catch (Exception ex) {/*ignore*/ }
        }
    }

    private String formatTextForSentiment(String text) {
        text = text.replaceAll("\\S+://\\S+", "");
        text = text.replaceAll("#", "");
        text = text.replaceAll("[\n\r]", "");
        text = EmojiUtils.removeAllEmojis(text);
        return text;
    }

    private String formatTextForIndexing(String text) {
        text = formatTextForSentiment(text);
        text = removeStopWords(text);
        text = NLPManager.lemmatize(text);
        return text;
    }

    private String removeStopWords(String text) {
        String[] stopwords = {"i", "me", "my", "myself", "we", "our",
            "ours", "ourselves", "you", "your", "yours", "yourself", "yourselves",
            "he", "him", "his", "himself", "she", "her", "hers", "herself", "it",
            "its", "itself", "they", "them", "their", "theirs", "themselves", "this",
            "that", "these", "those", "am", "is", "are", "was", "were", "be", "been",
            "being", "have", "has", "had", "a", "the", "about"};

        text = text.trim().replaceAll("\\s+", " ");
        ArrayList<String> wordsList = new ArrayList<>();
        String[] words = text.split(" ");

        wordsList.addAll(Arrays.asList(words));

        //remove stop words here from the temp list
        for (int i = 0; i < wordsList.size(); i++) {

            // get the item as string
            for (String stopword : stopwords) {
                if (wordsList.size() > i && stopword.contains(wordsList.get(i))) {
                    wordsList.remove(i);
                }
            }
        }

        return StringUtils.join(wordsList);
    }
}
