/**
 * Created by denak on 15.12.2017.
 */
public class Main {
    public static void main(String[] args) {
        Obj obj = ObjReader.load("Fidget_Spinner.obj");
        ObjReader.save(obj,"1.obj");
    }
}
