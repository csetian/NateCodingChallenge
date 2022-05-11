package com.example.nateserver;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.BasicJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class ProcessedUrlControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void givenNoParam_ProcessedUrlShouldReturnNoContent() throws Exception {
        this.mockMvc.perform(get("/process"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.wordCountMap").value(""));
    }

    @Test
    public void bigTextParam_ProcessedUrlShouldReturnWordCountMap() throws Exception {
        MvcResult result = this.mockMvc.perform(get("/process").param("url", "https://norvig.com/big.txt"))
                //.andDo(print())
                .andExpect(status().isOk()).andReturn();
        JsonParser parser = new BasicJsonParser();
        Map<String, Object> m =  parser.parseMap(result.getResponse().getContentAsString());
        Map<String, Number> wordCountMap = (Map<String, Number>) m.getOrDefault("wordCountMap", new HashMap<String,Number>());
        Assert.isTrue(!wordCountMap.containsKey("abcdefghijklmnopqrstuvwxyz"), "the string should not contain \"abcdefghijklmnopqrstuvwxyz\" ");
        Assert.isTrue(wordCountMap.containsKey("the"), "WordCountMap should have a key for \"the\"");
        Number i =  wordCountMap.get("the");
        Assert.isTrue(i.intValue() == 80030, "the count for \"the\" should be 80030");

        Assert.isTrue(wordCountMap.containsKey("isaiah"), "WordCountMap should have a key for \"isaiah\"");
        i =  wordCountMap.get("isaiah");
        Assert.isTrue(i.intValue() == 1, "the count for \"isaiah\" should be 1");

    }
}
