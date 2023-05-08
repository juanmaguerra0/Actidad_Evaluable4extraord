/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Clase auxiliar para envolver una lista de personas. Se utiliza para guardar
 * la lista de personas en XML.
 * 
 * @author Juanma Guerra
 */
// Define el nombre del elemento raíz XML como "persons"
@XmlRootElement(name = "persons")
public class PersonListWrapper {

	// Lista de objetos Person que serán almacenados en XML
	private List<Person> persons;

	// Define el nombre del elemento XML como "person" para cada objeto Person
	// en la lista.
	@XmlElement(name = "person")
	public List<Person> getPersons() {
		return persons;
	}

	// Establece la lista de objetos Person
	public void setPersons(List<Person> persons) {
		this.persons = persons;
	}
}
