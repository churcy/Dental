package com.tinytree.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class LoadProperty {
	
	public    Properties load(String propertyName){
		InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(propertyName);   
		Properties p = new Properties();   
		  try {   
		   p.load(inputStream); 
		  } catch (IOException e1) {   
		   e1.printStackTrace();   
		  }
		  finally{
			  
		  }
		return p;
	}
	
}
