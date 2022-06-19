/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.yelp;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import it.polito.tdp.yelp.model.Model;
import it.polito.tdp.yelp.model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="btnCreaGrafo"
    private Button btnCreaGrafo; // Value injected by FXMLLoader

    @FXML // fx:id="btnUtenteSimile"
    private Button btnUtenteSimile; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtX2"
    private TextField txtX2; // Value injected by FXMLLoader

    @FXML // fx:id="cmbAnno"
    private ComboBox<Integer> cmbAnno; // Value injected by FXMLLoader

    @FXML // fx:id="txtN"
    private TextField txtN; // Value injected by FXMLLoader

    @FXML // fx:id="cmbUtente"
    private ComboBox<User> cmbUtente; // Value injected by FXMLLoader

    @FXML // fx:id="txtX1"
    private TextField txtX1; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	int n;
    	try {
    		n = Integer.parseInt(txtN.getText());
		} catch (Exception e) {
			txtResult.setText("Inserisci un numero n intero!");
			return;
		}
    	
    	Integer year = cmbAnno.getValue();
    	if(year == null) {
    		txtResult.setText("Seleziona un anno!");
    		return;
    	}
    	
    	Set<User> users = this.model.creaGrafo(n, year);
    	txtResult.setText("Grafo creato con "+this.model.getGraph().vertexSet().size()+" vertici e "+this.model.getGraph().edgeSet().size()+" archi");
    	cmbUtente.getItems().clear();
    	cmbUtente.getItems().addAll(users);
    }

    @FXML
    void doUtenteSimile(ActionEvent event) {
    	txtResult.clear();
    	User user = cmbUtente.getValue();
    	if(user == null) {
    		txtResult.setText("Seleziona un utente!");
    		return;
    	}
    	
    	Map<User, Integer> m = this.model.getSimilarita(user);
    	txtResult.setText("Utenti più simili a "+user+":\n\n");
    	for (User u: m.keySet()) {
			txtResult.appendText(u+"\t GRADO: "+m.get(u)+"\n");
		}
    }
    
    @FXML
    void doSimula(ActionEvent event) {
    	int x1;
    	int x2;
    	try {
    		x1 = Integer.parseInt(txtX1.getText());
    		x2 = Integer.parseInt(txtX2.getText());
		} catch (Exception e) {
			txtResult.setText("Inserisci due numeri interi!\nNB: il numero di utenti deve essere minore di "+
							this.model.getGraph().vertexSet().size()+" e quello di intervistatori molto minore.");
			return;
		}
    	if(x1 > x2/3) {
    		txtResult.setText("Gli intervistatori devono essere meno di "+x2/3+" per impostazione del programmatore");
    		return;
    	}
    	
    	String s = this.model.simula(x1, x2);
    	
    	txtResult.setText(s);
    }
    

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnUtenteSimile != null : "fx:id=\"btnUtenteSimile\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX2 != null : "fx:id=\"txtX2\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbAnno != null : "fx:id=\"cmbAnno\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtN != null : "fx:id=\"txtN\" was not injected: check your FXML file 'Scene.fxml'.";
        assert cmbUtente != null : "fx:id=\"cmbUtente\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtX1 != null : "fx:id=\"txtX1\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";

    }
    
    public void setModel(Model model) {
    	this.model = model;
    	cmbAnno.getItems().addAll(2005, 2006, 2007, 2008, 2009, 2010, 2011, 2012, 2013);
    }
    
}
