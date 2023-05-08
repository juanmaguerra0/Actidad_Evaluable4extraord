package com.mycompany.actividad_evaluable5;

import controller.PersonEditDialogController;
import controller.PersonOverviewController;
import controller.RootLayoutController;
import java.io.File;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.prefs.Preferences;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import model.Person;
import model.PersonListWrapper;

/**
 * Aplicación JavaFX
 */
public class App extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    // Los datos como una lista observable de Personas.
    private ObservableList<Person> personData = FXCollections.observableArrayList();

    // Punto de entrada principal para la aplicación JavaFX
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("AddressApp");

        // Agregar algunos datos de muestra
        personData.add(new Person("Hans", "Muster"));
        // ... (datos de muestra restantes)

        initRootLayout();
        showPersonOverview();
    }

    /**
 * Initializes the root layout and tries to load the last opened
 * person file.
 */
public void initRootLayout() {
    try {
        // Load root layout from fxml file.
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(App.class
                .getResource("view/RootLayout.fxml"));
        rootLayout = (BorderPane) loader.load();

        // Show the scene containing the root layout.
        Scene scene = new Scene(rootLayout);
        primaryStage.setScene(scene);

        // Give the controller access to the main app.
        RootLayoutController controller = loader.getController();
        controller.setMainApp(this);

        primaryStage.show();
    } catch (IOException e) {
        e.printStackTrace();
    }

    // Try to load last opened person file.
    File file = getPersonFilePath();
    if (file != null) {
        loadPersonDataFromFile(file);
    }
}
    /**
     * Muestra la vista general de la persona dentro del diseño raíz.
     */
    public void showPersonOverview() {
        try {
            // Cargar la vista general de la persona.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/View/PersonOverview.fxml"));
            AnchorPane personOverview = (AnchorPane) loader.load();

            // Establecer la vista general de la persona en el centro del diseño raíz.
            rootLayout.setCenter(personOverview);

            // Darle al controlador acceso a la aplicación principal.
            PersonOverviewController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Devuelve el escenario principal.
     *
     * @return
     */
    public Stage getPrimaryStage() {
        return primaryStage;
    }

    /**
     * Devuelve los datos como una lista observable de Personas.
     *
     * @return
     */
    public ObservableList<Person> getPersonData() {
        return personData;
    }

    /**
     * Abre un cuadro de diálogo para editar los detalles de la persona especificada. Si el usuario
     * hace clic en Aceptar, los cambios se guardan en el objeto persona proporcionado y se devuelve true.
     *
     * @param person el objeto persona a editar
     * @return true si el usuario hizo clic en Aceptar, false en caso contrario.
     */
    public boolean showPersonEditDialog(Person person) {
        try {
            // Cargar el archivo fxml y crear un nuevo escenario para el cuadro de diálogo emergente.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("UI/PersonEditDialog.fxml"));
            AnchorPane page = (AnchorPane) loader.load();

            // Crear el escenario del cuadro de diálogo.
            Stage dialogStage = new Stage();
            dialogStage.setTitle("Editar Persona");
            dialogStage.initModality(Modality.WINDOW_MODAL);
            dialogStage.initOwner(primaryStage);
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

             // Establecer la persona en el controlador.
            PersonEditDialogController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setPerson(person);

            // Mostrar el cuadro de diálogo y esperar hasta que el usuario lo cierre
            dialogStage.showAndWait();

            return controller.isOkClicked();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    /**
 * Returns the person file preference, i.e. the file that was last opened.
 * The preference is read from the OS specific registry. If no such
 * preference can be found, null is returned.
 * 
 * @return
 */
public File getPersonFilePath() {
    Preferences prefs = Preferences.userNodeForPackage(App.class);
    String filePath = prefs.get("filePath", null);
    if (filePath != null) {
        return new File(filePath);
    } else {
        return null;
    }
}

/**
 * Sets the file path of the currently loaded file. The path is persisted in
 * the OS specific registry.
 * 
 * @param file the file or null to remove the path
 */
public void setPersonFilePath(File file) {
    Preferences prefs = Preferences.userNodeForPackage(App.class);
    if (file != null) {
        prefs.put("filePath", file.getPath());

        // Update the stage title.
        primaryStage.setTitle("AddressApp - " + file.getName());
    } else {
        prefs.remove("filePath");

        // Update the stage title.
        primaryStage.setTitle("AddressApp");
    }
}

/**
 * Loads person data from the specified file. The current person data will
 * be replaced.
 * 
 * @param file
 */
public void loadPersonDataFromFile(File file) {
    try {
        JAXBContext context = JAXBContext
                .newInstance(PersonListWrapper.class);
        Unmarshaller um = context.createUnmarshaller();

        // Reading XML from the file and unmarshalling.
        PersonListWrapper wrapper = (PersonListWrapper) um.unmarshal(file);

        personData.clear();
        personData.addAll(wrapper.getPersons());

        // Save the file path to the registry.
        setPersonFilePath(file);

    } catch (Exception e) { // catches ANY exception
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not load data");
        alert.setContentText("Could not load data from file:\n" + file.getPath());

        alert.showAndWait();
    }
}

/**
 * Saves the current person data to the specified file.
 * 
 * @param file
 */
public void savePersonDataToFile(File file) {
    try {
        JAXBContext context = JAXBContext
                .newInstance(PersonListWrapper.class);
        Marshaller m = context.createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        // Wrapping our person data.
        PersonListWrapper wrapper = new PersonListWrapper();
        wrapper.setPersons(personData);

        // Marshalling and saving XML to the file.
        m.marshal(wrapper, file);

        // Save the file path to the registry.
        setPersonFilePath(file);
    } catch (Exception e) { // catches ANY exception
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Could not save data");
        alert.setContentText("Could not save data to file:\n" + file.getPath());

        alert.showAndWait();
    }
}

    public static void main(String[] args) {
        launch(args);
    }
}