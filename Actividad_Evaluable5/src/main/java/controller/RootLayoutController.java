/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import com.mycompany.actividad_evaluable5.App;

/**
 * El controlador para el diseño raíz. El diseño raíz proporciona la estructura básica
 * de la aplicación que contiene una barra de menú y un espacio donde se pueden colocar
 * otros elementos de JavaFX.
 * 
 * @author Marco Jakob
 */
public class RootLayoutController {

    // Referencia a la aplicación principal
    private App mainApp;

    /**
     * Es llamado por la aplicación principal para dar una referencia a sí misma.
     * 
     * @param mainApp
     */
    public void setMainApp(App mainApp) {
        this.mainApp = mainApp;
    }

    /**
     * Crea una libreta de direcciones vacía.
     */
    @FXML
    private void handleNew() {
        mainApp.getPersonData().clear();
        mainApp.setPersonFilePath(null);
    }

    /**
     * Abre un FileChooser para permitir al usuario seleccionar una libreta de direcciones para cargar.
     */
    @FXML
    private void handleOpen() {
        FileChooser fileChooser = new FileChooser();

        // Establecer filtro de extensión
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
                "Archivos XML (*.xml)", "*.xml");
        fileChooser.getExtensionFilters().add(extFilter);

        // Mostrar el cuadro de diálogo Abrir archivo
        File file = fileChooser.showOpenDialog(mainApp.getPrimaryStage());

        if (file != null) {
            mainApp.loadPersonDataFromFile(file);
        }
    }

    /**
     * Guarda el archivo en el archivo de persona que está actualmente abierto. Si no hay
     * archivo abierto, se muestra el cuadro de diálogo "guardar como".
     */
    @FXML
    private void handleSave() {
        File personFile = mainApp.getPersonFilePath();
        if (personFile != null) {
            mainApp.savePersonDataToFile(personFile);
        } else {
            handleSaveAs();
        }
    }

    /**
     * Abre un FileChooser para permitir al usuario seleccionar un archivo para guardar.
     */
    @FXML
    private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();

		// Establecer filtro de extensión
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"Archivos XML (*.xml)", "*.xml");
		fileChooser.getExtensionFilters().add(extFilter);

		// Mostrar cuadro de diálogo Guardar archivo
		File file = fileChooser.showSaveDialog(mainApp.getPrimaryStage());

		if (file != null) {
			// Asegurar que tenga la extensión correcta
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
			}
			mainApp.savePersonDataToFile(file);
		}
	}

    /**
     * Abre un cuadro de diálogo Acerca de.
     */
    @FXML
    private void handleAbout() {
    	Alert alert = new Alert(AlertType.INFORMATION);
    	alert.setTitle("AddressApp");
    	alert.setHeaderText("Acerca de");
    	alert.setContentText("Autor: Juanma Guerra\nSitio web: http://code.makery.ch");

    	alert.showAndWait();
    }

    /**
     * Cierra la aplicación.
     */
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}
