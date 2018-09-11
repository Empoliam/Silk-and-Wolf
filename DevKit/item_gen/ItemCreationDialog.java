package item_gen;

import java.util.ArrayList;

import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import patchi.silk.item.ItemDef;

public class ItemCreationDialog extends Dialog<ItemDef>{

	public ItemCreationDialog(Stage S, ArrayList<ItemDef> iref) {

		initOwner(S);
		setTitle("Create new item");

		TextField idField = new TextField();

		idField.textProperty().addListener((observable, oldValue, newValue) -> {

			if(newValue.equals("")) {
				getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
			} else {

				boolean taken = false;

				for(ItemDef I : iref) {
					if(I.getID().equals(idField.getText())) taken = true;
				}

				if(taken || idField.getText().equals("")) { 
					getDialogPane().lookupButton(ButtonType.OK).setDisable(true);
				} else {
					getDialogPane().lookupButton(ButtonType.OK).setDisable(false);
				}

			}
		});

		TextField nameField = new TextField("Item Name");

		VBox internal = new VBox();
		internal.getChildren().addAll(idField, nameField);

		getDialogPane().setContent(internal);
		getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);

		setResultConverter((ButtonType B) -> {
			if (B == ButtonType.OK) {
				return new ItemDef(
						idField.getText(),
						nameField.getText());
			}
			return null;

		});

	}



}
