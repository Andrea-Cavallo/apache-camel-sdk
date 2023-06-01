package wes.apache.camel.orchestator.sdk.utils;

import static wes.apache.camel.orchestator.sdk.utils.Constants.CORRELATION_ID;
import static wes.apache.camel.orchestator.sdk.utils.Constants.ENDPOINT_URI;
import static wes.apache.camel.orchestator.sdk.utils.Constants.EXCEPTION_TIME;
import static wes.apache.camel.orchestator.sdk.utils.Constants.MESSAGE;
import static wes.apache.camel.orchestator.sdk.utils.Constants.STATUS_CODE;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility class for working with Exception.
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 */
public class ExceptionUtils {

	/**
	 * Creates an error map with relevant exception details.
	 *
	 * @param exceptionTime time when the exception occurred
	 * @param responseBody  the response body from the HttpOperationFailedException
	 * @param statusCode    the HTTP status code from the
	 *                      HttpOperationFailedException
	 * @param endpointUri   the endpoint URI where the HttpOperationFailedException
	 *                      occurred
	 * @param correlationId the correlation ID of the exchange
	 * @return error map containing the exception details
	 */
	public Map<String, Object> createErrorMap(String exceptionTime, String responseBody, int statusCode,
			String endpointUri, String correlationId) {
		Map<String, Object> errorMap = new HashMap<>();
		errorMap.put(MESSAGE, responseBody);
		errorMap.put(STATUS_CODE, statusCode);
		errorMap.put(ENDPOINT_URI, endpointUri);
		errorMap.put(EXCEPTION_TIME, exceptionTime);
		errorMap.put(CORRELATION_ID, correlationId);
		return errorMap;
	}

}
