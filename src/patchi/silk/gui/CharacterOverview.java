package patchi.silk.gui;

import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import patchi.silk.entities.Character;
/**
 * A debug UI element used to display all characterss and some details.
 */
public class CharacterOverview extends VBox{

	protected TableView<Character> charTable;
	protected CharInfoWindow charInfoWindow;
	protected VBox mainGroup;

	/**
	 * Instantiates the Character Table.
	 */
	@SuppressWarnings("unchecked")
	public CharacterOverview(List<Character> inputList) 
	{

		charTable = new TableView<>();
		ObservableList<Character> charlist = FXCollections.observableArrayList(inputList);

		TableColumn<Character,Number> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(50);
		idColumn.setCellValueFactory(n -> n.getValue().getIdProperty());

		TableColumn<Character,String> fnameColumn = new TableColumn<>("First Name");
		fnameColumn.setMinWidth(100);
		fnameColumn.setCellValueFactory(n -> n.getValue().getFirstNameProperty());

		TableColumn<Character,String> lnameColumn = new TableColumn<>("Last Name");
		lnameColumn.setMinWidth(100);
		lnameColumn.setCellValueFactory(n -> n.getValue().getLastNameProperty());

		TableColumn<Character,String> locationColumn = new TableColumn<>("Location");
		locationColumn.setMinWidth(100);
		locationColumn.setCellValueFactory(n -> n.getValue().locationNameProperty());

		TableColumn<Character,Number> departureColumn = new TableColumn<>("Departure time");
		departureColumn.setMinWidth(100);
		departureColumn.setCellValueFactory(n -> n.getValue().getDepartureHoursProperty());

		TableColumn<Character,Boolean> travellingColumn = new TableColumn<>("Travelling");
		travellingColumn.setMinWidth(100);
		travellingColumn.setCellValueFactory(n -> n.getValue().getTravellingProperty());

		TableColumn<Character, Number> remainingDistanceColumn = new TableColumn<>("Remaining Distance");
		remainingDistanceColumn.setMinWidth(100);
		remainingDistanceColumn.setCellValueFactory(n -> n.getValue().getRemainingDistanceProperty());

		charTable.setItems(charlist);
		charTable.getColumns().addAll(idColumn,fnameColumn,lnameColumn,locationColumn,departureColumn,travellingColumn,remainingDistanceColumn);		
		charTable.setEditable(false);

		Button loadSelected = new Button("View selected");
		loadSelected.setOnAction(e -> {
			charInfoWindow = new CharInfoWindow(charTable.getSelectionModel().getSelectedItem());
			getChildren().setAll(charInfoWindow);
		});

		mainGroup = new VBox();
		mainGroup.getChildren().addAll(charTable, loadSelected);
		getChildren().addAll(mainGroup);

	}

	public void refresh() {
		charTable.refresh();
		if(charInfoWindow != null) charInfoWindow.refresh();
	}

	private class CharInfoWindow extends VBox {

		private Character C;
		
		private Label name;
		private Label location;
		
		protected CharInfoWindow(Character C) {

			this.C = C;
			name = new Label(C.getFirstName() + " " + C.getLastName());
			if(C.getTravelling()) { 
				location = new Label("On " + C.locationName() + " travelling to " + C.getDestination().getName());
			} else {
				location = new Label(C.locationName());
			}
			
			Button backButton = new Button("Back");
			backButton.setOnAction(e -> {
				CharacterOverview.this.getChildren().setAll(mainGroup);
				CharacterOverview.this.charInfoWindow = null;
			});
			
			getChildren().addAll(name, location, backButton);
			setPadding(new Insets(10));
			
		}
		
		protected void refresh() {
			if(C.getTravelling()) { 
				location.setText("On " + C.locationName() + " travelling to " + C.getDestination().getName());
			} else {
				location.setText(C.locationName());
			}
		}

	}		

}
