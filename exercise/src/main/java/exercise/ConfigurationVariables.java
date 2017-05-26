package exercise;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class ConfigurationVariables {
	private static final String BUNDLE_NAME = "exercise.configurationVariables"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME);

	private ConfigurationVariables() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
	
	public static int getNumber(String key) {
		try {
			return Integer.parseInt(RESOURCE_BUNDLE.getString(key));
		} catch (MissingResourceException e) {
			return -99999;
		}
	}
}
