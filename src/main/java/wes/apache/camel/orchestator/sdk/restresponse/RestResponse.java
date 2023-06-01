package wes.apache.camel.orchestator.sdk.restresponse;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Represents a common REST response.
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, ignoreUnknown = true)
public abstract class RestResponse {

	protected Map<String, Object> errorMessages;
	protected List<String> validationErrors;

	/**
	 * Constructs a RestResponse object.
	 *
	 * @param errorMessages    a map of error messages
	 * @param validationErrors a list of validation errors
	 */
	protected RestResponse(Map<String, Object> errorMessages, List<String> validationErrors) {
		this.errorMessages = errorMessages;
		this.validationErrors = validationErrors;
	}

}
