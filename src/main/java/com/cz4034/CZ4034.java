package com.cz4034;

import com.cz4034.Manager.TweetManager;
import com.cz4034.Manager.TwitterCriteria;
import com.cz4034.model.Tweet;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;

class CZ4034 {

    public static final String WORKING_DIRECTORY = System.getProperty("user.dir") + File.separator + "data" + File.separator;
    public static final String DELIMITER = "\t";
    
    public static void main(String[] args) throws InterruptedException {

        //Virtual Reality, Augmented Reality, Hologram, Vive, Oculus Rift

        String query = "Vive";
        BufferedWriter writer = null;

        try {
            writer = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(query + ".txt"), "utf-8"));

            System.out.println("Start crawling!");
            System.out.println("Keyword: " + query);

            TwitterCriteria criteria = TwitterCriteria.create()
                    .setQuerySearch(query)
                    .setMaxTweets(10000);

            List<Tweet> tweetList = TweetManager.getTweets(criteria);

            for (Tweet tweet : tweetList) {
                
//            String textToFind = "pic.twitter";
//            String text = tweet.getText();
//            
//            if (StringUtils.containsIgnoreCase(text, textToFind)) {
//                int index = StringUtils.indexOfIgnoreCase(text, textToFind);
//                System.out.println(StringUtils.substring(text, index));
//            }
                
                String textToWrite = tweet.getId() + DELIMITER;
                textToWrite += tweet.getUsername() + DELIMITER;
                textToWrite += tweet.getText() + DELIMITER;
                
                if (!tweet.getHashtags().isEmpty()) {
                    textToWrite += tweet.getHashtags() + DELIMITER;
                }
                else {
                    textToWrite += "NULLTAG" + DELIMITER;
                }
                
                textToWrite += tweet.getDate() + DELIMITER;
                
                if (!tweet.getGeo().isEmpty()) {
                    textToWrite += tweet.getGeo();
                }
                else {
                    textToWrite += "NULLGEO";
                }
                
                writer.write(textToWrite);
                writer.newLine();
            }

            System.out.println("Retrieved : " + tweetList.size());
        } 
        catch (IOException ex) {
            System.out.println(ex.getMessage());
        } 
        finally {
            try {
                writer.close();
            } catch (Exception ex) {

            }
        }
    }
}
