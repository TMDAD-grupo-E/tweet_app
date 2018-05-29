package es.unizar.tmdad.lab3.controller;

import es.unizar.tmdad.lab3.service.TwitterLookupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.social.twitter.api.Trends;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@RestController
public class TrendsController {

    @Autowired
    TwitterLookupService twitter;

    @RequestMapping("/trends")
    public Trends trends(@RequestParam("c") String c, Model m) {
        System.out.println("hace esto");
        System.out.println(twitter.trends(c));
        return twitter.trends(c);
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public Trends handleNullPointerException(Model m) {
        return twitter.trendsEmptyAnswer();
    }
}
