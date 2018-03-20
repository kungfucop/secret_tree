package interview.yammer.com.treeui;


import java.util.ArrayList;
import java.util.List;

/**
 * Entity which represents a category
 */
public class NodeData {

    private String mLabel;
    private List<NodeData> mChildren;

    public NodeData(String label) {
        mLabel = label;
        mChildren = new ArrayList<>();
    }


    public void setChildren(List<String> children) {
        mChildren = new ArrayList<>();
        if (children != null) {
            for (int i = 0; i < children.size(); i++) {
                mChildren.add(new NodeData(children.get(i)));
            }
        }
    }


    public void addChild(NodeData child) {
        mChildren.add(child);
    }

    /**
     * Getters
     */

    public String getLabel() {
        return mLabel;
    }


    public List<NodeData> getChildren() {
        return mChildren;
    }


}
