package model;

import java.util.Iterator;
import java.util.Map;

public class PrefixTrie {
	private TrieNode root;
	private int strides;
	private TrieNode head;
	private String longestPrefixMatch; // may want to switch to int?
	
	public PrefixTrie(int strides){
		root = new TrieNode("-1");
		this.strides = strides;
		root = createChildrenForNode(root);
		head = root;
	}
	
	public TrieNode getRoot(){
		return root;
	}
	
	public String findNextHopRouter(String ip){
		String bit = "";
		
		if(ip.length() >= strides){
			bit = ip.substring(0, strides);
		} else {
			bit = ip;
		}
		
		if (ip.equals("")) {
			setLongestPrefixMatch();
			return longestPrefixMatch;
		}
		if (!root.hasChildForKey(bit)) {
			setLongestPrefixMatch();
			root = head;
		} else {
			setLongestPrefixMatch();
			root = root.getChild(bit);
			if (ip.length() == strides) {
				setLongestPrefixMatch();
				root = head;
			} else {
				return findNextHopRouter(ip.substring(strides));
			}
		}
		String rtn = longestPrefixMatch;
		longestPrefixMatch = null;
		return rtn == null ? "No Prefix found" : rtn;
	}
	
	private void setLongestPrefixMatch() {
		if (!root.getNextHop().equals("-1")) {
			longestPrefixMatch = root.getNextHop();
		}
	}

	public void insert(String ip, String nextHop) {
		String bit = "";
		String binarySub = "";
		if (ip.length() == 0) {
			root.setNextHop(nextHop);
			return;
		}
		if (ip.length() >= strides) {
			bit = ip.substring(0, strides);
			binarySub = ip.substring(strides);
		} else {
			if (root.getChildren().size() == 0) {
				createChildrenForNode(root);
			}
			insertNaughtyBit(ip, nextHop);
			root = head;
			return;
		}
		if (root.getChildren().size() == 0) {
			createChildrenForNode(root);
		}
		root = root.getChild(bit);
		if (ip.length() == strides) {
			root.setNextHop(nextHop);
			root = head;
		} else {
			insert(binarySub, nextHop);
		}
	}

	private TrieNode createChildrenForNode(TrieNode node) {
		String[] connections;
		if (strides == 2) {
			connections = new String[] { "00", "01", "10", "11" };
			return createChildrenWithConnection(connections, node);
		} else if (strides == 3) {
			connections = new String[] { "000", "001", "010", "011", "100",
					"101", "110", "111" };
			return createChildrenWithConnection(connections, node);
		} else {
			connections = new String[] { "0", "1" };
			return createChildrenWithConnection(connections, node);
		}
	}

	private TrieNode createChildrenWithConnection(String[] connections,
			TrieNode node) {
		for (String connection : connections) {
			TrieNode newChild = new TrieNode("-1");
			node.addChild(connection, newChild);
		}
		return node;
	}

	public void insertNaughtyBit(String toMatch, String nextHop) {
		Iterator it = root.getChildren().entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pairs = (Map.Entry) it.next();
			String key = pairs.getKey().toString();
			if (key.startsWith(toMatch) && root.getChild(key).getNextHop().equals("-1")) {
				root.getChild(key).setNextHop(nextHop);
			}
		}
	}

	public void setStrides(int strides) {
		this.strides = strides;
	}

	public TrieNode getHead() {
		return head;
	}

	public void setHead(TrieNode head) {
		this.head = head;
	}

	public String getLongestPrefixMatch() {
		return longestPrefixMatch;
	}

	public void setLongestPrefixMatch(String longestPrefixMatch) {
		this.longestPrefixMatch = longestPrefixMatch;
	}

	public int getStrides() {
		return strides;
	}

	public void setRoot(TrieNode root) {
		this.root = root;
	}
	
}
