package com.java.weatherapp.dto;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

@JacksonXmlRootElement(localName = "weather")
public class WeatherMapDTO implements Serializable {

	private static final long serialVersionUID = 1253320017739887653L;

	private String cod;

	@JacksonXmlProperty(localName = "list")
	@JacksonXmlElementWrapper(localName = "list", useWrapping = true)
	private List<WeatherMapTimeDTO> list;

	public String getCod() {
		return cod;
	}

	public void setCod(String cod) {
		this.cod = cod;
	}


	public List<WeatherMapTimeDTO> getList() {
		return list;
	}

	public void setList(List<WeatherMapTimeDTO> list) {
		this.list = list;
	}

}
