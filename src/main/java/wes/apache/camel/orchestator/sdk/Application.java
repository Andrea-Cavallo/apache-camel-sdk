package wes.apache.camel.orchestator.sdk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import wes.apache.camel.orchestator.sdk.utils.ExchangeUtils;
import wes.apache.camel.orchestator.sdk.utils.HttpUtils;
import wes.apache.camel.orchestator.sdk.utils.LogUtils;

/**
 * Main class for the application.
 */
@SpringBootApplication
public class Application {

	/**
	 * Starts the application.
	 *
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	/**
	 * Creates a LogUtils bean.
	 *
	 * @param objectMapper the ObjectMapper instance
	 * @return the LogUtils bean
	 */
	@Bean
	public LogUtils logUtils(ObjectMapper objectMapper) {
		return new LogUtils(objectMapper);
	}

	/**
	 * Creates an ExchangeUtils bean.
	 *
	 * @param logUtils     the LogUtils instance
	 * @param objectMapper the ObjectMapper instance
	 * @return the ExchangeUtils bean
	 */
	@Bean
	public ExchangeUtils exchangeUtils(LogUtils logUtils, ObjectMapper objectMapper) {
		return new ExchangeUtils(logUtils, objectMapper);

	}

	/**
	 * Creates an HttpUtils bean.
	 *
	 * @return the HttpUtils bean
	 */
	@Bean
	public HttpUtils httpUtils() {
		return new HttpUtils();
	}

	/**
	 * Creates an ObjectMapper bean.
	 *
	 * @return the ObjectMapper bean
	 */
	@Bean
	public ObjectMapper objectMapper() {
		ObjectMapper om = new ObjectMapper();
		om.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
		return om;
	}

}
