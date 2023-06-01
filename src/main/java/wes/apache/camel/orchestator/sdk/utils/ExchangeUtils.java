package wes.apache.camel.orchestator.sdk.utils;

import java.util.Map;
import java.util.Optional;

import org.apache.camel.Exchange;
import org.apache.camel.Predicate;
import org.apache.camel.ProducerTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.SneakyThrows;

/**
 * Utility class for working with Apache Camel Exchanges.
 * 
 * The ExchangeUtils class contains methods that deal with the Apache Camel
 * Exchange object and provides functionality for working with it. This class is
 * designed to simplify access to and manipulation of the Exchange object in a
 * more efficient way.
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 */
public class ExchangeUtils {

	private final LogUtils logUtils;
	private final ObjectMapper objectMapper;

	/**
	 * Constructs an instance of ExchangeUtils with the provided LogUtils.
	 *
	 * @param logUtils     the LogUtils instance to use for logging
	 * @param objectMapper the objectMapper instance used to serialize
	 */
	public ExchangeUtils(LogUtils logUtils, ObjectMapper objectMapper) {
		this.logUtils = logUtils;
		this.objectMapper = objectMapper;
	}

	/**
	 * Removes the specified property from the Exchange and then sets a new property
	 * with the given key-value pair.
	 * 
	 * @param exchange the Exchange object to modify
	 * @param key      the key of the property to remove and set
	 * @param value    the value to set for the property
	 * @param <T>      the type of the value
	 */
	public <T> void cleanAndSetProperty(Exchange exchange, String key, T value) {
		removeProperty(exchange, key);
		setProperty(exchange, key, value);
	}

	/**
	 * 
	 * Removes the specified header from the Exchange and then sets a new header
	 * with the given key-value pair.
	 * 
	 * @param exchange the Exchange object to modify
	 * @param key      the key of the header to remove and set
	 * @param value    the value to set for the header
	 * @param <T>      the type of the value
	 */
	public <T> void cleanAndSetHeader(Exchange exchange, String key, T value) {
		removeHeader(exchange, key);
		setHeader(exchange, key, value);
	}

	/**
	 * Sets a property with the specified key and value in the Exchange object.
	 *
	 * @param exchange the Exchange object to set the property in
	 * @param key      the property key
	 * @param value    the property value
	 * @param <T>      the type of the value
	 */
	public <T> void setProperty(Exchange exchange, String key, T value) {
		exchange.setProperty(key, value);
	}

	/**
	 * Gets a property from the Exchange object based on the specified key and type.
	 *
	 * @param exchange the Exchange object to retrieve the property from
	 * @param key      the property key
	 * @param type     the expected type of the property
	 * @return the property value, or null if not found or the value is not of the
	 *         expected type
	 * @param <T> the type of the class
	 */
	public <T> Optional<T> getProperty(Exchange exchange, String key, Class<T> type) {
		Object value = exchange.getProperty(key);
		return Optional.ofNullable(value).filter(type::isInstance).map(type::cast);
	}

	/**
	 * Checks if the Exchange object contains a property with the specified key.
	 *
	 * @param exchange the Exchange object to check
	 * @param key      the property key
	 * @return true if the property exists, false otherwise
	 */
	public boolean hasProperty(Exchange exchange, String key) {
		return exchange.getProperties().containsKey(key);
	}

	/**
	 * Removes a property from the Exchange object based on the specified key.
	 *
	 * @param exchange the Exchange object to remove the property from
	 * @param key      the property key
	 */
	public void removeProperty(Exchange exchange, String key) {
		exchange.getProperties().remove(key);
	}

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
	 * Gets a header from the Exchange object based on the specified key and type.
	 *
	 * @param exchange the Exchange object to retrieve the header from
	 * @param key      the header key
	 * @param type     the expected type of the header
	 * @return the header value, or null if not found or the value is not of the
	 *         expected type
	 */
	public <T> T getHeader(Exchange exchange, String key, Class<T> type) {
		Object value = exchange.getIn().getHeader(key);
		if (value != null && type.isInstance(value)) {
			return type.cast(value);
		}
		return null;
	}

	/**
	 * Checks if the Exchange object contains a header with the specified key.
	 *
	 * @param exchange the Exchange object to check
	 * @param key      the header key
	 * @return true if the header exists, false otherwise
	 */
	public boolean hasHeader(Exchange exchange, String key) {
		return exchange.getIn().getHeaders().containsKey(key);
	}

	/**
	 * Removes a header from the Exchange object based on the specified key.
	 *
	 * @param exchange the Exchange object to remove the header from
	 * @param key      the header key
	 */
	public void removeHeader(Exchange exchange, String key) {
		exchange.getIn().removeHeader(key);
	}

	/**
	 * Gets all the headers from the Exchange object.
	 *
	 * @param exchange the Exchange object to retrieve the headers from
	 * @return a map containing all the headers
	 */
	public Map<String, Object> getAllHeaders(Exchange exchange) {
		return exchange.getIn().getHeaders();
	}

	/**
	 * Sends the Exchange object to the specified endpoint URI.
	 *
	 * @param endpointURI the URI of the endpoint to send the Exchange to
	 * @param exchange    the Exchange object to send Exchange sent - is the object
	 *                    sended
	 */
	@SneakyThrows
	public void sendToEndpoint(String endpointURI, Exchange exchange) {
		try (ProducerTemplate createProducerTemplate = exchange.getContext().createProducerTemplate()) {
			logUtils.logEndpoint(endpointURI);
			Exchange sent = createProducerTemplate.send(endpointURI, exchange);
			exchange.setIn(sent.getIn());
		}
	}

	/**
	 * 
	 * Checks if the predicate associated with the given name in the Exchange
	 * evaluates to true.
	 * 
	 * @param exchange the Exchange object to retrieve the predicate from
	 * @param name     the name of the predicate to check
	 * @return true if the predicate evaluates to true, false otherwise
	 */
	public boolean checkPredicate(Exchange exchange, String name) {
		Predicate predicate = exchange.getProperty(name, Predicate.class);
		if (predicate != null) {
			return predicate.matches(exchange);
		}
		return false;
	}

	/**
	 * Serializes the given object into JSON and sets it as the body of the input
	 * message in the exchange. This method may throw an unchecked exception if the
	 * object cannot be serialized.
	 * 
	 * @param exchange The exchange whose input message's body will be set to the
	 *                 serialized form of the object.
	 * @param object   The object to be serialized and set as the body of the input
	 *                 message in the exchange. UncheckedIOException If the object
	 *                 cannot be serialized to JSON. This is a runtime exception.
	 * @see com.fasterxml.jackson.databind.ObjectMapper#writeValueAsString(Object)
	 */
	@SneakyThrows
	public void setSerializedBody(Exchange exchange, Object object) {

		String serializedObject = objectMapper.writeValueAsString(object);
		exchange.getIn().setBody(serializedObject);

	}

}
