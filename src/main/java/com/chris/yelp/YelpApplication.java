package com.chris.yelp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.net.URI;

@SpringBootApplication
public class YelpApplication {

	private final RestTemplate template = new RestTemplate();

	public static void main(String[] args) {
		SpringApplication.run(YelpApplication.class, args);
	}

	@RestController
	class YelpController {

		@GetMapping("/reviews")
		public String getReviews(@RequestParam(value = "apiKey", defaultValue = "aeArz1SkDPAwua80gtmivm261v6X8edccKdH9u1aQDsvCRo3IS-VTfe5Kn4cwsDVy0DMQVPfjUIUJjNtEbJalhUfx1_FwAT9wF3wo6qchKHkhk4w7rdkLQYXtEO4YHYx") String apiKey){

			//Initialize authorization header
			HttpHeaders headers = new HttpHeaders();
			headers.set("Authorization", "Bearer " + apiKey);

			HttpEntity entity = new HttpEntity(headers);

			//Create business search URI
			URI uri = UriComponentsBuilder.fromUriString("https://api.yelp.com")
					.path("/v3/businesses/search")
					.queryParam("location", "Las Vegas")
					.queryParam("term", "Lindo Michoacan")
					.build()
					.encode()
					.toUri();

			//Search Yelp for Lindo Michoacan
			BusinessSearchResponse yelpResponse = template.exchange(uri, HttpMethod.GET, entity, BusinessSearchResponse.class).getBody();

			//Find the Lindo Michaocan I want, default to the first business if not found
			Business targetBusiness = yelpResponse.businesses.get(0);
			for(Business b : yelpResponse.businesses){
				if(b.location.address1.equals("645 Carnegie St")){
					targetBusiness = b;
				}
			}

			//Retrieve reviews from Yelp
			String reviewResponse = null;
			if(targetBusiness != null){
				uri = UriComponentsBuilder.fromUriString("https://api.yelp.com")
						.path("/v3/businesses/" + targetBusiness.id + "/reviews")
						.build()
						.encode()
						.toUri();

				reviewResponse = template.exchange(uri, HttpMethod.GET, entity, String.class).getBody();
			}

			//Return reviews
			return reviewResponse;
		}
	}
}
