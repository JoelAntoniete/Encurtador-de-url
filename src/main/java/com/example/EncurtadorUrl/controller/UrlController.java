package com.example.EncurtadorUrl.controller;

import com.example.EncurtadorUrl.model.Url;
import com.example.EncurtadorUrl.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/url")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Map<String, String>> shortenUrl(@RequestBody Map<String, String> request) {
        String originalUrl = request.get("url");
        String shortUrl = urlService.shortenUrl(originalUrl);
        Map<String, String> response = new HashMap<String, String>();
        response.put("url", "https://xxx.com/" + shortUrl);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Object> redirectToOriginalUrl(@PathVariable String shortUrl) {

        Optional<Url> urlOptional = urlService.getOriginalUrl(shortUrl);
        if (urlOptional.isPresent()) {
            Url url = urlOptional.get();
            System.out.println("Redirecionando para: " + url.getOriginalUrl());
            return ResponseEntity.status(302).location(URI.create(url.getOriginalUrl())).build();
        }

        System.out.println("URL não encontrada ou expirada: " + shortUrl);

        return ResponseEntity.notFound().build();
    }

}
