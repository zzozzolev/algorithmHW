import java.io.FileReader;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.util.HashSet;
import java.util.Arrays;
import java.util.ArrayList;

public class Runner {
	
	public static void main(String[] args) throws NumberFormatException, IOException {
		String inputPath = "/Users/poza/jam/2019_2/algorithm_analysis/input3.txt";
		FileReader fileReader = new FileReader(inputPath);
		BufferedReader bufferReader = new BufferedReader(fileReader);
		
		int nIter = Integer.parseInt(bufferReader.readLine().trim());
		String[] outputs = new String[nIter];
		
		for(int i = 0; i<nIter; i++) {
			String[] nHoleNumSplited = bufferReader.readLine().split(" ");
			int n = Integer.parseInt(nHoleNumSplited[0].trim());
			int holeNum = Integer.parseInt(nHoleNumSplited[1].trim());
			String holeLine = bufferReader.readLine().trim();
			
			ArrayList<int[]> holeCoords = getHoleCoords(holeLine, holeNum);
			HashSet<ArrayList<Integer>> uniqueRemoved = new HashSet<ArrayList<Integer>>();

			for(int[] holeCoord: holeCoords) {
				int conDim = getConDim(holeCoord, n);

				if (conDim == -1) {
					System.out.println("invalid conDim "+conDim);
					break;
				}

				ArrayList<int[]> removedCoords = getRemovedCoords(holeCoord, n, conDim);

				for(int[] coord: removedCoords) {
					ArrayList<Integer> convertedCoord = new ArrayList<Integer>();
					for(int d: coord)
						convertedCoord.add(d);
					uniqueRemoved.add(convertedCoord);
				}

			}
			int totalNum = n * n * n;
			int result = totalNum - uniqueRemoved.size();
			
			outputs[i] = String.format("#%d %d", i+1, result);
		}
		bufferReader.close();
		writeOutput(outputs);
	}	
	public static ArrayList<int[]> getHoleCoords(String line, int holeNum){
		int nDim = 3;
		
		// split line
		String[] splited = line.split(" ");
		
		// convert str to int
		int[] Converted = new int[splited.length];
		for(int i=0; i<splited.length; i++) {
			Converted[i] = Integer.parseInt(splited[i]);
		}
		
		// group coords
		ArrayList<int[]> holeCoords = new ArrayList<int[]>();
		for(int i = 0; i < splited.length; i += nDim) {
			int[] coord = Arrays.copyOfRange(Converted, i, i+nDim);
			
			holeCoords.add(coord);
			
		}
		
		if (holeCoords.size() != holeNum)
			System.out.println(String.format("holeCoords.size() %d != %d", holeCoords.size(), holeNum));
		
		return holeCoords;
		
	}
	
	public static int getConDim(int[] holeCoord, int n) {
		int rowDim = 0;
		int colDim = 1;
		int levDim = 2;
		
		int rowConIdx = n-1;
		int colLevConIdx = 0;
		
		int conDim = -1;
		if (holeCoord[rowDim] == rowConIdx) {
			conDim = rowDim;
		}
		else if(holeCoord[colDim] == colLevConIdx) {
			conDim = colDim;
		}
		else if(holeCoord[levDim] == colLevConIdx) {
			conDim = levDim;
		}
		
		return conDim;
	}
	
	public static ArrayList<int[]> getRemovedCoords(int[] holeCoord, int n, int dim){
		int rowDim = 0;
		
		ArrayList<int[]> removedCoords = new ArrayList<int[]>();
		
		for(int i=0; i<n; i++) {
			int[] copied = Arrays.copyOf(holeCoord, holeCoord.length);
			
			if (dim == rowDim)
				copied[dim] -= i;
			else
				copied[dim] += i;
			
			if ((copied[dim] < 0) || (copied[dim] > n-1))
				System.out.println("invalid copied"+copied[dim]);
			
			removedCoords.add(copied);
		}
		
		return removedCoords;
	}
	 
	public static void writeOutput(String[] outputs) throws IOException {
		String path = "/Users/poza/jam/2019_2/algorithm_analysis/2016110002.txt";
		File file = new File(path);
		BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
		
		for(String output: outputs) {
			bufferedWriter.write(output);
			bufferedWriter.newLine();
		}
		
		bufferedWriter.close();
	}
}



