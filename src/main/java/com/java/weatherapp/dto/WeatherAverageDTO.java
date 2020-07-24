package com.java.weatherapp.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

public class WeatherAverageDTO implements Serializable {

	private static final long serialVersionUID = 5763148931413501367L;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
	private LocalDate date;

	private BigDecimal temp;

	
	@JsonIgnore
	private BigDecimal totalDaily;

	@JsonIgnore
	private Integer quantDaily;

	public WeatherAverageDTO() {
		this.totalDaily = BigDecimal.ZERO;
		this.quantDaily = 0;
	}
	
	public void plusMap(WeatherMapTimeDTO map) {
		if (map.isDaily()) {
			this.totalDaily = this.totalDaily.add(map.getMain().getTemp());
			this.quantDaily++;
		} 
	}

public WeatherAverageDTO(LocalDate date, BigDecimal daily, BigDecimal totalDaily, Integer quantDaily) {
	super();
	this.date = date;
	this.temp = daily;
	this.totalDaily = totalDaily;
	this.quantDaily = quantDaily;
}

	
	public void totalize() {
		this.temp = (this.quantDaily > 0)
				? this.totalDaily.divide(new BigDecimal(this.quantDaily.toString()), 2, RoundingMode.HALF_UP)
				: null;
		}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getTemp() {
		return temp;
	}

	public void setTemp(BigDecimal temp) {
		this.temp = temp;
	}

	public BigDecimal getTotalDaily() {
		return totalDaily;
	}

	public void setTotalDaily(BigDecimal totalDaily) {
		this.totalDaily = totalDaily;
	}

	public Integer getQuantDaily() {
		return quantDaily;
	}

	public void setQuantDaily(Integer quantDaily) {
		this.quantDaily = quantDaily;
	}


	
}
