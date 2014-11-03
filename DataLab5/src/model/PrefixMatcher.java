package model;

import java.io.*;
import java.util.*;
import java.util.zip.GZIPInputStream;

public class PrefixMatcher {
	private String file1;
	private String file2;
	private int strides;
	
	public static void main(String[] args) {
		
		new PrefixMatcher(args[0], args[1], args[2]);
	}
	
	public PrefixMatcher(String file1, String file2, String length) {
		this.file1 = file2;
		this.file2 = file2;
		strides = Integer.parseInt(length);
		
		ArrayList<String> fin = readFirstFile(this.file1);
		
		if (fin == null) {
			System.out.println("An error has occured when parsing the router file");
			return;
		}
		
		PrefixTrie trie = buildTrie(fin);
		
		// Lookup each ip in sampleips.txt, return next hop for each one
		
		try{
			String line;
			BufferedReader br = new BufferedReader(new FileReader(file2));
			while((line = br.readLine()) != null){
				trie.findNextHopRouter(line);
			}
			
		} catch (IOException e){
			e.printStackTrace();
			return;
		}
		
	}
	
	private ArrayList<String> readFirstFile(String file) {
		String line;
		try{
			GZIPInputStream gZip = new GZIPInputStream(new FileInputStream(file1));
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
	
	private PrefixTrie buildTrie(ArrayList<String> current) {
		PrefixTrie trie = new PrefixTrie(strides);
		trie.setRoot(new TrieNode());
		
		for (int i = 0; i < current.size(); i++) {
			String str = current.get(i);
			String[] parts = str.split("\\|");
			
			int[] prefix = getPrefixAsInt(parts[0]);
			int length = getPrefixLength(parts[0]);
			
			TrieNode curr = trie.getRoot();
			
			for (int j = 0; j < length; j++) {
				
				int pre;
				int shift = 7 - (j % 8);
				
				if (j >= 0 && j < 8) {
					pre = prefix[0];
				} else if (j >=8 && j < 16) {
					pre = prefix[1];
				} else if (j >= 16 && j < 24) {
					pre = prefix[2];
				} else {
					pre = prefix[3];
				}
				
				int bit = ((pre >>> shift) << 7) >>> 7;
				
				if(curr.getChildren().containsKey(""+bit)) {
					curr = curr.getChild(""+bit);
				} else {
					curr.addChild(""+bit, new TrieNode());
					curr = curr.getChild(""+bit);
				}
				
				if(j + 1 == length) {
					curr.setNextHop(parts[2]);
				}
			}
		}
		
		return trie;
	}
	
	private int getPrefixLength(String s){
		String[] temp = s.split("\\/");
		int result = Integer.parseInt(temp[1]);
		return result;
	}
	
	private int[] getPrefixAsInt(String s){
		String[] parts = s.split("\\/");
		String[] temp = parts[0].split("\\.");
		int[] result = new int[4];
		for(int i = 0; i < result.length; i++)
			result[i] = Integer.parseInt(temp[i]);
		return result;
	}
	
	private int[] getIPAsInt(String s){
		String[] temp = s.split("\\.");
		int[] result = new int[temp.length];
		for(int i = 0; i < temp.length; i++)
			result[i] = Integer.parseInt(temp[i]);
		return result;
	}
}
