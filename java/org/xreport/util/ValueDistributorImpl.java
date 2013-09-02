package org.xreport.util;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.xreport.entities.Parameter;

import com.mavaris.webcaravella.element.ValueDistributor;

public class ValueDistributorImpl implements ValueDistributor {

	private Map<String, String> values;
	
	public ValueDistributorImpl(Parameter[] parameters){
		values = new HashMap<String, String>();
		Parameter p;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
		if(parameters!=null){
			for(int i=0; i<parameters.length; i++){
				p=parameters[i];
				if(p.getId().equals(ParameterType.PPeriod.getType())){
					values.put("pfrom",	dateFormat.format(p.getValFrom()));
					values.put("pto", dateFormat.format(p.getValTo()));
				}else if(p.getId().equals(ParameterType.PDate.getType())){
					values.put(p.getId().toLowerCase(), dateFormat.format(p.getValDate()));
				}else{
					values.put(p.getId().toLowerCase(), p.getValString());
				}
			}
		}
	}

	@Override
	public String getValue(String elementName) {
   	 if (elementName == null){
		 return null;
	 }
     return values.get(elementName);
	}

}
