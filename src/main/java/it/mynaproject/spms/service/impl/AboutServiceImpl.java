package it.mynaproject.spms.service.impl;

import java.util.Properties;

import org.springframework.stereotype.Service;

import it.mynaproject.spms.model.AboutJson;
import it.mynaproject.spms.service.AboutService;
import it.mynaproject.spms.util.PropertiesFileReader;

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
