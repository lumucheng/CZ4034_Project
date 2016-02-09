/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.cz4034.Manager;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;
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
    public static final String DELIMITER = "\t";

    public void searchByUser(String username, Long sinceId, Long twitterId) {
        String queryStr = "from:" + username;
        searchTwitterRESTAPI(queryStr, sinceId, twitterId);
    }
    
    public void searchByKeyword(String keyword) {
        searchTwitterRESTAPI(keyword, Long.valueOf(0), Long.valueOf(0));
    }
    
    private void searchTwitterRESTAPI(String queryStr, Long sinceId, Long twitterId) {
        
        BufferedWriter writer = null;
        Twitter twitter = TwitterFactory.getSingleton();
        Query query = new Query(queryStr);

        // Uncomment if needed
        //query.setSince("2015-01-01");
        query.setLang("en");

        QueryResult result;
        int count = 0;

        try {
            do {
                result = twitter.search(query);
                List<Status> tweets = result.getTweets();

                writer = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(queryStr + ".txt"), "utf-8"));
                
                for (final Status tweet : tweets) {
                    count++;
                    
                    String textToWrite = count + DELIMITER;
                    textToWrite += tweet.getId() + DELIMITER;
                    textToWrite += tweet.getUser().getScreenName() + DELIMITER;
                    textToWrite += tweet.getText() + DELIMITER;
                    textToWrite += tweet.getCreatedAt();
                    
                    MediaEntity[] mediaArray = tweet.getMediaEntities();
                    for (MediaEntity media : mediaArray) {
                        textToWrite += DELIMITER + media.getMediaURL();
                    }
                    
                    writer.write(textToWrite);
                    writer.newLine();
                    
                    System.out.println(textToWrite);
//                        if (NLPManager.getSentiment(tweet.getText()) > 1) {
//
//                        }
                }
            } while ((query = result.nextQuery()) != null);

            System.out.println("Retrieved " + count + " number of tweets for " + queryStr);
            System.out.println("----------------------------------");
        } 
        catch (TwitterException | IOException ex) {
            System.out.println(ex.getMessage());
            // Do logger here
        }
        
        finally {
            try { writer.close(); } catch (Exception ex) {/*ignore*/}
        }
    }
}
