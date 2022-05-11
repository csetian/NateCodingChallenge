package com.example.nateclient;
import org.junit.Before;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class ClientTests {

    @Autowired
    private Client client;
    private List<String> badUrls;
    private List<String> goodUrls;

    @Before
    public void init() {
        client = new Client();
    }
    @Test
    public void givenBadUrls_ClientShouldMarkAllInvalid() throws Exception {
        badUrls = new ArrayList<>();
        badUrls.add("htps://bbc.com");
        badUrls.add("bbc.com");
        badUrls.add("https:/bbc.com");
        for(String url : badUrls){
            Assert.isTrue(!client.isValidUrl(url), url + " should not be deemed valid");
        }
    }

    @Test
    public void givenGoodUrls_ClientShouldMarkAllValidAndGetWordCountMapsIfConnected() throws Exception {

        goodUrls = new ArrayList<>();
        goodUrls.add("https://www.bbc.com");
        goodUrls.add("https://norvig.com/big.txt");
        goodUrls.add("https://www.google.com");
        for(String url : goodUrls){
            Assert.isTrue(client.isValidUrl(url), url + " should be deemed valid");
            if(!client.canConnect()){
                continue;
            }
            Map<String, Integer> map =  client.sendUrl(url);
            Assert.notNull(map, "WordCountMap should not be null");
            Assert.notEmpty(map, "WordCountMap should not be empty");

        }
    }


}
