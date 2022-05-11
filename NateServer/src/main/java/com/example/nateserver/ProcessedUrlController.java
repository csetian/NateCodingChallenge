package com.example.nateserver;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProcessedUrlController {
    @GetMapping("/process")
    public ProcessedUrl greeting(@RequestParam(value = "url", defaultValue = "") String urlAddress) {
        return new ProcessedUrl(urlAddress);
    }

}
