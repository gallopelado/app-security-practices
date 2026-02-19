package com.juan.app_security.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/cards")
public class CardsController {

    @GetMapping
    public Map<String, String> cards() {
        return Collections.singletonMap("msj", "cards");
    }

}
