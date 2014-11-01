package model;

public class TrieNode {
	protected char letter;
	protected TrieNode[] links;
	protected boolean match;
	
	public TrieNode(char letter, boolean match){
		this.letter = letter;
		links = new TrieNode[3];	// root, next-left, and next-right for 1 bit trie
		this.match = match;
	}
	
	public char getLetter(){
		return this.letter;
	}
	
	public void setLetter(char c){
		this.letter = c;
	}
	
	public TrieNode[] getLinks(){
		return this.links;
	}
	
	public void setLinks(TrieNode[] l){
		this.links = l;
	}
	
	public boolean isMatch(){
		return this.match;
	}
}