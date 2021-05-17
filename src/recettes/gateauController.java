package recettes;

import java.awt.event.ActionEvent;
import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;

public class gateauController {

    @FXML
    private MenuBar barMenu;

    @FXML
    private MenuItem menuSaveAs;

    @FXML
    private MenuItem menuNew;

    @FXML
    private Button btnConfirmer;

    @FXML
    private Text txtServir;

    @FXML
    private TableColumn<Gateau, String> columnIngredient;

    @FXML
    private Button btnEffacer;

    @FXML
    private Text txtPersonnes;

    @FXML
    private Button btnRecommencer;

    @FXML
    private TableColumn<Gateau, Double> columnQuantite;

    @FXML
    private Button btnModifier;

    @FXML
    private Text txtGateau;

    @FXML
    private TextField txtNum1;

    @FXML
    private ComboBox<String> cboIngredient;

    @FXML
    private MenuItem menuOuvrir;

    @FXML
    private MenuItem menuSave;

    @FXML
    private TableView<Gateau> tableauQuantite;




   //liste pour les ingrédients: permettra de donner les valuers du ComboBox
    private ObservableList<String> list=(ObservableList<String>) FXCollections.observableArrayList("Farine","Sucre","Poudre de cacao","oeufs","lait","bicarbonate de soude","levure chimique");

   
   //placer les ingrédeints dans un observable list
    public ObservableList<Gateau> gateauData=FXCollections.observableArrayList();
   
    //Créer une méthode pour accéder à la liste
    public ObservableList<Gateau> getgateauData()
    {
    	return gateauData;
    }
    public void initialize(URL location, ResourceBundle resources) 
	{
		cboIngredient.setItems(list);
		
	//attribuer les valeurs aux colonnes du tableView
	columnQuantite.setCellValueFactory(new PropertyValueFactory<>("Quantité"));
	columnIngredient.setCellValueFactory(new PropertyValueFactory<>("Ingrédient"));
	tableauQuantite.setItems(gateauData);
		
		showGateau(null);
		
	//Mettre à jour l'affichage d'une donnée selectionné
	tableauQuantite.getSelectionModel().selectedItemProperty().addListener((
		observable, oldValue, newValue)-> showGateau(newValue));
	}
	
    private void showGateau(Object object) {
		// TODO Auto-generated method stub
		
	}

	//Ajouter un étudiant
	@FXML
	void ajouter()
	{
			Gateau tmp=new Gateau();
		tmp=new Gateau();
		tmp.setQuantite(Double.parseDouble(txtNum1.getText()));
		tmp.setIngredient(cboIngredient.getValue());
		gateauData.add(tmp); 
		clearFields();
	}
		
	//Effacer le contenu des champs
		@FXML
		void clearFields()
		{
			cboIngredient.setValue(null);
			txtNum1.setText("");
		}

	//Afficher les quantités
		@FXML
		void showObjet(Gateau gateau)
		{
			if(gateau !=null)
		{
						
				cboIngredient.setValue(gateau.getIngredient());
				txtNum1.setText(Double.toString(gateau.getNum1()));
				btnModifier.setDisable(false);
				btnEffacer.setDisable(true);
				btnRecommencer.setDisable(true);

		}
			else
			{
				clearFields();
			}
		}
	//Mise à jour d'un objet
	
		public void updateObjet()
		{
			Gateau gateau=(Gateau) tableauQuantite.getSelectionModel().getSelectedItem();
			
			gateau.setQuantite(Double.parseDouble(txtNum1.getText()));
			gateau.setIngredient(cboIngredient.getValue());
			tableauQuantite.refresh();
		}
		
	//Effacer un objet
		@FXML
		public void deleteObjet()
			{
				int selectedIndex = tableauQuantite.getSelectionModel().getSelectedIndex();
				if (selectedIndex >=0)
					{
						tableauQuantite.getItems().remove(selectedIndex);
					}
			}
		

	//SAUVEGARDE DE DONNÉES
				
	//Récupérer le chemin (path) des fichiers si ca existe
	public File getIngredientFilePath()
	{
			
		Preferences prefs = Preferences.userNodeForPackage(Main.class);
		String filePath = prefs.get("filePath", null);
		
		if (filePath !=null)
		{
			return new File(filePath);
		}
			else
			{
				return null;
			}

		}
	//Attribuer un chemin de fichiers
	public void setGateauFilePath(File file)
	{
			Preferences prefs = Preferences.userNodeForPackage(Main.class);
		if (file !=null)
		{
			prefs.put("filePath", file.getPath());
		}
		else
		{
			prefs.remove("filePath");
		}
	}
	//Prendre les donnés de type XML et les convertir en données de type javaFx
	public void loadGateauDataFromFile(File file)
	{
		try {
			JAXBContext context = JAXBContext.newInstance(GateauListeWrapper.class);
			Unmarshaller um = context.createUnmarshaller(); 
			
			GateauListeWrapper wrapper = (GateauListeWrapper) um.unmarshal(file);
			gateauData.clear();
			gateauData.addAll(wrapper.getGateaux());
			setGateauFilePath(file);
		} catch (Exception e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("les données n'ont pas été trouvées");
			alert.setContentText("Les données ne pouvaient pas être trouvées dans le fichier : \n" +file.getPath());
			alert.showAndWait();
		}
	}

	//Prendre les données de type JavaFx et les convertir en type XML
	 public void saveGateauDataToFile(File file) {
		 try {
			 JAXBContext context = JAXBContext.newInstance(GateauListeWrapper.class);
			 Marshaller m = context.createMarshaller();
			 m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		 GateauListeWrapper wrapper = new GateauListeWrapper();
		 wrapper.setGateaux(gateauData);
			 
		 m.marshal(wrapper,file);
		 
	// Sauvegarde dans le registre.
		 setGateauFilePath(file);
			
		 } catch (Exception e) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Erreur");
				alert.setHeaderText("les données n'ont pas été trouvées");
				alert.setContentText("Les données ne pouvaient pas être trouvées dans le fichier : \n" +file.getPath());
				alert.showAndWait();
			}
		}
		 
	//Commencer un nouveau
	 @FXML
	 private void handleNew()
	 {
		 getgateauData().clear();
		 setGateauFilePath(null);

	}

	/*
	 * Le FileChooser permet à l'usager de choisir le fichier à ouvrir.
	 */
	@FXML
	private void handleOpen() {
		FileChooser fileChooser = new FileChooser();
			
		//Permettre un filtre sur l'extension du fichier à chercher
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)","*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
			
		//montrer le dialogue
		File file = fileChooser.showOpenDialog(null);
		
		if (file != null) {
			loadGateauDataFromFile(file);
		}
	}
		
	/*
	 * Sauvegarde le fichier correspondant à l'ingredient actif
	 * S'il n y a pas de fichier, le menu sauvegarder sous va s'afficher
	 */
	@FXML  
	private void handleSave() {
			
		File gateauFile = getIngredientFilePath();
		if (gateauFile != null) {
				saveGateauDataToFile(gateauFile);
				
			} else {
				handleSaveAs();
				}
			
		}
	
	/*
	 * Ouvrir le FileChooser pour trouver le chemin.
	 */
	@FXML
	private void handleSaveAs() {
		FileChooser fileChooser = new FileChooser();
		
		FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter(
				"XML files (*.xml)","*.xml");
		fileChooser.getExtensionFilters().add(extFilter);
		
		// Sauvegarde
		File file = fileChooser.showSaveDialog(null);
		
		if (file != null) {
			// Vérification de l'extension
			if (!file.getPath().endsWith(".xml")) {
				file = new File(file.getPath() + ".xml");
				
			}
			saveGateauDataToFile(file);
			}
		}
	}
