package model;

public class PrefixTrie {
	public TrieNode createTree()
    {
        return(new TrieNode('\0', false));
    }
   
    public void insertWord(TrieNode root, String word)
    {
        int offset = 97;
        int l = word.length();
        char[] letters = word.toCharArray();
        TrieNode curNode = root;
       
        for (int i = 0; i < l; i++)
        {
            if (curNode.links[letters[i]-offset] == null)
                curNode.links[letters[i]-offset] = new TrieNode(letters[i], i == l-1 ? true : false);
            curNode = curNode.links[letters[i]-offset];
        }
        
        if(curNode != null && !curNode.match)
        	curNode.match = true;
    }

    public boolean find(TrieNode root, String word)
    {
        char[] letters = word.toCharArray();
        int l = letters.length;
        int offset = 97;
        TrieNode curNode = root;
       
        int i;
        for (i = 0; i < l; i++)
        {
            if (curNode == null)
                return false;
            curNode = curNode.links[letters[i]-offset];
        }
       
        if (i == l && curNode == null)
            return false;
       
        if (curNode != null && !curNode.match)
            return false;
       
        return true;
    }
   
    public void printTree(TrieNode root, int level, char[] branch)
    {
        if (root == null)
            return;
       
        for (int i = 0; i < root.links.length; i++)
        {
            branch[level] = root.letter;
            printTree(root.links[i], level+1, branch);   
        }
       
        if (root.match)
        {
            for (int j = 1; j <= level; j++)
                System.out.print(branch[j]);
            System.out.println();
        }
    }
}
