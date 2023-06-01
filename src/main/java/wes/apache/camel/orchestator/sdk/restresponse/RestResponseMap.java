package wes.apache.camel.orchestator.sdk.restresponse;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Extends RestResponse, one of the possible RestResponse with a Map output as
 * standards
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 *
 */
@Getter
@Setter
@ToString
@JsonIgnoreProperties(value = { "hibernateLazyInitializer", "handler" }, ignoreUnknown = true)
public class RestResponseMap extends RestResponse {

	private Map<String, Object> output;

	/**
	 * Constructs a RestResponseMap object.
	 *
	 * @param errorMessages    a map of error messages
	 * @param validationErrors a list of validation errors
	 * @param output           the map representing the response output
	 */
	public RestResponseMap(Map<String, Object> errorMessages, List<String> validationErrors,
			Map<String, Object> output) {
		super(errorMessages, validationErrors);
		this.output = output;
	}

	/**
	 * Checks if the output is empty and if either errorMessages or validationErrors
	 * are not empty.
	 *
	 * @return true if the output is empty and either errorMessages or
	 *         validationErrors are not empty, false otherwise
	 */
	public boolean hasErrors() {
		return (output == null || output.isEmpty()) && (errorMessages != null && !errorMessages.isEmpty()
				|| validationErrors != null && !validationErrors.isEmpty());
	}

}
