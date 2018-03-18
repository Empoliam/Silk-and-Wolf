package gui;

import java.util.HashMap;

import entities.NPC;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.VBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

// TODO: Auto-generated Javadoc
/**
 * A debug UI element used to display all NPCs and their details.
 */
public class NPCTable extends VBox{

	/** Reference to NPCs HashMap. */
	private static final HashMap<Integer,NPC> NPCS = NPC.NPCS;
	
	/** Table object */
	private TableView<NPC> table; 
	
	/**
	 * Instantiates the NPC Table.
	 */
	@SuppressWarnings("unchecked")
	public NPCTable() 
	{
		
		ObservableList<NPC> listTest = FXCollections.observableArrayList(NPCS.values());

		TableColumn<NPC,Number> idColumn = new TableColumn<>("ID");
		idColumn.setMinWidth(50);
		idColumn.setCellValueFactory(n -> n.getValue().getIdProperty());

		TableColumn<NPC,String> fnameColumn = new TableColumn<>("First Name");
		fnameColumn.setMinWidth(100);
		fnameColumn.setCellValueFactory(n -> n.getValue().getFirstNameProperty());

		TableColumn<NPC,String> lnameColumn = new TableColumn<>("Last Name");
		lnameColumn.setMinWidth(100);
		lnameColumn.setCellValueFactory(n -> n.getValue().getLastNameProperty());

		TableColumn<NPC,String> locationColumn = new TableColumn<>("Location");
		locationColumn.setMinWidth(100);
		locationColumn.setCellValueFactory(n -> n.getValue().locationNameProperty());

		TableColumn<NPC,Number> departureColumn = new TableColumn<>("Departure time");
		departureColumn.setMinWidth(100);
		departureColumn.setCellValueFactory(n -> n.getValue().getDepartureHoursProperty());

		table = new TableView<NPC>();	
		table.setItems(listTest);
		table.getColumns().addAll(idColumn,fnameColumn,lnameColumn,locationColumn,departureColumn);		
		table.setEditable(false);
		
		this.getChildren().add(table);
		
	}
	
	/**
	 * Refresh table values.
	 */
	public void refresh() {
		table.refresh();
	}

}
