package edu.wustl.keggproject.client;

import com.smartgwt.client.data.DataSourceField;
import com.smartgwt.client.data.RestDataSource;
import com.smartgwt.client.data.fields.DataSourceIntegerField;
import com.smartgwt.client.types.DSDataFormat;
import com.smartgwt.client.types.FieldType;
import com.smartgwt.client.types.RPCTransport;


public class OptimizationDS extends RestDataSource{
	private static OptimizationDS instance = null;
	private static String baseurl = "http://128.252.160.238:8000/";
	private OptimizationDS(String id)
	{
		setID(id);
		
		// Cross domain JSON
		setDataFormat(DSDataFormat.JSON);
	    setDataTransport(RPCTransport.SCRIPTINCLUDE);
	    setCallbackParam("callback");
	    
		DataSourceIntegerField pkField = new DataSourceIntegerField("pk");
		pkField.setHidden(true);
		pkField.setPrimaryKey(true);
		
		DataSourceField rexpr  = new DataSourceField("r", FieldType.TEXT, "Right");
		DataSourceField symbol = new DataSourceField("s", FieldType.TEXT, "Symbol");
		DataSourceField lexpr  = new DataSourceField("l", FieldType.TEXT, "Left");
		// DataSourceTextField pathway =  new DataSourceTextField("pathway", "Pathway");
		// pathway.setHidden(true);
		// pathway.setValueMap("")
		setFields(pkField, rexpr, symbol, lexpr);
		
		setFetchDataURL(baseurl + "optfetch/");
	}
	
	public static OptimizationDS getInstance(){
		if (instance == null) {
			instance = new OptimizationDS("optimization");
			return instance;
		}
		else {
			return instance;
		}
	}
}
