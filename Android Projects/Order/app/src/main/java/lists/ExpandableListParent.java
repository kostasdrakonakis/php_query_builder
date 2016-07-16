package lists;


import java.util.ArrayList;

public class ExpandableListParent {

    private String name;
    private ArrayList<ProductList> childs;

    public ExpandableListParent(String name, ArrayList<ProductList> childs) {
        this.name = name;
        this.childs = childs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ProductList> getChilds() {
        return childs;
    }

    public void setChilds(ArrayList<ProductList> childs) {
        this.childs = childs;
    }
}
