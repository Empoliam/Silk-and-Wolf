package item_gen;

import java.util.ArrayList;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import patchi.components.IntegerField;
import patchi.silk.item.Item;

public class ItemCreationDialog extends Dialog<Item>{

	public ItemCreationDialog(Stage S, ArrayList<Item> iref, int currentID) {

		initOwner(S);
		setTitle("Create new item");

		IntegerField idField = new IntegerField(currentID);

		idField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(newValue.equals("")) {
				getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
			} else {

				boolean taken = false;

				for(Item I : iref) {
					if(I.getID() == idField.getInteger()) taken = true;
				}

				if(taken) { 
					getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
				} else {
					getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}

			}
		});

		TextField nameField = new TextField("Item Name");
		IntegerField typeField = new IntegerField(0);

		VBox internal = new VBox();
		internal.getChildren().addAll(idField, nameField, typeField);

		getDialogPane().setContent(internal);
		getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

		setResultConverter((ButtonType B) -> {
			if (B == ButtonType.OK) {
				return new Item(
						idField.getInteger(),
						nameField.getText(),
						typeField.getInteger());
			}
			return null;

		});

	}



}
