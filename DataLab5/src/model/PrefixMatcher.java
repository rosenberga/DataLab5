package model;

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
	}
}
