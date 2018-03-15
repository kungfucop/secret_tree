package interview.yammer.com.treeui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hdong
 *         Adapter the for a tree structure
 */
public class TreeAdapter extends BaseAdapter {
    private static final String TAG = TreeAdapter.class.getSimpleName();
    private Context mContext;
    // visible nodes
    private List<TreeNode> mVisibleNodes = new ArrayList<>();
    // backup of the original nodes for this tree
    private List<TreeNode> mOriginalNodes = new ArrayList<>();


    public TreeAdapter(Context context, TreeNode root) {
        ArrayList<TreeNode> roots = new ArrayList<>();
        initNodeRoot(root, roots);

        this.mContext = context;
        for (TreeNode n : roots) {
            storeNode(n);
        }
        setExpandLevel(1); // Set expand level to be 1 by default

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
    public void setExpandLevel(int level) {
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
            holder = new ViewHolder();
            holder.listItem = convertView.findViewById(R.id.list_item);
            holder.groupIndicator = convertView.findViewById(R.id.group_indicator);
            holder.groupIndicator.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int location = (Integer) v.getTag();
                            expandOrCollapse(location);
                        }
                    }
            );
            holder.titleView = convertView.findViewById(R.id.expandable_list_title);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.groupIndicator.setTag(position);
        TreeNode n = mVisibleNodes.get(position);
        if (n != null) {
            if (n.isLeaf()) {
                holder.groupIndicator.setVisibility(View.INVISIBLE);
            } else {
                holder.groupIndicator.setVisibility(View.VISIBLE);
                if (n.isExpanded()) {
                    holder.groupIndicator.setImageResource(R.drawable.btn_arrow_down);

                } else {
                    holder.groupIndicator.setImageResource(R.drawable.btn_arrow_right);

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
        return convertView;
    }

    private void expandOrCollapse(int position) {
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
        Log.d(TAG, "collect visible nodes");
        for (TreeNode n : mOriginalNodes) {
            Log.d(TAG, String.format("%s is parent collapsed? %b show?%b", n.getTitle(), n.isParentCollapsed(), !n.isParentCollapsed() || n.isRoot()));

            if (!n.isParentCollapsed() || n.isRoot()) {
                mVisibleNodes.add(n);
            }
        }
    }

    private static class ViewHolder {
        private ImageView groupIndicator;
        private TextView titleView;
        private View listItem;
    }

}
