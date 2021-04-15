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
import it.mynaproject.spms.model.IEnergyMeasureJson;
import it.mynaproject.spms.model.IEnergyMeasuresJson;
import it.mynaproject.spms.service.IEnergyService;
import it.mynaproject.spms.util.PropertiesFileReader;

@Service
public class IEnergyServiceImpl implements IEnergyService {

	final private static Logger log = LoggerFactory.getLogger(IEnergyService.class);

	private RestTemplate restTemplate;

	private static String cookie = "";

	private static String token = "";

	final private static Properties prop = PropertiesFileReader.loadPropertiesFile();

	@Autowired
    public IEnergyServiceImpl() {

		List<ClientHttpRequestInterceptor> interceptors = new ArrayList<ClientHttpRequestInterceptor>();
        interceptors.add(new PlusEncoderInterceptor());

        restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptors);
        restTemplate.setErrorHandler(new RestTemplateResponseErrorHandler());
    }

	@Override
	public void getToken() {

		log.info("New request for IEnergy token");

		ResponseEntity<Void> response = restTemplate.exchange(prop.getProperty("ienergyapiurl") + "token", HttpMethod.GET, new HttpEntity<String>(setHttpHeaders()), Void.class);
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
	public void putMeasures(IEnergyMeasuresJson measures) {

		if (this.checkIEnergySettings()) {
			log.info("Send measure with timestamp {} to IEnergy", measures.getAt());

			measures.setClientId(Integer.parseInt(prop.getProperty("ienergyclientid")));
			measures.setDeviceId(prop.getProperty("ienergydeviceid"));

			for (IEnergyMeasureJson measure : measures.getMeasures())
				measure.setMeasureId(prop.getProperty("ienergymeasureid"));

			this.doPut(prop.getProperty("ienergyapiurl") + "organization/measures", measures);

			log.info("Measure sent to IEnergy!");
		}
	}

	@Override
	public void deleteMeasures(String start, String end) {

		if (this.checkIEnergySettings()) {
			log.info("Delete measures from IEnergy from {} to {}", start, end);

			this.doDelete(prop.getProperty("ienergyapiurl") + "organization/measures?clientId=" + prop.getProperty("ienergyclientid") + "&deviceId=" + prop.getProperty("ienergydeviceid") + "&start=" + start + "&end=" + end);

			log.info("Measure deleted from IEnergy!");
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
			throw new ForbiddenException(493, "Cannot access to IEnergy resource!");
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
			throw new ForbiddenException(493, "Cannot access to IEnergy-Da resource!");
	}

	private boolean checkIEnergySettings() {

		return ((prop.getProperty("ienergyapiurl") != null) && !prop.getProperty("ienergyapiurl").equals(""));
	}

	private static HttpHeaders setHttpHeaders() {

		String plainCreds = prop.getProperty("ienergyuser") + ":" + prop.getProperty("ienergypwd");

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