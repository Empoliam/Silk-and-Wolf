package item_gen;

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

import patchi.components.IntegerField;
import patchi.silk.item.Item;

public class ItemDialog extends Dialog<Item> {

	public ItemDialog(Stage S, Item inputItem, ArrayList<Item> iref) {

		int originalID = inputItem.getID();
		
		GridPane container = new GridPane();

		initOwner(S);
		setTitle("Editing " + inputItem.getName());

		Text idText = new Text("ID");
		IntegerField idField = new IntegerField(inputItem.getID());

		idField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(newValue.equals("")) {
				getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
			} else {
				
				boolean taken = false;

				for(Item I : iref) {
					if(I.getID() == idField.getInteger() && !(originalID == I.getID())) taken = true;
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

		Text typeText = new Text("Type");
		IntegerField typeField = new IntegerField(inputItem.getType());

		container.add(idText, 0, 0);
		container.add(idField, 1, 0);
		container.add(nameText, 0, 1);
		container.add(nameField, 1, 1);
		container.add(typeText, 0, 2);
		container.add(typeField, 1, 2);

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

				Item I = new Item(
						idField.getInteger(),
						nameField.getText(),
						typeField.getInteger()
						);

				return I;

			}

			return null;
		});

	}

}
