/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import java.time.LocalDate;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Adaptador (para JAXB) para convertir entre LocalDate y la representación
 * de la fecha en formato ISO 8601, como '2012-12-03'.
 * 
 * @author Juanma Guerra
 */
public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    // Convierte la fecha representada como una cadena en formato ISO 8601
    // a un objeto LocalDate.
	@Override
	public LocalDate unmarshal(String v) throws Exception {
		return LocalDate.parse(v);
	}

    // Convierte un objeto LocalDate a su representación en formato ISO 8601.
	@Override
	public String marshal(LocalDate v) throws Exception {
		return v.toString();
	}
}
