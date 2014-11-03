package model;

import java.util.HashMap;

public class TrieNode {
	private HashMap<String, TrieNode> children;
	private String nextHop;
	private TrieNode parent;
	
	public TrieNode() {
		children = new HashMap<String, TrieNode>();
		parent = null;
	}
	
	public TrieNode(TrieNode p) {
		parent = p;
		children = new HashMap<String, TrieNode>();
		this.nextHop = null;
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

	public TrieNode getParent() {
		return parent;
	}

	public void setParent(TrieNode parent) {
		this.parent = parent;
	}
}