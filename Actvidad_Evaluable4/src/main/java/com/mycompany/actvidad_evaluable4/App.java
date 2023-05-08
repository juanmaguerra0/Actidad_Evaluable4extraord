package com.mycompany.actvidad_evaluable4;

import controller.PersonEditDialogController;
import controller.PersonOverviewController;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import model.Person;

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
     * Inicializa el diseño raíz.
     */
    public void initRootLayout() {
        try {
            // Cargar diseño raíz desde el archivo fxml.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(App.class.getResource("/View/RootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Mostrar la escena que contiene el diseño raíz.
            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
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

    public static void main(String[] args) {
        launch(args);
    }
}