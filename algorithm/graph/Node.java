import java.util.ArrayList;
import java.util.List;

class Node {
  int val;
  List<Node> neighbors;

  Node(int val) {
    this.val = val;
    this.neighbors = new ArrayList<Node>();
  }

  Node(int val, List<Node> neighbors) {
    this.val = val;
    this.neighbors = neighbors;
  }
}
