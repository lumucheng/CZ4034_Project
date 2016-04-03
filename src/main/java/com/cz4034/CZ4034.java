package com.cz4034;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;
import com.cz4034.Manager.NLPManager;
import com.cz4034.Manager.TwitterManager;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;

class CZ4034 {

    public static final String WORKING_DIRECTORY = System.getProperty("user.dir") + File.separator + "data" + File.separator;
    public static final String DELIMITER = "\t";
    
    public static void main(String[] args) throws InterruptedException {

            //Virtual Reality, Augmented Reality, Hologram, Vive, Oculus Rift
            
//        String query = "Vive";
//        BufferedWriter writer = null;
//
//        try {
//            writer = new BufferedWriter(new OutputStreamWriter(
//                    new FileOutputStream(query + ".txt"), "utf-8"));
//
//            System.out.println("Start crawling!");
//            System.out.println("Keyword: " + query);
//
//            TwitterCriteria criteria = TwitterCriteria.create()
//                    .setQuerySearch(query)
//                    .setMaxTweets(10000);
//
//            List<Tweet> tweetList = TweetManager.getTweets(criteria);
//
//            for (Tweet tweet : tweetList) {
//                
////            String textToFind = "pic.twitter";
////            String text = tweet.getText();
////            
////            if (StringUtils.containsIgnoreCase(text, textToFind)) {
////                int index = StringUtils.indexOfIgnoreCase(text, textToFind);
////                System.out.println(StringUtils.substring(text, index));
////            }
//                
//                String textToWrite = tweet.getId() + DELIMITER;
//                textToWrite += tweet.getUsername() + DELIMITER;
//                textToWrite += tweet.getText() + DELIMITER;
//                
//                if (!tweet.getHashtags().isEmpty()) {
//                    textToWrite += tweet.getHashtags() + DELIMITER;
//                }
//                else {
//                    textToWrite += "NULLTAG" + DELIMITER;
//                }
//                
//                textToWrite += tweet.getDate() + DELIMITER;
//                
//                if (!tweet.getGeo().isEmpty()) {
//                    textToWrite += tweet.getGeo();
//                }
//                else {
//                    textToWrite += "NULLGEO";
//                }
//                
//                writer.write(textToWrite);
//                writer.newLine();
//            }
//
//            System.out.println("Retrieved : " + tweetList.size());
//        } 
//        catch (IOException ex) {
//            System.out.println(ex.getMessage());
//        } 
//        finally {
//            try {
//                writer.close();
//            } catch (Exception ex) {
//
//            }
//        }
//        try{    
//            convertTSVToCSV();
//        } catch (UnsupportedEncodingException ex) {
//            Logger.getLogger(CZ4034.class.getName()).log(Level.SEVERE, null, ex);
//        } catch (IOException ex) {
//            Logger.getLogger(CZ4034.class.getName()).log(Level.SEVERE, null, ex);
//        }
        
        NLPManager.init();
        TwitterManager mgr = new TwitterManager();
        mgr.searchTwitterRESTAPI();
    }
    
    public static void convertTSVToCSV() throws FileNotFoundException, UnsupportedEncodingException, IOException {

        FileInputStream fis = new FileInputStream("Oculus Rift.txt");
        InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
        CSVReader reader = new CSVReader(isr, '\t');

        FileOutputStream fos = new FileOutputStream("Oculus.csv"); 
        OutputStreamWriter osw = new OutputStreamWriter(fos, "UTF-8");
        CSVWriter writer = new CSVWriter(osw);

        for (String[] row; (row = reader.readNext()) != null;) {
            writer.writeNext(row);
        }
        
        reader.close();
        isr.close();
        fis.close();
        
        writer.close();
        osw.close();
        fos.close();
    }
}
