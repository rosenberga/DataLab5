package model;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.zip.*;

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
		
		String line;
		try{
			GZIPInputStream gZip = new GZIPInputStream(new FileInputStream(file1));
			BufferedReader br = new BufferedReader(new InputStreamReader(gZip));
			while((line = br.readLine()) != null) {
				String[] parts = line.split("\\|");
				buildTrie(parts[0], parts[1], parts[2]);
//				System.out.println(parts[0]);
//				System.out.println(parts[1]);
//				System.out.println(parts[2]);
//				System.out.println(line);
			}
			
			br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
	}
	
	public void buildTrie(String s, String s2, String s3){
		String prefix = s;
		int[] as = getAS(s2);
		String nextHop = s3;
		
		// TODO Begin building the trie here
	}
	
	public int[] getAS(String s){
		String[] temp = s.split(" ");
		int[] result = new int[temp.length];
		
		for(int i = 0; i < temp.length; i++)
			result[i] = Integer.parseInt(temp[i]);
		return result;
	}
}
