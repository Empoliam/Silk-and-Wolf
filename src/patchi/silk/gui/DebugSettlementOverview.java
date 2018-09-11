package patchi.silk.gui;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import patchi.silk.entities.Settlement;
import patchi.silk.entities.World;

public class DebugSettlementOverview extends TableView<Settlement> {

	static final ArrayList<Settlement> SETTLEMENTS = World.getMainWorld().getSettlementSet();
	
	public DebugSettlementOverview() {
		
		ObservableList<Settlement> settlementList = FXCollections.observableArrayList(SETTLEMENTS);
		
		TableColumn<Settlement, String> nameColumn = new TableColumn<>("Name");
		nameColumn.setMinWidth(100);
		nameColumn.setCellValueFactory(n -> n.getValue().getNameProperty());
		
		TableColumn<Settlement, Number> populationColumn = new TableColumn<>("Pop.");
		populationColumn.setMinWidth(50);
		populationColumn.setCellValueFactory(s -> s.getValue().getCurrentPopulationProperty());
		
		setItems(settlementList);
		getColumns().add(nameColumn);
		getColumns().add(populationColumn);
		setEditable(false);
		
	}
	
}
