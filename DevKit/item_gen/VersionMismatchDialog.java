package item_gen;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;

import item.Item;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class VersionMismatchDialog extends Dialog<Integer> {

	public VersionMismatchDialog(Stage S, BufferedReader in, ArrayList<Item> itemList) {

		VBox outerContainer = new VBox();
		GridPane innerContainer = new GridPane();

		initOwner(S);
		setTitle("Version Mismatch");

		Text warning = new Text("Warning: Item file version mismatch!\nIdentify the indices of each field, or check the box and provide a default value");
		warning.setTextAlignment(TextAlignment.CENTER);
		
		Text nameText = new Text("Name");
		TextField nameField = new TextField();
		CheckBox nameDefaultCheck = new CheckBox();

		Text typeText = new Text("Type");
		TextField typeField = new TextField();
		CheckBox typeDefaultCheck = new CheckBox();

		innerContainer.add(nameText, 0, 0);
		innerContainer.add(nameField, 1, 0);
		innerContainer.add(nameDefaultCheck, 2, 0);
		innerContainer.add(typeText, 0, 1);
		innerContainer.add(typeField, 1, 1);
		innerContainer.add(typeDefaultCheck, 2, 1);

		innerContainer.setPadding(new Insets(10));
		innerContainer.setHgap(10);
		innerContainer.setVgap(10);

		innerContainer.getColumnConstraints().add(new ColumnConstraints(100));
		innerContainer.getColumnConstraints().add(new ColumnConstraints(200));
		innerContainer.getColumnConstraints().add(new ColumnConstraints(50));

		for(Node N : innerContainer.getChildren()) {
			GridPane.setHalignment(N, HPos.CENTER);
		}

		outerContainer.getChildren().addAll(warning,innerContainer);

		outerContainer.setPadding(new Insets(10));

		getDialogPane().setContent(outerContainer);
		getDialogPane().getButtonTypes().addAll(ButtonType.OK);

		setResultConverter((ButtonType B) -> {
			if (B == ButtonType.OK) {

				String line;
				int idCounter = 0;
				
				try {
					
					line = in.readLine();

					while(line != null) {

						String[] arr = line.split(",");

						int id = Integer.parseInt(arr[0]);
						String name = null;
						int type = 0;
						
						if(nameDefaultCheck.isSelected()) {
							name = nameField.getText();
						} else {
							name = arr[Integer.parseInt(nameField.getText())];
						};
												
						if(typeDefaultCheck.isSelected()) {
							type = Integer.parseInt(typeField.getText());
						} else {
							int index = Integer.parseInt(typeField.getText());
							type = Integer.parseInt(arr[index]);
						}
						
						itemList.add(new Item(id, name, type));

						line = in.readLine();
						
						idCounter++;
					}
					
					return idCounter;

				} catch (IOException e) {

					e.printStackTrace();
				}

			}
			return null;

		});

	}

}
