package interview.yammer.com.treeui;

import java.util.List;

/**
 * Interface for the tree structure node, which supports expand and collapse
 * <p/>
 */
public interface TreeNode {

    /**
     * Get all the children
     *
     * @return
     */
    public List<? extends TreeNode> getChildren();

    /**
     * get children count
     *
     * @return
     */
    public int getChildCount();

    /**
     * Is this node root or not
     *
     * @return
     */
    public boolean isRoot();

    /**
     * Whether this node is expanded
     *
     * @return
     */
    public boolean isExpanded();

    /**
     * @param isExpanded
     */
    public void setExpanded(boolean isExpanded);

    /**
     * Get the title of this node
     *
     * @return
     */

    public String getTitle();

    /**
     * Get level of this node
     *
     * @return
     */
    public int getLevel();

    /**
     * Check whether the parent node is collapsed
     *
     * @return
     */
    public boolean isParentCollapsed();

    /**
     * If node has no child, it is leaf
     *
     * @return
     */
    public boolean isLeaf();

}
