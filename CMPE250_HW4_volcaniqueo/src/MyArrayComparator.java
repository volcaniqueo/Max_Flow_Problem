import java.util.Comparator;

public class MyArrayComparator implements Comparator<int[]> {
	
	public int compare(int[] first, int[] second) {
		if (first[1] >= second[1])
			return -1;
		else
			return 1;
	}

}
