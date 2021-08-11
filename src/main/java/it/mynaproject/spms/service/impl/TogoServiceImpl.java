package it.mynaproject.spms.service.impl;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.support.HttpRequestWrapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import it.mynaproject.spms.exception.ForbiddenException;
import it.mynaproject.spms.exception.RestTemplateResponseErrorHandler;
import it.mynaproject.spms.model.TogoMeasureJson;
import it.mynaproject.spms.model.TogoMeasuresJson;
import it.mynaproject.spms.service.TogoService;
import it.mynaproject.spms.util.PropertiesFileReader;

@Service
public class TogoServiceImpl implements TogoService {

	final private static Logger log = LoggerFactory.getLogger(TogoService.class);

	private RestTemplate restTemplate;

	private static String cookie = "";

	private static String token = "";

	final private static Properties prop = PropertiesFileReader.loadPropertiesFile();

	@Autowired
    public TogoServiceImpl() {

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new PlusEncoderInterceptor());

        restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

	@Override
	public void getToken() {

		log.info("New request for TOGO-API token");

		ResponseEntity<Void> response = restTemplate.exchange(prop.getProperty("togoapiurl") + "token", HttpMethod.GET, new HttpEntity<String>(setHttpHeaders()), Void.class);
		if (response.getStatusCode().equals(HttpStatus.OK)) {
			String cookieHeader = response.getHeaders().getFirst(HttpHeaders.SET_COOKIE);
			if (cookieHeader != null) {
				String[] parts = cookieHeader.split(";");
				cookie = parts[0];
			}
			token = response.getHeaders().getFirst("X-CSRF-TOKEN");
		}
	}

	@Override
	public void putMeasures(TogoMeasuresJson measures) {

		if (this.checkTogoApiSettings()) {
			log.info("Send measure with timestamp {} to TOGO", measures.getAt());

			measures.setClientId(Integer.parseInt(prop.getProperty("togoclientid")));
			measures.setDeviceId(prop.getProperty("togodeviceid"));

			for (TogoMeasureJson measure : measures.getMeasures())
				measure.setMeasureId(prop.getProperty("togomeasureid"));

			this.doPut(prop.getProperty("togoapiurl") + "organization/measures", measures);

			log.info("Measure sent to TOGO!");
		}
	}

	@Override
	public void deleteMeasures(String start, String end) {

		if (this.checkTogoApiSettings()) {
			log.info("Delete measures from TOGO from {} to {}", start, end);

			this.doDelete(prop.getProperty("togoapiurl") + "organization/measures?clientId=" + prop.getProperty("togoclientid") + "&deviceId=" + prop.getProperty("togodeviceid") + "&start=" + start + "&end=" + end);

			log.info("Measure deleted from TOGO!");
		}
	}

	private void doPut(String url, Object putObj) {

		if (token.equals("") || cookie.equals(""))
			getToken();

		ResponseEntity<Void> response = null;

		Integer retry = 1;
		while ((retry <= 3) || (response == null)) {
			if ((response == null) || (response.getStatusCode().equals(HttpStatus.FORBIDDEN))) {
				response = restTemplate.exchange(url, HttpMethod.PUT, new HttpEntity<>(putObj, setHttpHeaders()), Void.class);
				getToken();
			}
			retry += 1;
		}

		if (response.getStatusCode().equals(HttpStatus.FORBIDDEN))
			throw new ForbiddenException(493, "Cannot access to TOGO resource!");
	}

	private void doDelete(String url) {

		if (token.equals("") || cookie.equals(""))
			getToken();

		ResponseEntity<Void> response = null;

		Integer retry = 1;
		while ((retry <= 3) || (response == null)) {
			if ((response == null) || (response.getStatusCode().equals(HttpStatus.FORBIDDEN))) {
				response = restTemplate.exchange(url, HttpMethod.DELETE, new HttpEntity<>(setHttpHeaders()), Void.class);
				getToken();
			}
			retry += 1;
		}

		if (response.getStatusCode().equals(HttpStatus.FORBIDDEN))
			throw new ForbiddenException(493, "Cannot access to TOGO resource!");
	}

	private boolean checkTogoApiSettings() {

		return ((prop.getProperty("togoapiurl") != null) && !prop.getProperty("togoapiurl").equals(""));
	}

	private static HttpHeaders setHttpHeaders() {

		String plainCreds = prop.getProperty("togouser") + ":" + prop.getProperty("togopwd");

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + Base64.getEncoder().encodeToString(plainCreds.getBytes()));
		headers.add("X-CSRF-TOKEN", token);
		headers.add("Cookie", cookie);

		return headers;
	}

	private class PlusEncoderInterceptor implements ClientHttpRequestInterceptor {

		@Override
		public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
			return execution.execute(new HttpRequestWrapper(request) {
				@Override
				public URI getURI() {
					URI u = super.getURI();
					String strictlyEscapedQuery = StringUtils.replace(u.getRawQuery(), "+", "%2B");
					return UriComponentsBuilder.fromUri(u).replaceQuery(strictlyEscapedQuery).build(true).toUri();
				}
			}, body);
		}
	}
}