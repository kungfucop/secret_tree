package interview.yammer.com.treeui.sol;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import interview.yammer.com.treeui.R;
import interview.yammer.com.treeui.TreeNode;

/**
 * @author hdong
 *         Adapter the for a tree structure
 */
public class TreeAdapterSol extends BaseAdapter {
    private static final String TAG = TreeAdapterSol.class.getSimpleName();
    private Context mContext;
    // visible nodes
    private List<TreeNode> mVisibleNodes = new ArrayList<>();
    // backup of the original nodes for this tree
    private List<TreeNode> mOriginalNodes = new ArrayList<>();


    public TreeAdapterSol(Context context, TreeNode root) {
        ArrayList<TreeNode> roots = new ArrayList<>();
        initNodeRoot(root, roots);

        this.mContext = context;
        for (TreeNode n : roots) {
            storeNode(n);
        }
        updateExpandStatus(1); // Set expand level to be 1 by default so it means only show the
        // most ancestor node

    }

    private void initNodeRoot(TreeNode root, ArrayList<TreeNode> roots) {
        roots.add(root);
        for (TreeNode n : root.getChildren()) {
            if (n.isLeaf()) {
                roots.add(n);
            } else {
                initNodeRoot(n, roots);
            }
        }
    }

    private void storeNode(TreeNode node) {
        mVisibleNodes.add(node);
        mOriginalNodes.add(node);
    }

    /**
     * Set the level of sub group to expand
     *
     * @param level
     */
    public void updateExpandStatus(int level) {
        mVisibleNodes.clear();
        for (TreeNode n : mOriginalNodes) {
            int nodeLevel = n.getLevel();
            if (n.getLevel() <= level) {
                n.setExpanded(nodeLevel != level);
                mVisibleNodes.add(n);
            } else {
                n.setExpanded(false);
            }
        }

    }

    @Override
    public int getCount() {
        return mVisibleNodes.size();
    }

    @Override
    public Object getItem(int position) {
        return mVisibleNodes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup viewgroup) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.expandable_list_item, null);
            holder = new ViewHolder(convertView);
            holder.expandIndicator.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int pos = (Integer) v.getTag();
                            refreshNodeSubTree(pos);
                        }
                    }
            );
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.expandIndicator.setTag(position);
        TreeNode n = mVisibleNodes.get(position);
        updateNodeUI(n, holder);
        return convertView;
    }

    private void refreshNodeSubTree(int position) {
        TreeNode n = mVisibleNodes.get(position);
        if (n != null) {
            if (!n.isLeaf()) {
                n.setExpanded(!n.isExpanded());
                collectVisibleNodes();
                this.notifyDataSetChanged();
            }
        }
    }

    private void collectVisibleNodes() {
        mVisibleNodes.clear();
        for (TreeNode n : mOriginalNodes) {
            if (!n.isParentCollapsed() || n.isRoot()) {
                mVisibleNodes.add(n);
            }
        }
    }


    /**
     * Update the expand indicator and change padding accordingly
     *
     * @param n
     * @param holder
     */
    private void updateNodeUI(TreeNode n, ViewHolder holder) {
        if (n.isLeaf()) {
            holder.expandIndicator.setVisibility(View.INVISIBLE);
        } else {
            holder.expandIndicator.setVisibility(View.VISIBLE);
            if (n.isExpanded()) {
                holder.expandIndicator.setImageResource(R.drawable.btn_arrow_down);
            } else {
                holder.expandIndicator.setImageResource(R.drawable.btn_arrow_right);

            }
        }

        holder.titleView.setText(n.getTitle());
        holder.listItem.setPadding(
                mContext.getResources()
                        .getDimensionPixelSize(R.dimen.expandable_list_child_horizontal_padding)
                        * n.getLevel(),
                mContext.getResources().getDimensionPixelSize(R.dimen.inner_vertical_padding_micro),
                mContext.getResources()
                        .getDimensionPixelSize(R.dimen.expandable_list_child_horizontal_padding),
                mContext.getResources().getDimensionPixelSize(R.dimen.inner_vertical_padding_micro)
        );
    }

    private static class ViewHolder {
        private ImageView expandIndicator;
        private TextView titleView;
        private View listItem;

        public ViewHolder(View containerView) {
            listItem = containerView.findViewById(R.id.list_item);
            expandIndicator = containerView.findViewById(R.id.group_indicator);
            titleView = containerView.findViewById(R.id.expandable_list_title);
        }
    }

}
