import java.util.ArrayList;

/**
 * Created by denis on 16.12.2017.
 */
public class KDTree {
    public Box box;
    public ArrayList<Node> object_nodes;

    public void createTree(Scene scene){
        for(Obj obj : scene.objects){

        }
    }

    public class Node{
        ArrayList<Triangle> triangles;
        public Node left, right;
    }
}
