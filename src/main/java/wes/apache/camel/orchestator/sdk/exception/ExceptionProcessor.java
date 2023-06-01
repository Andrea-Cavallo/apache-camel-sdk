package wes.apache.camel.orchestator.sdk.exception;

import static wes.apache.camel.orchestator.sdk.utils.Constants.CORRELATION_ID;
import static wes.apache.camel.orchestator.sdk.utils.Constants.MISSING_CORRELATION_ID_PROPERTY;
import static wes.apache.camel.orchestator.sdk.utils.Constants.UNKNOWN_ENDPOINT;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.RuntimeCamelException;
import org.apache.camel.http.base.HttpOperationFailedException;

import wes.apache.camel.orchestator.sdk.restresponse.RestResponseMap;
import wes.apache.camel.orchestator.sdk.utils.ExceptionUtils;
import wes.apache.camel.orchestator.sdk.utils.ExchangeUtils;
import wes.apache.camel.orchestator.sdk.utils.LogUtils;
import wes.apache.camel.orchestator.sdk.utils.OrchestratorUtils;

/**
 * ExceptionProcessor processes exceptions in an Apache Camel exchange and
 * handles HttpOperationFailedException specifically.
 *
 * The processor modifies the exchange body to reflect error details.
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 */
public class ExceptionProcessor implements Processor {

	private final ExchangeUtils exchangeUtils;
	private final ExceptionUtils exceptionUtils;
	private final LogUtils logUtils;

	/**
	 * Constructor for the ExceptionProcessor
	 * 
	 * @param exchangeUtils utility for handling Apache Camel exchange operations
	 */
	public ExceptionProcessor(ExchangeUtils exchangeUtils, ExceptionUtils exceptionUtils, LogUtils logUtils) {
		this.exchangeUtils = exchangeUtils;
		this.exceptionUtils = exceptionUtils;
		this.logUtils = logUtils;
	}

	/**
	 * The process method is invoked when a message needs to be processed.
	 *
	 * This method handles exceptions by checking if the exception in the exchange
	 * property is an instance of HttpOperationFailedException or
	 * RuntimeCamelException and creating a response body with error details
	 * accordingly.
	 *
	 * @param exchange the message exchange
	 */
	@Override
	public void process(Exchange exchange) {
		Optional<Throwable> optionalException = exchangeUtils.getProperty(exchange, Exchange.EXCEPTION_CAUGHT,
				Throwable.class);

		optionalException.filter(HttpOperationFailedException.class::isInstance)
				.ifPresent(exception -> handleHttpOperationFailedException(exchange, exception));

		optionalException.filter(RuntimeCamelException.class::isInstance)
				.ifPresent(exception -> handleRuntimeCamelException(exchange, exception));
	}

	/**
	 * Handles HttpOperationFailedException by creating an error map and setting it
	 * as the body of the exchange message. A very important note about the *
	 * HttpOperationFailedException is that if it is thrown, we have the opportunity
	 * to collect very important information that we would indeed lose with other
	 * types of exceptions. I refer to the reading of this exception.
	 * 
	 * @param exchange  the message exchange
	 * @param exception the HttpOperationFailedException
	 */
	private void handleHttpOperationFailedException(Exchange exchange, Throwable exception) {
		HttpOperationFailedException httpException = (HttpOperationFailedException) exception;

		String correlationId = exchangeUtils.getProperty(exchange, CORRELATION_ID, String.class)
				.orElseThrow(() -> new RuntimeException(MISSING_CORRELATION_ID_PROPERTY));
		String exceptionTime = OrchestratorUtils.toStringValue(LocalDateTime.now());
		String responseBody = httpException.getResponseBody();
		int statusCode = httpException.getHttpResponseCode();
		String endpointUri = (exchange.getFromEndpoint() != null) ? exchange.getFromEndpoint().getEndpointUri()
				: UNKNOWN_ENDPOINT;
		String headersString = httpException.getResponseHeaders().entrySet().stream()
				.map(entry -> entry.getKey() + ":" + entry.getValue()).collect(Collectors.joining(","));

		createOnExceptionRestResponse(exchange, responseBody, statusCode, endpointUri, correlationId, exceptionTime,
				headersString);
	}

	/**
	 * Handles the RuntimeCamelException by creating an error map and setting it as
	 * the body of the exchange message.
	 *
	 * @param exchange  the message exchange
	 * @param throwable the RuntimeCamelException to handle
	 */
	private void handleRuntimeCamelException(Exchange exchange, Throwable throwable) {
		RuntimeCamelException runtimeCamelException = (RuntimeCamelException) throwable;
		String correlationId = exchangeUtils.getProperty(exchange, CORRELATION_ID, String.class)
				.orElseThrow(() -> new RuntimeException(MISSING_CORRELATION_ID_PROPERTY));
		String exceptionTime = OrchestratorUtils.toStringValue(LocalDateTime.now());
		String responseBody = runtimeCamelException.getMessage();

		createOnExceptionRestResponse(exchange, responseBody, 0, "", correlationId, exceptionTime, "");
	}

	/**
	 * Handles the exception by creating an error map and setting it as the body of
	 * the exchange message.
	 *
	 * @param exchange      the message exchange
	 * @param responseBody  the body of the error response
	 * @param statusCode    the HTTP status code of the error response
	 * @param endpointUri   the URI of the endpoint that returned the error response
	 * @param correlationId the correlation ID associated with the request
	 * @param exceptionTime the timestamp of the exception occurrence
	 * @param headersString the headers of the error response as a concatenated
	 *                      string
	 */
	private void createOnExceptionRestResponse(Exchange exchange, String responseBody, int statusCode,
			String endpointUri, String correlationId, String exceptionTime, String headersString) {
		Map<String, Object> errorMap = exceptionUtils.createErrorMap(exceptionTime, responseBody, statusCode,
				endpointUri, correlationId);
		RestResponseMap restResponse = new RestResponseMap(null, null, errorMap);
		logUtils.logErrorResponse(endpointUri, responseBody, headersString);
		exchangeUtils.setSerializedBody(exchange, restResponse);
	}

}
