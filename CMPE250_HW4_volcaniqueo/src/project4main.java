import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Scanner;

public class project4main {

	public static void main(String[] args) throws FileNotFoundException{
		
		Scanner in = new Scanner(new File(args[0]));
		PrintStream out = new PrintStream(new File(args[1]));
		
		// index & id = 0 corresponds to source and index & id = 1 corresponds to terminal.
		ArrayList<HashMap<Integer, Integer>> myGraph = new ArrayList<HashMap<Integer, Integer>>();
		myGraph.add(new HashMap<Integer, Integer>()); // will be modified when reading the data of bags.
		myGraph.add(new HashMap<Integer, Integer>()); // will stay as null since it out degree is zero.
		ArrayList<Integer> redTrains = new ArrayList<Integer>();
		ArrayList<Integer> greenTrains = new ArrayList<Integer>();
		ArrayList<Integer> greenReindeers = new ArrayList<Integer>();
		ArrayList<Integer> redReindeers = new ArrayList<Integer>();
		int giveID = 2;
		int gifts = 0;
		
		String myLine = in.nextLine();
		String[] myOrder = myLine.split(" ");
		int numGreenT = Integer.parseInt(myOrder[0]);
		String myLine2 = in.nextLine();
		String[] myOrder2 = myLine2.split(" ");
		for (int i = 0; i < numGreenT; i++) {
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			int capFlow = Integer.parseInt(myOrder2[i]); // capacity
			if (capFlow == 0)
				continue;
			toBeAdded.put(1, capFlow);
			myGraph.add(toBeAdded);
			greenTrains.add(giveID);
			giveID ++;
		}
		
		String myLine3 = in.nextLine();
		String[] myOrder3 = myLine3.split(" ");
		int numRedT = Integer.parseInt(myOrder3[0]);
		String myLine4 = in.nextLine();
		String[] myOrder4 = myLine4.split(" ");
		for (int i = 0; i < numRedT; i++) {
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			int capFlow = Integer.parseInt(myOrder4[i]); // capacity
			if (capFlow == 0)
				continue;
			toBeAdded.put(1, capFlow);
			myGraph.add(toBeAdded);
			redTrains.add(giveID);
			giveID ++;
		}
		
		String myLine5 = in.nextLine();
		String[] myOrder5 = myLine5.split(" ");
		int numGreenR = Integer.parseInt(myOrder5[0]);
		String myLine6 = in.nextLine();
		String[] myOrder6 = myLine6.split(" ");
		for (int i = 0; i < numGreenR; i++) {
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			int capFlow = Integer.parseInt(myOrder6[i]); // capacity
			if (capFlow == 0)
				continue;
			toBeAdded.put(1, capFlow);
			myGraph.add(toBeAdded);
			greenReindeers.add(giveID);
			giveID ++;
		}
		
		String myLine7 = in.nextLine();
		String[] myOrder7 = myLine7.split(" ");
		int numRedR = Integer.parseInt(myOrder7[0]);
		String myLine8 = in.nextLine();
		String[] myOrder8 = myLine8.split(" ");
		for (int i = 0; i < numRedR; i++) {
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			int capFlow = Integer.parseInt(myOrder8[i]); // capacity
			if (capFlow == 0)
				continue;
			toBeAdded.put(1, capFlow);
			myGraph.add(toBeAdded);
			redReindeers.add(giveID);
			giveID ++;
		}
		int lastV = giveID - 1;
		ArrayList<Integer> greenVehicles = new ArrayList<Integer>();
		greenVehicles.addAll(greenTrains);
		greenVehicles.addAll(greenReindeers);
		ArrayList<Integer> redVehicles = new ArrayList<Integer>();
		redVehicles.addAll(redTrains);
		redVehicles.addAll(redReindeers);
		ArrayList<Integer> trains = new ArrayList<Integer>();
		trains.addAll(redTrains);
		trains.addAll(greenTrains);
		ArrayList<Integer> reindeers = new ArrayList<Integer>();
		reindeers.addAll(redReindeers);
		reindeers.addAll(greenReindeers);
		ArrayList<Integer> allVehicles = new ArrayList<Integer>();
		allVehicles.addAll(trains);
		allVehicles.addAll(reindeers);
		
		
		
		// Type = b green region, Type = c red region, Type = d train, Type = e reindeer.
		// The bags will be merged unless they have the constraint "a".
		int bd = 0;
		int be = 0;
		int b = 0;
		int cd = 0;
		int ce = 0;
		int c = 0;
		int d = 0;
		int e = 0;
		String myLine9 = in.nextLine();
		String[] myOrder9 = myLine9.split(" ");
		int numBags = Integer.parseInt(myOrder9[0]);
		if (numBags == 0) {
			//System.out.print(0);
			out.println(0);
			return;
		}
		String myLine10 = in.nextLine();
		String[] myOrder10 = myLine10.split(" ");
		for (int i = 0; i < numBags * 2; i += 2) {
			int capFlow = Integer.parseInt(myOrder10[i + 1]);
			if (capFlow == 0)
				continue;
			gifts += Integer.parseInt(myOrder10[i + 1]);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			boolean typeA = false;
			if (myOrder10[i].contains("a")) {
				typeA = true;
				myGraph.get(0).put(giveID, capFlow);
			}
			if (myOrder10[i].contains("b")) {
				if (myOrder10[i].contains("d")) {
					if (typeA) {
						for (int myID: greenTrains)
							toBeAdded.put(myID, 1);
						myGraph.add(toBeAdded);
						giveID ++;
					}
					else
						bd += capFlow;
				}
				else if (myOrder10[i].contains("e")) {
					if (typeA) {
						for (int myID: greenReindeers)
							toBeAdded.put(myID, 1);
						myGraph.add(toBeAdded);
						giveID ++;
					}
					else
						be += capFlow;
				}
				else {
					if (typeA) {
						for (int myID: greenVehicles)
							toBeAdded.put(myID, 1);
						myGraph.add(toBeAdded);
						giveID ++;
					}
					else
						b += capFlow;
				}
				continue;
			}
			if (myOrder10[i].contains("c")) {
				if (myOrder10[i].contains("d")) {
					if (typeA) {
						for (int myID: redTrains)
							toBeAdded.put(myID, 1);
						myGraph.add(toBeAdded);
						giveID ++;
					}
					else
						cd += capFlow;
				}
				else if (myOrder10[i].contains("e")) {
					if (typeA) {
						for (int myID: redReindeers)
							toBeAdded.put(myID, 1);
						myGraph.add(toBeAdded);
						giveID ++;
					}
					else
						ce += capFlow;
				}
				else {
					if (typeA) {
						for (int myID: redVehicles)
							toBeAdded.put(myID, 1);
						myGraph.add(toBeAdded);
						giveID ++;
					}
					else
						c += capFlow;
				}
				continue;
			}
			if (myOrder10[i].contains("d")) {
				if (typeA) {
					for (int myID: trains)
						toBeAdded.put(myID, 1);
					myGraph.add(toBeAdded);
					giveID ++;
				}
				else
					d += capFlow;
				continue;
			}
			if (myOrder10[i].contains("e")) {
				if (typeA) {
					for (int myID: reindeers)
						toBeAdded.put(myID, 1);
					myGraph.add(toBeAdded);
					giveID ++;
				}
				else
					e += capFlow;
				continue;
			}
			for (int myID: allVehicles)
				toBeAdded.put(myID, 1);
			myGraph.add(toBeAdded);
			giveID ++;
			continue;
		}
		
		if (bd != 0) {
			myGraph.get(0).put(giveID, bd);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: greenTrains)
				toBeAdded.put(myID, bd);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		if (be != 0) {
			myGraph.get(0).put(giveID, be);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: greenReindeers)
				toBeAdded.put(myID, be);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		if (b != 0) {
			myGraph.get(0).put(giveID, b);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: greenVehicles)
				toBeAdded.put(myID, b);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		if (cd != 0) {
			myGraph.get(0).put(giveID, cd);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: redTrains)
				toBeAdded.put(myID, cd);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		if (ce != 0) {
			myGraph.get(0).put(giveID, ce);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: redReindeers)
				toBeAdded.put(myID, ce);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		if (c != 0) {
			myGraph.get(0).put(giveID, c);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: redVehicles)
				toBeAdded.put(myID, c);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		if (d != 0) {
			myGraph.get(0).put(giveID, d);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: trains)
				toBeAdded.put(myID, d);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		if (e != 0) {
			myGraph.get(0).put(giveID, e);
			HashMap<Integer, Integer> toBeAdded = new HashMap<Integer, Integer>();
			for (int myID: reindeers)
				toBeAdded.put(myID, e);
			myGraph.add(toBeAdded);
			giveID ++;
		}
		
		/*
		Code for representation of the graph:
		for (int a = 0; a < myGraph.size(); a ++) {
			for (int i: myGraph.get(a).keySet()) {
				System.out.print(a);
				System.out.print("-");
				System.out.print(i);
				System.out.print("-");
				System.out.print(myGraph.get(a).get(i));
				System.out.print(" ");
			}
			System.out.println();
		}
		*/
		
		PriorityQueue<int[]> excessesWH = new PriorityQueue<int[]>(new MyArrayComparator());
		int[] heightsV = new int[myGraph.size()];
		int[] excessesV = new int[myGraph.size()];
		for (int i = 0; i < myGraph.size(); i++) {  // Push-Relabel Part1 : Initialize flow.
			if (i == 0) {
				heightsV[i] = myGraph.size();
				excessesV[i] = 0;
				continue;
			}
			if (myGraph.get(0).containsKey(i)) {
				int[] h = new int[2];
				h[0] = i;
				h[1] = 2;
				excessesWH.add(h);
				heightsV[i] = 1;
				excessesV[i] = myGraph.get(0).get(i);
				myGraph.get(i).put(0, myGraph.get(0).get(i));
				myGraph.get(0).remove(i);
			}
			else {
				heightsV[i] = 0;
				excessesV[i] = 0;
			}
		}
		
		PushRelabel pushRelabel = new PushRelabel(myGraph, heightsV, excessesV, excessesWH, lastV);
		pushRelabel.sendFlow();
		
		/*
		integer remaining = pushRelabel.excessesV[0];  
		Not applicable right now, would give wrong answer because program ends when there is no edge
		to the sink. There may be some excess flow on the vertices other than source or sink.
		*/
		
		out.println(gifts - pushRelabel.excessesV[1]);
		
		

	}

}
