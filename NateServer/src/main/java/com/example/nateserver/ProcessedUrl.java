package com.example.nateserver;

import org.jsoup.Jsoup;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProcessedUrl {
    private Map<String, Integer> wordCountMap;
    private String url;

    public ProcessedUrl(String urlAddress){
        wordCountMap = new HashMap<String,Integer>();
        this.url = urlAddress;
        try {
            URL url = new URL(this.url);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));

            String inputLine;
            StringBuilder builder = new StringBuilder();
            // read in the entire website
            while ((inputLine = in.readLine()) != null) {
                builder.append(inputLine).append("\n");

            }
            in.close();

            // use Jsoup to get the text and remove unwanted html tags
            String justText = Jsoup.parse(builder.toString()).text();

            // remove all punctuation except apostrophes
            String[] words = justText.toLowerCase().replaceAll("[\\!\"#\\$%\\&\\'\\(\\)\\*\\+,\\-\\./\\:;\\<\\=\\>\\?@\\[\\]\\^_\\{\\|\\}~]", " ").split(" ");

            for(String word : words){
                if(word.isEmpty())
                    continue;
                int count = wordCountMap.getOrDefault(word.trim(), 0);
                wordCountMap.put(word.trim(), count+1);
            }
        }
        catch(Exception e){
            System.out.println("Error occurred while processing URL " + url + " ; Error "+e);
        }
    }

    public String getContent() {
        return this.wordCountMap.toString();
    }
    public String getUrl() {
        return url;
    }

    public Map<String, Integer> getWordCountMap() {
        return wordCountMap;
    }
}
