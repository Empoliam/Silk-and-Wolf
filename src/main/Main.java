package main;

import java.util.ArrayList;
import java.util.List;

import entities.Road;
import entities.Settlement;

import market.Stock;

public class Main {

	public static List<Settlement> SETTLEMENTS = new ArrayList<Settlement>();
	public static List<Road> ROADS = new ArrayList<Road>();
	public static List<Stock> STOCKS = new ArrayList<Stock>();

	public static void main(String[] args) {

		Load.settlements();
		Load.roads();
		Load.stocks();
		
		Stock gold = STOCKS.get(0);

	}
}
