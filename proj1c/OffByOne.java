public class OffByOne implements CharacterComparator {
    // Need a constructor
    public OffByOne() {
    }

    // This method returns true for letters that are differnt by one letter
    @Override
    public boolean equalChars(char x, char y) {
        return (Math.abs(x - y) == 1);
    }

}
