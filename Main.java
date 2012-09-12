/**
 * Written by Mike O'Beirne (michael.obeirne@gmail.com)
 * 
 * Successfully completes the ACM ICPC judge data in 1.864 seconds.
 */
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

class Main {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);

		State first;
		State last;

		HashMap<State, State> childToParent = new HashMap<State, State>();
		HashSet<State> visited = new HashSet<State>();
		ArrayDeque<State> Q = new ArrayDeque<State>();

		while (in.hasNext()) {

			first = new State(in.nextInt(), in.nextInt(), in.nextInt());
			last = first;

			childToParent.put(first, null);
			visited.add(first);
			Q.add(first);

			// If it doesn't have solution, skip algorithm
			if ((first.x + first.y + first.z) % 3 != 0) {
				Q.clear();
			}

			// Main Algorithm - BFS
			while (!Q.isEmpty()) {

				State current = Q.pollFirst();

				if (current.finalState()) {
					last = current;
					Q.clear();
				} else {

					ArrayList<State> toAdd = current.getChildren();

					// Process and filter children states
					for (State s : toAdd) {

						if (!visited.contains(s)) {
							visited.add(s);

							if (!childToParent.containsKey(s)) {
								childToParent.put(s, current);
							}

							Q.add(s);
						}

					}

				}

			}

			// Print solution and clear data structures
			printSolution(last, childToParent);
			Q.clear();
			childToParent.clear();
			visited.clear();

		}

	}

	public static void printSolution(State last, HashMap<State, State> map) {

		Stack<String> ans = new Stack<String>();

		if (map == null) {
			ans.add(last.toString());
		} else {

			State current = last;
			while (current != null) {
				ans.add(current.toString());
				current = map.get(current);
			}

		}

		while (!ans.isEmpty()) {
			System.out.println(ans.pop());
		}

		System.out.println("============");

	}

	static class State {

		int x, y, z;
		int hashCode;

		public State(int x, int y, int z) {
			this.x = x;
			this.y = y;
			this.z = z;

			hashCode = x + 2*y + 4*z;
		}

		public String toString() {
			return String.format("%4d%4d%4d", x, y, z);
		}

		public boolean finalState() {
			return x == y && y == z;
		}

		public ArrayList<State> getChildren() {

			ArrayList<State> ans = new ArrayList<State>();

			if (x > y) {
				ans.add(new State(x - y, 2 * y, z));
			}
			if (x > z) {
				ans.add(new State(x - z, y, 2 * z));
			}
			if (y > x) {
				ans.add(new State(2 * x, y - x, z));
			}
			if (y > z) {
				ans.add(new State(x, y - z, 2 * z));
			}
			if (z > x) {
				ans.add(new State(2 * x, y, z - x));
			}
			if (z > y) {
				ans.add(new State(x, 2 * y, z - y));
			}

			return ans;
		}

		@Override
		public boolean equals(Object o) {
			State that = (State) o;

			// If these two hold, then the last is implied
			if (x == that.x && y == that.y) {
				return true;
			}

			return false;
		}

		@Override
		public int hashCode() {
			return hashCode;
		}

	}
}
