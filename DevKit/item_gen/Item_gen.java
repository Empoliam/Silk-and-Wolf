package item_gen;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

import item.Item;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

public class Item_gen extends Application {

	static final int VERSION = 1;
	static int currentID = 0;

	static ArrayList<Item> itemList = new ArrayList<>();

	static HBox loadWindow;
	static Scene loadScene;

	static VBox mainWindow;
	static ListView<Item> itemListView = new ListView<>();
	static Scene mainScene;

	static File inputFile;

	public static void main(String[] args) {		
		launch(args);
	}

	@Override
	public void start(Stage mainStage) throws Exception {
		
		mainStage.setTitle("Item Manager");

		//************************** Load Window ***************************//

		Button chooseFileButton = new Button("Choose file");

		chooseFileButton.setOnAction(e -> {

			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Load item file");
			fileChooser.getExtensionFilters().addAll(
					new ExtensionFilter("Silk and Wolf Data Files", "*.silk"),
					new ExtensionFilter("All Files", "*"));
			fileChooser.setInitialDirectory(new File("."));
			
			inputFile = fileChooser.showOpenDialog(mainStage);
			if(inputFile != null) { 
				try {
					BufferedReader in = new BufferedReader(new FileReader(inputFile));

					String line = in.readLine();

					if(Integer.parseInt(line) == VERSION) {
						
						line = in.readLine();
						
						while(line != null) {
							itemList.add(new Item(line));
							line = in.readLine();
							currentID++;
						}

						in.close();
					}
					
					else {
						System.out.println("Version Mismatch.");
						VersionMismatchDialog mismatch = new VersionMismatchDialog(mainStage, in, itemList);
						mismatch.showAndWait();
						currentID = mismatch.getResult();
					}
					
					launchMainView(mainStage);

				}  catch (IOException e1) {

					System.out.println("Uhh");
					inputFile = fileChooser.showOpenDialog(mainStage);

				}
			}

		});

		Button newFileButton = new Button("New File");	

		newFileButton.setOnAction(e -> {
			launchMainView(mainStage);
		});

		//************************** Main Window ***************************//

		Button newItemButton = new Button("Create new item");

		newItemButton.setOnAction(e -> {

			ItemCreationDialog createDialog = new ItemCreationDialog(mainStage, itemList, currentID);
			Optional<Item> result = createDialog.showAndWait();
			if(result.isPresent()) {
				itemListView.getItems().add(result.get());	
				currentID = result.get().getID()+1;
			}

		});

		Button deleteItemButton = new Button("Delete selected");

		deleteItemButton.setOnAction(e -> {	

			int s = itemListView.getSelectionModel().getSelectedIndex();
			if(s != -1) itemListView.getItems().remove(s);

		});
		
		Button editItemButton = new Button("Edit selected");
		
		editItemButton.setOnAction(e -> {	

			int index = itemListView.getSelectionModel().getSelectedIndex();
			if(index != -1) {
				ItemDialog editDialog = new ItemDialog(mainStage, itemListView.getItems().get(index), itemList);
				Optional<Item> result = editDialog.showAndWait();
				if(result.isPresent()) {
					itemListView.getItems().set(index, result.get());

				}
			};

		});

		Button saveItemsButton = new Button("Save");

		saveItemsButton.setOnAction(e -> {

			try {
				File F = new File("./DevKitOutput/items.silk");
				F.getParentFile().mkdirs();
				BufferedWriter bw = new BufferedWriter(new FileWriter(F));
				bw.append(VERSION + "\n");

				for (Item I : itemList) {
					String S = I.getID() + "," + I.getName() + "," + I.getType();
					bw.append(S);
					bw.newLine();
				}
				bw.close();
			} catch (IOException e1) {
				System.out.println("oof");
				e1.printStackTrace();
			}

		});

		//************************** Scene setup ***************************//

		loadWindow = new HBox();
		loadWindow.setPadding(new Insets(10));
		loadWindow.setSpacing(10);

		loadWindow.getChildren().addAll(
				chooseFileButton,
				new Text("or"),
				newFileButton);

		loadScene = new Scene(loadWindow);


		mainWindow = new VBox();
		mainWindow.setPadding(new Insets(10));
		mainWindow.setSpacing(10);

		mainWindow.getChildren().addAll(
				itemListView,
				newItemButton, 
				deleteItemButton, 
				editItemButton,
				saveItemsButton
				);

		mainScene = new Scene(mainWindow);

		mainStage.setScene(loadScene);
		mainStage.sizeToScene();
		mainStage.show();

	}

	public static void populateList() {

		itemListView.setItems(FXCollections.observableList(itemList));

		itemListView.setCellFactory(new Callback<ListView<Item>, ListCell<Item>>() {

			@Override
			public ListCell<Item> call(ListView<Item> listIn) {

				ListCell<Item> cell = new ListCell<Item>() {
					@Override
					protected void updateItem(Item i, boolean empty) {
						super.updateItem(i, empty);
						if (empty || i == null) {
							setText(null);
						}
						else {
							setText(i.getName());
						};
					}
				};

				return cell;
			}

		});

	}

	public static void launchMainView(Stage S) {
		populateList();
		S.setScene(mainScene);
		S.sizeToScene();
	}

}
