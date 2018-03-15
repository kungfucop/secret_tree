package interview.yammer.com.treeui;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic tree structure node for category
 * <p/>
 */
public class TreeNodeImpl implements TreeNode {

    private NodeData mNodeData;
    private List<TreeNodeImpl> mChildren;
    private TreeNodeImpl mParent;
    private boolean mIsExpanded = true;

    /**
     * Constructor for the root node
     *
     * @param children children of the root node
     */
    public TreeNodeImpl(@NonNull List<NodeData> children) {
        mChildren = new ArrayList<>(children.size());
        for (NodeData child : children) {
            TreeNodeImpl childNode = new TreeNodeImpl(child);
            childNode.mParent = this;
            mChildren.add(childNode);
        }
    }

    /**
     * Constructor with nodeData
     *
     * @param nodeData nodeData for this node
     */
    public TreeNodeImpl(@NonNull NodeData nodeData) {
        List<NodeData> children = nodeData.getChildren();
        mChildren = new ArrayList<>(children.size());
        for (NodeData child : children) {
            TreeNodeImpl childNode = new TreeNodeImpl(child);
            childNode.mParent = this;
            mChildren.add(childNode);
        }
        mNodeData = nodeData;
    }

    @Override
    public List<? extends TreeNode> getChildren() {
        return mChildren;
    }

    @Override
    public int getChildCount() {
        return mChildren == null ? 0 : mChildren.size();
    }

    @Override
    public boolean isRoot() {
        return mParent == null;
    }

    /**
     * @return true if this is expanded
     */
    @Override
    public boolean isExpanded() {
        return mIsExpanded;
    }

    /**
     * Set this node to expanded
     *
     * @param isExpanded new state
     */
    @Override
    public void setExpanded(boolean isExpanded) {
        mIsExpanded = isExpanded;
    }

    // The label for just the category for example "Mini" under node "Skirts"
    @Override
    public String getTitle() {
        if (mNodeData == null) {
            return "";
        } else {
            return mNodeData.getLabel();
        }
    }

    @Override
    public int getLevel() {
        return mParent == null ? 0 : mParent.getLevel() + 1;
    }

    @Override
    public boolean isParentCollapsed() {
        boolean parentCollapsed =  mParent != null && (!mParent.isExpanded() || mParent.isParentCollapsed());
        return  parentCollapsed;
    }

    @Override
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

}
