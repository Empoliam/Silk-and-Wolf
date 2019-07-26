package itemGen;

import java.util.ArrayList;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import patchi.silk.item.ItemDef;

public class ItemDialog extends Dialog<ItemDef> {

	public ItemDialog(Stage S, ItemDef inputItem, ArrayList<ItemDef> iref) {

		String originalID = inputItem.getID();
		
		GridPane container = new GridPane();

		initOwner(S);
		setTitle("Editing " + inputItem.getName());

		Text idText = new Text("ID");
		TextField idField = new TextField(inputItem.getID());

		idField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(newValue.equals("")) {
				getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
			} else {
				
				boolean taken = false;

				for(ItemDef I : iref) {
					if(I.getID().equals(idField.getText()) && !(originalID.equals(I.getID()))) {
						taken = true;
					}
				}

				if(taken) { 
					getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
				} else {
					getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}

			}
			
		});

		Text nameText = new Text("Name");
		TextField nameField = new TextField(inputItem.getName());

		container.add(idText, 0, 0);
		container.add(idField, 1, 0);
		container.add(nameText, 0, 1);
		container.add(nameField, 1, 1);

		for(Node N : container.getChildren()) {
			GridPane.setHalignment(N, HPos.CENTER);
		}

		container.setPadding(new Insets(10));
		container.setVgap(10);
		container.setHgap(10);

		getDialogPane().setContent(container);
		getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL, ButtonType.OK);

		setResultConverter((ButtonType B) -> {

			if(B == ButtonType.OK) {

				ItemDef I = new ItemDef(
						idField.getText(),
						nameField.getText()
						);

				return I;

			}

			return null;
		});

	}

}
