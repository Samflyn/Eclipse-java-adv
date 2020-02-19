package com.sailotech.mcap.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class JsonDateTimeSerializerUtil extends JsonSerializer<Date> {

	@Override
	public void serialize(Date value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException {
		   SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		    String dateString = dateFormat.format(value);
		    gen.writeString(dateString);
		
	}
	
}
