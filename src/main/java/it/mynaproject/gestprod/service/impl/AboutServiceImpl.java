package it.mynaproject.gestprod.service.impl;

import java.util.Properties;

import org.springframework.stereotype.Service;

import it.mynaproject.gestprod.model.AboutJson;
import it.mynaproject.gestprod.service.AboutService;
import it.mynaproject.gestprod.util.PropertiesFileReader;

@Service
public class AboutServiceImpl implements AboutService {

	public AboutJson getVersionAndBuildtime() {

		AboutJson aboutJson = new AboutJson();

		Properties prop = PropertiesFileReader.loadPropertiesFile();

		aboutJson.setVersion(prop.getProperty("version"));
		aboutJson.setBuildtime(prop.getProperty("buildtimestamp"));

		return aboutJson;
	}
}
