package com.cz4034;

import com.cz4034.Manager.TwitterManager;


class CZ4034 {
    public static void main(String [] args) throws InterruptedException {
        
       System.out.println("Start crawling!");
       System.out.println("Keyword: Oculus Rift");
       
       TwitterManager twitterMgr = new TwitterManager();
       twitterMgr.searchByKeyword("Virtual Reality");
    }
}
