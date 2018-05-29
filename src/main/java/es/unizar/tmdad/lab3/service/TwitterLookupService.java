package es.unizar.tmdad.lab3.service;

import com.googlecode.concurrentlinkedhashmap.ConcurrentLinkedHashMap;
import es.unizar.tmdad.lab3.utils.CountryAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.social.twitter.api.*;
import org.springframework.social.twitter.api.impl.TwitterTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentMap;

@Service
public class TwitterLookupService {

	@Value("${twitter.consumerKey}")
	private String consumerKey;

	@Value("${twitter.consumerSecret}")
	private String consumerSecret;

	@Value("${twitter.accessToken}")
	private String accessToken;

	@Value("${twitter.accessTokenSecret}")
	private String accessTokenSecret;

	/*private String consumerKey = System.getenv("consumerKey");

	private String consumerSecret = System.getenv("consumerSecret");

	private String accessToken = System.getenv("accessToken");

	private String accessTokenSecret = System.getenv("accessTokenSecret");*/

	CountryAdapter adapter = new CountryAdapter();

	private final ConcurrentMap<String, String> connections = new ConcurrentLinkedHashMap.Builder<String, String>()
			.maximumWeightedCapacity(10).build();

	public void search(String query) {
		connections.putIfAbsent(query, query);
	}

	public Set<String> getQueries() {
		return connections.keySet();
	}

	public Trends trends(String woeid) {
		Twitter twitter = new TwitterTemplate(consumerKey, consumerSecret, accessToken, accessTokenSecret);
		System.out.println("entro en trends");
		//System.out.println(twitter.searchOperations().getLocalTrends(adapter.getWOEId(woeid)));
		return twitter.searchOperations().getLocalTrends(adapter.getWOEId(woeid));
	}


	public Trends trendsEmptyAnswer() {
		return new Trends(null, null);
	}

}

