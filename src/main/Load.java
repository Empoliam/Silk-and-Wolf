package main;

import static main.Main.ROADS;
import static main.Main.SETTLEMENTS;
import static main.Main.STOCKS;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import entities.Road;
import entities.Settlement;

import market.Stock;

public class Load {
	
	static void settlements() {

		try {
			
			BufferedReader br = new BufferedReader(new FileReader("resources/settlements.csv"));
			String line = br.readLine();

			while (line != null) {

				SETTLEMENTS.add(new Settlement(line.split(",")));
				line = br.readLine();
			}

			br.close();
		} 
		catch (FileNotFoundException e) {}
		catch (IOException e) {}
	}

	static void roads() {
		
		try {
		
			BufferedReader br = new BufferedReader(new FileReader("resources/roads.csv"));
			String line = br.readLine();
			
			while(line != null) {
				
				ROADS.add(new Road(line.split(",")));
				line = br.readLine();
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}
	
	static void stocks() {
		
		try {
		
			BufferedReader br = new BufferedReader(new FileReader("resources/stocks.csv"));
			String line = br.readLine();
			
			while(line != null) {
				
				STOCKS.add(new Stock(line.split(",")));
				line = br.readLine();
			}
			
			br.close();
		}
		catch(FileNotFoundException e){}
		catch(IOException e){}
	}

}
