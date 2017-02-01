public class OffByN implements CharacterComparator {
	// First build a constructor
	private int N;
	public OffByN(int x) {
		N = x;
	}
	// Then the method
	@Override
	public boolean equalChars(char x, char y) {
		return (Math.abs(x - y) == N);
	}
}
