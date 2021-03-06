package model;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;

/**
 * PrefixTrie
 * 
 * @author Adam Rosenberg
 * @author Conrad Geisel
 * @author Nick Paquette
 *
 */
public class PrefixTrie {
	private TrieNode root;
	private int stride;
	private int bitMask;
	private long startTime;
	private long endTime;
	private long runningTimeTotal = 0;
	private long lookupCount = 0;
	private long mem = 0;
	
	public PrefixTrie(int stride) {
		this.stride = stride;
		this.bitMask = (1 << stride) - 1;
		root = new TrieNode(stride);
	}
	
	public TrieNode getRoot(){
		return root;
	}

	public void setRoot(TrieNode root) {
		this.root = root;
	}
	
	public void buildTrie(ArrayList<String> nodes) {
		Runtime runTime = Runtime.getRuntime();
		
		for(int i = 0; i < nodes.size(); i++){
			String str = nodes.get(i);
			String[] parts = str.split("\\|");
		
			// Get Prefix and Prefix Length as ints
			String[] prefixParts = parts[0].split("\\/");
			int prefixLength = Integer.parseInt(prefixParts[1]);
			int prefix = getIPAsInt(prefixParts[0]);
			
			TrieNode p = root;
			
			int n, j = 0;
			for(j = 1; j <= prefixLength/stride; j++){
				n = prefix >>> (32 - (stride * j));
				p = p.insertNode(""+ (n & bitMask));
			}
			
			// if multiple branch matches at node
			if(prefixLength % stride > 0) {
				n = prefix >>> (32 - (stride * i));
				int mask1 = ((1 << (prefixLength % stride)) - 1) << (stride - (prefixLength % stride));
				int mask2 = (1 << (stride - (prefixLength % stride))) - 1;
				
				int k = (n & mask1);
				TrieNode node = null;
				while (k <= (n & mask1 | mask2)){
					node = p.insertNode(""+ k++);
					node.addNextHop(prefixLength, parts[1].split(" ").length, parts[2]);
				}
			} else {
				p.addNextHop(prefixLength, parts[1].split(" ").length, parts[2]);
			}

		}
		
		runTime.gc();
		mem = runTime.totalMemory() - runTime.freeMemory();
	}
	
	public String getNextHop(String s){
		TrieNode p = root;
		int address = getIPAsInt(s);
		String result = null;
		
		lookupCount++;
		startTime = System.nanoTime();
		for(int i = 1; i <= 32/stride; i++){
			int temp = address >>> (32 - (stride * i));
			p = p.getChild(""+ (temp & bitMask));
			if(p == null)
				break;
			else if(p.containsNextHop())
				result = p.getNextHop();
		}
		endTime = System.nanoTime();
		
		runningTimeTotal += (endTime-startTime);
		return result;
	}
	
	private int getIPAsInt(String s){
		InetAddress a;
		try{
			a = InetAddress.getByName(s);
		} catch (UnknownHostException e){
			e.printStackTrace();
			return 0;
		}
		
		byte[] b = a.getAddress();
		int result = 0;
		
		for(int i = 1; i <= b.length; i++){
			result |= ((int) b[i-1] & 0xFF) << (32 - (8 * i));
		}
		return result;
	}
	
	public long getAvgLookupTime(){
		long result;
		result = runningTimeTotal / lookupCount;
		return result;
	}
	
	public long getMemUsage(){
		return mem;
	}
}
