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
    List<? extends TreeNode> getChildren();

    /**
     * get children count
     *
     * @return
     */
    int getChildCount();

    /**
     * Is this node root or not
     *
     * @return
     */
    boolean isRoot();

    /**
     * Whether this node is expanded
     *
     * @return
     */
    boolean isExpanded();

    /**
     * @param isExpanded
     */
    void setExpanded(boolean isExpanded);

    /**
     * Get the title of this node
     *
     * @return
     */

    String getTitle();

    /**
     * Get level of this node
     *
     * @return
     */
    int getLevel();

    /**
     * Check whether the parent node is collapsed
     *
     * @return
     */
    boolean isParentCollapsed();

    /**
     * If node has no child, it is leaf
     *
     * @return
     */
    boolean isLeaf();

}
