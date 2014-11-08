package model;

import java.util.HashMap;
/**
 * PrefixTree Trie Node
 * 
 * @author Adam Rosenberg
 * @author Conrad Geisel
 * @author Nick Paquette
 *
 */
public class TrieNode {
	private HashMap<String, TrieNode> children;
	private String nextHop = "";
	private TrieNode parent;
	private int strides;
	private int prefix;
	private int asPath;
	
	public TrieNode(int strides) {
		children = new HashMap<String, TrieNode>();
		parent = null;
		this.strides = strides;
	}
	
	public TrieNode(TrieNode p, int strides) {
		parent = p;
		children = new HashMap<String, TrieNode>();
		this.nextHop = null;
		this.strides = strides;
	}
	
	public TrieNode insertNode(String key){
		if(children.containsKey(key)){
			return children.get(key);
		} else {
			TrieNode p = new TrieNode(strides);
			children.put(key, p);
			return p;
		}
	}

	public void addNextHop(int prefix, int asPath, String nextHop){
		if(containsNextHop() && prefix <= this.prefix && !(this.prefix == prefix && asPath < this.asPath)){
			return;
		}
		
		this.prefix = prefix;
		this.asPath = asPath;
		this.nextHop = nextHop;
	}
	
	public boolean containsNextHop(){
		if(nextHop.equals(""))
			return false;
		else
			return true;
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