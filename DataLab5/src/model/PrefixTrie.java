package model;

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
	
	public PrefixTrie(int stride) {
		this.stride = stride;
		root = new TrieNode();
	}
	
	public TrieNode getRoot(){
		return root;
	}

	public void setRoot(TrieNode root) {
		this.root = root;
	}
	
	public void buildTrie(ArrayList<String> nodes) {
		if(stride == 1) {
			build1(nodes);
		} else if (stride == 2) {
			build2(nodes);
		} else if (stride == 3) {
			build3(nodes);
		}
	}
	
	public void getNextHop(String line) {
		System.out.print(line+ "\t");
		
		if(stride == 1) {
			find1(line);
		} else if(stride == 2) {
			find2(line);
		} else if(stride == 3) {
			find3(line);
		}
	}
	
	private void build1(ArrayList<String> nodes) {
		for (int i = 0; i < nodes.size(); i++) {
			String str = nodes.get(i);
			String[] parts = str.split("\\|");
			
			int[] prefix = getPrefixAsInt(parts[0]);
			int length = getPrefixLength(parts[0]);
			
			TrieNode curr = this.getRoot();
			
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
				
				int bit = ((pre >>> shift) & 1);
				
				if(curr.getChildren().containsKey(""+bit)) {
					curr = curr.getChild(""+bit);
				} else {
					curr.addChild(""+bit, new TrieNode(curr));
					curr = curr.getChild(""+bit);
				}
				
				if(j + 1 == length) {
					curr.setNextHop(parts[2]);
				}
			}
		}
	}
	
	private void build2(ArrayList<String> nodes) {
		
	}
	
	private void build3(ArrayList<String> nodes) {
	
	}
	
	private void find1(String line) {
		int[] ip = getIPAsInt(line);
		
		TrieNode curr = this.getRoot();
		
		for (int j = 0; j < 32; j++) {
			
			int pre;
			int shift = 7 - (j % 8);
			
			if (j >= 0 && j < 8) {
				pre = ip[0];
			} else if (j >=8 && j < 16) {
				pre = ip[1];
			} else if (j >= 16 && j < 24) {
				pre = ip[2];
			} else {
				pre = ip[3];
			}
			
			int bit = ((pre >>> shift) & 1);
			
			if(curr.getChildren().containsKey(""+bit)) {
				curr = curr.getChild(""+bit);
			} else {
				
				while(curr.getNextHop() == null) {
					curr = curr.getParent();
					if (curr == null) {
						System.out.println("NoMatch");
						return;
					}
				}
				
				System.out.println(curr.getNextHop());
				return;
			}
		}
	}
	
	private void find2(String line) {
		
	}
	
	private void find3(String line) {
		
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
