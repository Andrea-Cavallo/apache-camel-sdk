package wes.apache.camel.orchestator.sdk.utils;

public class Constants {

	private Constants() {
	}

	public static final String CORRELATION_ID = "correlation-id";
	public static final String EXCEPTION_TIME = "exceptionTime";
	public static final String ENDPOINT_URI = "endpointURI";
	public static final String STATUS_CODE = "statusCode";
	public static final String MESSAGE = "message";
	public static final String POST = "POST";
	public static final String GET = "GET";
	public static final String APPLICATION_JSON = "application/json";
	public static final String PUT = "PUT";
	public static final String CAMEL_HTTP_PATH = "CamelHttpPath";
	public static final String DELETE = "DELETE";
	public static final String UTF_8 = "UTF-8";
	public static final String MISSING_CORRELATION_ID_PROPERTY = "Missing correlation-id property";
	public static final String UNKNOWN_ENDPOINT = "unknown-endpoint";
	public static final String UNCHECKED = "unchecked";
	public static final String MESSAGE_AND_BODY = "** {} {} **";
	public static final String ERROR_SERIALIZING_OBJECT = "Error serializing {} object";
	public static final String INVOKED_ENDPOINT = "** Invoked endpoint: {} **";
	public static final String EXCEPTION_OCCURRED = " ** Exception occurred: {}";
	public static final String RECEIVED_ERROR_RESPONSE_FROM = "Received error response from endpoint: {}, body: {}, headers: {}";
	public static final String ELAPSED_TIME_IS = " ** elapsed time is: {}";
}
