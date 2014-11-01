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
	private int prefixLength;
	
	public static void main(String[] args) {
		new PrefixMatcher(args[0], args[1], args[2]);
	}
	
	public PrefixMatcher(String file1, String file2, String length) {
		this.file1 = file2;
		this.file2 = file2;
		prefixLength = Integer.parseInt(length);
		
		String line;
		try{
			GZIPInputStream gZip = new GZIPInputStream(new FileInputStream(file1));
			BufferedReader br = new BufferedReader(new InputStreamReader(gZip));
			while((line = br.readLine()) != null) {
				System.out.println(line);
			}
			
			br.close();
		} catch (IOException e){
			e.printStackTrace();
		}
//		String line;
//		String[] parts = {};
//		int i = 0;
//		try {
//			BufferedReader br = new BufferedReader(new FileReader(file1));
//			while ((line = br.readLine()) != null) {
//				line = br.readLine();
//				parts[i] = line;
//				System.out.println(parts[i]);
//				i++;
//			}
//			line = br.readLine();
//			br.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
	}
}
