import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.PriorityQueue;

public class PushRelabel {
	public ArrayList<HashMap<Integer, Integer>> graph;
	public int[] heightsV, excessesV;
	public PriorityQueue<int[]> excessesWH;
	public boolean check = false;
	public int lastV;
	public int brkcond = 0;
	public int[] relabelled;
	public boolean toStop = false;
	
	public PushRelabel(ArrayList<HashMap<Integer, Integer>> graph, int[] heightsV, int[] excessesV, PriorityQueue<int[]> excessesWH, int lastV) {
		this.graph = graph;
		this.heightsV = heightsV;
		this.excessesV = excessesV;
		this.excessesWH = excessesWH;
		this.lastV = lastV;
		this.relabelled = new int[lastV - 1];
		for (int i = 0; i < lastV - 1; i++) 
			this.relabelled[i] = 0;
	}
	
	public void push(int i, int a) {
		int capIA = this.graph.get(i).get(a);
		int minimum = Math.min(this.excessesV[i], capIA);
		excessesV[i] -= minimum;
		int firstEA = excessesV[a];
		excessesV[a] += minimum;
		if (capIA - minimum == 0)
			//this.graph.get(i).remove(a);
			this.check = true;
		else
			this.graph.get(i).replace(a, capIA - minimum);
		if (this.graph.get(a).containsKey(i))
			this.graph.get(a).replace(i, this.graph.get(a).get(i) + minimum);
		else
			this.graph.get(a).put(i, minimum);
		if (firstEA == 0 && a > 1) {
			int[] beAdded = new int[2];
			beAdded[0] = a;
			beAdded[1] = this.heightsV[a];
			excessesWH.add(beAdded);
		}
	}
	
	public void sendFlow() {
		while (!(this.excessesWH.isEmpty())) {
			if (this.toStop)
				break;
			int process = this.excessesWH.poll()[0];
			while (this.excessesV[process] > 0) {
				int min = Integer.MAX_VALUE;
				boolean brk = false;
				for (Iterator<HashMap.Entry<Integer, Integer>> iterator = this.graph.get(process).entrySet().iterator(); iterator.hasNext();) {  // int u: this.graph.get(process).keySet()
					int u = iterator.next().getKey();
					if (this.heightsV[process] > this.heightsV[u]) {
						this.push(process, u);
						if (check) {
							iterator.remove();
							this.check = false;
						}
						if (this.excessesV[process] == 0) {
							brk = true;
							break;
						}
					}
					else {
						if (this.heightsV[u] < min) {
							min = this.heightsV[u];
							if (min == 0)
								break;
						}
					}
				}
				if (brk)
					break;
				if (min != Integer.MAX_VALUE) {
					this.heightsV[process] += (min + 1);
					if (process <= lastV && this.heightsV[process] > 1) {
						if (this.relabelled[process - 2] == 0) {
							this.relabelled[process - 2] = 1;
							brkcond++;
							if (brkcond == lastV - 1) {
								this.toStop = true;
								break;
							}
						}
					}
				}
			}
		}
	}
	
	
}
