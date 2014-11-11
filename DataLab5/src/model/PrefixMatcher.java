package model;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

/**
 * PrefixMatcher
 * 
 * @author Adam Rosenberg
 * @author Conrad Geisel
 * @author Nick Paquette
 *
 */
public class PrefixMatcher {
	private String file1;
	private String file2;
	private int strides;
	
	public static void main(String[] args) {
		
		new PrefixMatcher(args[0], args[1], args[2]);
	}
	
	public PrefixMatcher(String file1, String file2, String length) {
		this.file1 = file1;
		this.file2 = file2;
		this.strides = Integer.parseInt(length);
		
		if(this.strides <= 0 || this.strides > 3){
			System.out.println("Stride length must be 1, 2, or 3");
			System.exit(1);
		}
		// read the first file and build a list to use to build the trie
		ArrayList<String> fin = readFirstFile(this.file1);
		
		if (fin == null) {
			System.out.println("An error has occured when parsing the router file");
			return;
		}
		
		// build the trie
		PrefixTrie trie = new PrefixTrie(this.strides);
		trie.buildTrie(fin);
		
		// Lookup each IP in sampleips.txt, return next hop for each one
		
		readSecondFile(this.file2, trie);
		
		System.out.println("\nAverage Lookup Time (in nanoseconds): " +trie.getAvgLookupTime());
		System.out.println("Trie memory usage (in bytes): " +trie.getMemUsage());
		
	}
	
	private ArrayList<String> readFirstFile(String file) {
		String line;
		try{
			GZIPInputStream gZip = new GZIPInputStream(new FileInputStream(file));
			BufferedReader br = new BufferedReader(new InputStreamReader(gZip));
			
			ArrayList<String> current = new ArrayList<String>();
			ArrayList<String> fin = new ArrayList<String>();
			String cS = "";
			while((line = br.readLine()) != null) {
				String[] parts = line.split("\\|");
				if (current.size() == 0) {
					current.add(line);
					cS = parts[0];
				} else {
					if (parts[0].equals(cS)) {
						current.add(line);
					} else {
						fin.add(findShortestAS(current));
						current.clear();
						current.add(line);
						cS = parts[0];
					}
				}
			}
			
			br.close();
			return fin;
		} catch (IOException e){
			e.printStackTrace();
			return null;
		}
	}
	
	private String findShortestAS(ArrayList<String> current) {
		int count = 999;
		String ret = "";
		
		for (int i = 0; i < current.size(); i++) {
			String[] parts = current.get(i).split("\\|");
			String[] as = parts[1].split(" ");
			int l = as.length;
			if (l < count) {
				count = l;
				ret = current.get(i);
			}
		}
		
		return ret;
	}
	
	
	private void readSecondFile(String file, PrefixTrie trie){
		try{
			String line, nextHop;
			BufferedReader br = new BufferedReader(new FileReader(file));
			while((line = br.readLine()) != null){
				System.out.print(line + '\t');
				nextHop = trie.getNextHop(line);
				if(nextHop == null)
					System.out.println("No Match");
				else
					System.out.println(nextHop);
				
			}
			br.close();
			
		} catch (IOException e){
			e.printStackTrace();
		}	
	}
}
