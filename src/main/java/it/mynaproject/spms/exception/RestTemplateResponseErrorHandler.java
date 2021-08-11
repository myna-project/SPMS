package it.mynaproject.spms.exception;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

	@Override
	public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

		return ((httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) || (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR));
	}

	@Override
	public void handleError(ClientHttpResponse httpResponse) throws IOException {

		if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
			throw new GenericException(590, "Error during connection to TOGO-API");
		} else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
			if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
				throw new NotFoundException(494, "TOGO resource not found!");
			} else if (httpResponse.getStatusCode() == HttpStatus.BAD_REQUEST) {
				throw new ConflictException(490, "Bad request to TOGO-API!");
			}
		}
	}
}