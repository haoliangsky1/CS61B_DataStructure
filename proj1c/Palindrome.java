public class Palindrome {
    // Create two methods:
    // This method builds a Deque where the characters in the Deque 
    // appear in the same order as in the word
    public static Deque<Character> wordToDeque(String word) {
        // super();
        Deque<Character> dq = new LinkedListDequeSolution<Character>();
        // String[] arr = word.split(" ");
        char input;
        for (int i = 0; i < word.length(); i++) {
            input = word.charAt(i);
            dq.addLast(input);
        }
        return dq;
    }
    // This method returns true if the given word is a palindrome and false otherwise.
    public static boolean isPalindrome(String word) {
        if ((word.length() == 0) || (word.length() == 1)) {
            return true;
        } else {
            double temp = Math.floor(word.length() * 1.0 / 2) - 1;
            boolean flag = false;
            for (int i = 0; i < temp; i++) {
                if (word.charAt(i) == word.charAt(word.length() - 1 - i)) {
                    flag = true;
                } else {
                    return false;
                }
            }
            return flag;
        }
    }
    // Generalize the isPalindrome method
    // This method returns true if the word is a palindrome 
    // according to the character comparison test provided 
    // by the CharacterComparator passed in as argument cc
    public static boolean isPalindrome(String word, CharacterComparator cc) {
        // Deque<Character> list = new LinkedListDequeSolution<Character> ();
        // list.wordToDeque(word);
        if ((word.length() == 0) || (word.length() == 1)) {
            return true;
        } else {
            double temp = Math.floor(word.length() * 1.0 / 2) - 1;
            boolean flag = false;
            for (int i = 0; i < temp; i++) {
                flag = cc.equalChars(word.charAt(i), word.charAt(word.length() - 1 - i));
            }
            return flag;
        }
    }
}
