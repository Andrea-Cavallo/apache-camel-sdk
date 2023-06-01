package wes.apache.camel.orchestator.sdk.utils;

import static wes.apache.camel.orchestator.sdk.utils.Constants.UNCHECKED;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 
 * The OrchestratorUtils class is a utility class for general orchestration
 * tasks. It contains methods for manipulating objects and performing common
 * operations related to orchestration.
 * 
 * @since 1.0
 * @author Andrea-Cavallo
 */
public class OrchestratorUtils {

	/**
	 * Private constructor to prevent instantiation of the OrchestratorUtils class.
	 */
	private OrchestratorUtils() {
	}

	/**
	 * Retrieves a map value from an object.
	 *
	 * @param obj the object to extract the map value from
	 * @return the map value if the object is of type Map, otherwise an empty map
	 */
	@SuppressWarnings(UNCHECKED)
	public static Map<String, Object> toMapValue(Object obj) {
		return Optional.ofNullable(obj).filter(Map.class::isInstance).map(map -> (Map<String, Object>) map)
				.orElse(Collections.emptyMap());
	}

	/**
	 * Retrieves a string value from an object.
	 *
	 * @param obj the object to extract the string value from
	 * @return the string value if the object is of type String, otherwise an empty
	 *         string
	 */
	public static String toStringValue(Object obj) {
		return Optional.ofNullable(obj).filter(String.class::isInstance).map(String.class::cast).orElse("");
	}

	/**
	 * Retrieves a list of maps from an object.
	 *
	 * @param obj the object to extract the list of maps from
	 * @return the list of maps if the object is of type List, otherwise an empty
	 *         list
	 */
	@SuppressWarnings(UNCHECKED)
	public static List<Map<String, Object>> toListOfMap(Object obj) {
		return Optional.ofNullable(obj).filter(List.class::isInstance).map(list -> (List<Map<String, Object>>) list)
				.orElse(Collections.emptyList());
	}

	/**
	 * Retrieves a long value from an object.
	 *
	 * @param obj the object to extract the long value from
	 * @return the long value if the object is of type Long, otherwise 0L
	 */
	public static Long toLongValue(Object obj) {
		return Optional.ofNullable(obj).filter(Long.class::isInstance).map(Long.class::cast).orElse(0L);
	}

	/**
	 * Retrieves an integer value from an object.
	 *
	 * @param obj the object to extract the integer value from
	 * @return the integer value if the object is of type Integer, otherwise 0
	 */
	public static Integer toIntegerValue(Object obj) {
		return Optional.ofNullable(obj).filter(Integer.class::isInstance).map(Integer.class::cast).orElse(0);
	}
}