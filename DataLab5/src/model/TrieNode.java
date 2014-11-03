package model;

import java.util.HashMap;

public class TrieNode {
	private HashMap<String, TrieNode> children;
	private String nextHop;
	
	public TrieNode() {
		children = new HashMap<String, TrieNode>();
	}

	public TrieNode(String nextHop) {
		children = new HashMap<String, TrieNode>();
		this.nextHop = nextHop;
	}

	public String getNextHop() {
		return nextHop;
	}

	public void setNextHop(String nextHop) {
		this.nextHop = nextHop;
	}

	public HashMap<String, TrieNode> getChildren() {
		return children;	
	}

	public TrieNode getChild(String key) {
		return children.get(key);
	}

	public boolean hasChildForKey(String key) {
		return children.get(key) != null;
	}

	public void addChild(String key, TrieNode node) {
		children.put(key, node);
	}

	public void setChildren(HashMap<String, TrieNode> children) {
		this.children = children;
	}
	
	
}