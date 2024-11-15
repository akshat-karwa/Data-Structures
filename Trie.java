class TrieNode {

    private TrieNode[] links;
    private boolean isEnd;

    public TrieNode() {
        this.links = new TrieNode[26];
        this.isEnd = false;
    }

    public boolean containsKey(char c) {
        return this.links[c - 'a'] != null;
    }

    public TrieNode get(char c) {
        return this.links[c - 'a'];
    }

    public void put(char c, TrieNode node) {
        this.links[c - 'a'] = node;
    }

    public void setEnd() {
        isEnd = true;
    }

    public boolean getIsEnd() {
        return this.isEnd;
    }
    
}

class Trie {

    private TrieNode root;

    public Trie() {
        this.root = new TrieNode();
    }
    
    public void insert(String word) {
        TrieNode node = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (!node.containsKey(c)) {
                node.put(c, new TrieNode());
            }
            node = node.get(c);
        }
        node.setEnd();
    }
    
    public boolean search(String word) {
        TrieNode node = searchPrefix(word);
        return node != null && node.getIsEnd();
    }

    private TrieNode searchPrefix(String word) {
        TrieNode node = this.root;
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            if (node.containsKey(c)) {
                node = node.get(c);
            } else {
                return null;
            }
        }
        return node;
    }
    
    public boolean startsWith(String prefix) {
        TrieNode node = searchPrefix(prefix);
        return node != null; 
    }
    
}
