package es.unizar.tmdad.lab3.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.social.UncategorizedApiException;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Trends;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;

import es.unizar.tmdad.lab3.service.TwitterLookupService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

@Controller
public class SearchController {

    @Autowired
    TwitterLookupService twitter;

    @RequestMapping("/")
    public String greeting() {
        return "index";
    }
    
    @MessageMapping("/search")
	public void search(String query) {
		twitter.search(query);
	}

    @RequestMapping("/trends")
    public Trends trends(@RequestParam("c") String c, Model m) {
        return twitter.trends(c);
    }


    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NullPointerException.class)
    public Trends handleNullPointerException(Model m) {
        return twitter.trendsEmptyAnswer();
    }
}