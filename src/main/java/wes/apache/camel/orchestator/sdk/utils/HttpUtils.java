package wes.apache.camel.orchestator.sdk.utils;

import static wes.apache.camel.orchestator.sdk.utils.Constants.APPLICATION_JSON;
import static wes.apache.camel.orchestator.sdk.utils.Constants.CAMEL_HTTP_PATH;
import static wes.apache.camel.orchestator.sdk.utils.Constants.DELETE;
import static wes.apache.camel.orchestator.sdk.utils.Constants.GET;
import static wes.apache.camel.orchestator.sdk.utils.Constants.POST;
import static wes.apache.camel.orchestator.sdk.utils.Constants.PUT;
import static wes.apache.camel.orchestator.sdk.utils.Constants.UTF_8;

import java.util.Objects;

import org.apache.camel.Exchange;

/**
 * Utility class for setting HTTP-related headers in an Exchange object.
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 */
public class HttpUtils {

	/**
	 * Sets a header with the specified key and value in the Exchange object.
	 *
	 * @param exchange the Exchange object to set the header in
	 * @param key      the header key
	 * @param value    the header value
	 */
	public void setHeader(Exchange exchange, String key, Object value) {
		exchange.getIn().setHeader(key, value);
	}

	/**
	 * Sets the HTTP method header in the Exchange object.
	 *
	 * @param exchange the Exchange object to set the method header in
	 * @param method   the HTTP method
	 */
	public void setMethodHeader(Exchange exchange, String method) {
		exchange.getIn().setHeader(Exchange.HTTP_METHOD, method);
	}

	/**
	 * Sets the headers for a POST request in the Exchange object.
	 *
	 * @param exchange the Exchange object to set the POST headers in
	 * @param body     the request body
	 */
	public void setPostHeaders(Exchange exchange, String body) {
		setMethodHeader(exchange, POST);
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, APPLICATION_JSON);
		this.setHeader(exchange, Exchange.CONTENT_ENCODING, UTF_8);

		if (Objects.nonNull(body)) {
			exchange.getIn().setBody(body);
		}
	}

	/**
	 * Sets the headers for a GET request in the Exchange object.
	 *
	 * @param exchange the Exchange object to set the GET headers in
	 */
	public void setGetHeaders(Exchange exchange) {
		setMethodHeader(exchange, GET);
		this.setHeader(exchange, Exchange.CONTENT_ENCODING, UTF_8);
	}

	/**
	 * Sets the headers for a PUT request in the Exchange object.
	 *
	 * @param exchange the Exchange object to set the PUT headers in
	 * @param body     the request body
	 */
	public void setPutHeaders(Exchange exchange, String body) {
		setMethodHeader(exchange, PUT);
		exchange.getIn().setHeader(Exchange.CONTENT_TYPE, APPLICATION_JSON);
		this.setHeader(exchange, Exchange.CONTENT_ENCODING, UTF_8);

		if (Objects.nonNull(body)) {
			exchange.getIn().setBody(body);
		}
	}

	/**
	 * Sets the headers for a DELETE request in the Exchange object.
	 *
	 * @param exchange the Exchange object to set the DELETE headers in
	 */
	public void setDeleteHeaders(Exchange exchange) {
		setMethodHeader(exchange, DELETE);
		this.setHeader(exchange, Exchange.CONTENT_ENCODING, UTF_8);

	}

	public void removeUnnecessaryHeaders(Exchange exchange) {
		exchange.getIn().removeHeader(Exchange.HTTP_URI);
		exchange.getIn().removeHeader(CAMEL_HTTP_PATH);
		exchange.getIn().removeHeader(Exchange.HTTP_QUERY);

	}

}
