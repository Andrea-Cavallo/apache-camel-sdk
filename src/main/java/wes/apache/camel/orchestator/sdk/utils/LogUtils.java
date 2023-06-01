package wes.apache.camel.orchestator.sdk.utils;

import static wes.apache.camel.orchestator.sdk.utils.Constants.ELAPSED_TIME_IS;
import static wes.apache.camel.orchestator.sdk.utils.Constants.ERROR_SERIALIZING_OBJECT;
import static wes.apache.camel.orchestator.sdk.utils.Constants.EXCEPTION_OCCURRED;
import static wes.apache.camel.orchestator.sdk.utils.Constants.INVOKED_ENDPOINT;
import static wes.apache.camel.orchestator.sdk.utils.Constants.MESSAGE_AND_BODY;
import static wes.apache.camel.orchestator.sdk.utils.Constants.RECEIVED_ERROR_RESPONSE_FROM;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * The LogUtils class provides utility methods for logging input, output,
 * endpoints, and exceptions. It uses an instance of the ObjectMapper class for
 * serializing objects into JSON strings.
 * <p>
 * 
 * This class is designed to assist with logging in orchestration-related tasks.
 * </p>
 * <p>
 * The methods in this class are not static and require an instance of the
 * ObjectMapper class to be provided through the constructor.
 * </p>
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 */
@Slf4j
public class LogUtils {

	public LogUtils(ObjectMapper objectMapper) {
		super();
		this.objectMapper = objectMapper;
	}

	private final ObjectMapper objectMapper;

	/**
	 * Serialize and log the input/output object.
	 *
	 * @param obj     the object to be logged
	 * @param message the prefix to be used in the log message: example, Input body
	 *                is:
	 * @param <T>     the type of the object
	 */
	public <T> void serializeAndLog(T obj, String message) {
		try {
			String serialized = objectMapper.writeValueAsString(obj);
			log.info(MESSAGE_AND_BODY, message, serialized);
		} catch (JsonProcessingException e) {
			log.error(ERROR_SERIALIZING_OBJECT, message.toLowerCase(), e);
		}
	}

	/**
	 * Logs the invoked endpoint URI.
	 *
	 * @param uri the URI of the invoked endpoint
	 */
	public void logEndpoint(String uri) {
		log.info(INVOKED_ENDPOINT, uri);
	}

	/**
	 * Logs the exception message.
	 *
	 * @param e the exception to be logged
	 */
	public void logException(Exception e) {
		log.error(EXCEPTION_OCCURRED, e.getMessage());

	}

	/**
	 * Logs the exception message.
	 *
	 * @param startTime given a startTime logs the elapsed time
	 */
	public void logElapsedTime(long startTime) {

		long endTime = System.currentTimeMillis();
		long duration = endTime - startTime;
		String elapsedTime = OrchestratorUtils.toStringValue(duration);
		log.debug(ELAPSED_TIME_IS, elapsedTime);
	}

	/**
	 * Logs an error response received from an endpoint.
	 *
	 * @param endpointUri   The URI of the endpoint that returned the error
	 *                      response.
	 * @param responseBody  The body of the error response.
	 * @param headersString The headers of the error response as a concatenated
	 *                      string.
	 */
	public void logErrorResponse(String endpointUri, String responseBody, String headersString) {
		log.debug(RECEIVED_ERROR_RESPONSE_FROM, endpointUri, responseBody, headersString);
	}

}
