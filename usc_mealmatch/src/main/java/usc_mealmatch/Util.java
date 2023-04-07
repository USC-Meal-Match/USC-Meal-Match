package usc_mealmatch;

public class Util {
	public static class MissingEnvironmentVariableException extends RuntimeException {
		private static final long serialVersionUID = -7357073201666337511L;

		public MissingEnvironmentVariableException(String key) {
			super(String.format("Environment variable %s is not defined", key));
		}
	}

	/**
	 * Get value from environment variable
	 * 
	 * @param key associated with the environment variable
	 * @return value associated with the environment variable
	 * @throws MissingEnvironmentVariableException if key does not exist
	 */
	public static String getEnv(String key) {
		String value = System.getenv(key);
		if (value != null) {
			return value;
		} else {
			throw new MissingEnvironmentVariableException(key);
		}
	}
}
