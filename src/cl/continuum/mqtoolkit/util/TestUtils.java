package cl.continuum.mqtoolkit.util;

import java.util.ArrayList;
import java.util.List;

public class TestUtils {
	
	public List<String> formatMessage(List<String> nonParsedBody){
		String message;
		List<String> parsed = new ArrayList<String>();
		for (int i = 0; i < nonParsedBody.size(); i++) {
			message = nonParsedBody.get(i);
			message.replaceAll("#", String.valueOf(i+1));
			parsed.add(message);
		}
		return parsed;
		
	}

}
