package com.example.nateclient;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Map;
import java.util.Scanner;
@SpringBootApplication
public class Client {
    public static final String uri = "http://localhost:8080";

    public boolean isValidUrl(String url){
        return (url != null && url.startsWith("https://")) ? true: false;
    }

    public Map<String,Integer> sendUrl(String url){
        try {
            RestTemplate template = new RestTemplate();
            JsonParser parser = new BasicJsonParser();
            // pass the URL to the server and parse the output
            Map<String, Object> outputMap = parser.parseMap(template.getForObject(uri + "/process?url=" + url, String.class));
            Map<String, Integer> wordCountMap = (Map<String, Integer>) outputMap.get("wordCountMap");
            //System.out.println(wordCountMap);
            wordCountMap.entrySet().forEach(x -> System.out.println(x.getKey() + ":" + x.getValue()));
            return  wordCountMap;
        }catch (ResourceAccessException e){
            System.out.println(e);
        }
        return null;
    }

    public boolean canConnect(){
        try{
            RestTemplate template = new RestTemplate();
            template.getForObject(uri+ "/process", String.class);
        }catch (Exception e){
            return false;
        }
        return true;
    }
    @Generated // custom annotation to exclude from jacoco coverage report
    public void readFromCommandLine(){
        String line = "";
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter urls (beginning with https://) to be processed. Type exit when done.");
        while (!line.equalsIgnoreCase("exit"))
        {
            line = scanner.next();
            if(!isValidUrl(line.trim())){
                System.out.println("Please enter a valid url that starts with https://");
            }else {
                this.sendUrl(line.trim());
            }
        }
        scanner.close();
    }

    //Additional functionality for processing a list of urls in a file
    public void readFromFile(String fileName){
        try{
            Scanner scanner = new Scanner(new File(fileName));
            while(scanner.hasNext()){
                String line  = scanner.nextLine();
                if(!isValidUrl(line.trim()))
                    continue;
                this.sendUrl(line.trim());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    @Generated
    public static void main(String[] args){
        Client c = new Client();
        // assume first arg is the file name (including path)
        if(args.length > 0){
            c.readFromFile(args[0]);
        }else {
            c.readFromCommandLine();
        }
    }
}
