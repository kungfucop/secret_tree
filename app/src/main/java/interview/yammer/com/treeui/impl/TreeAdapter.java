package interview.yammer.com.treeui.impl;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import interview.yammer.com.treeui.R;
import interview.yammer.com.treeui.sol.TreeAdapterSol;
import interview.yammer.com.treeui.TreeNode;

/**
 * Created by hongjie on 3/20/18.
 */

public class TreeAdapter extends BaseAdapter {

    private static final String TAG = TreeAdapterSol.class.getSimpleName();
    private Context mContext;
    // visible nodes
    private List<TreeNode> mVisibleNodes;
    // backup of the original nodes for this tree
    private List<TreeNode> mOriginalNodes;


    public TreeAdapter(Context mContext, TreeNode root) {
        this.mContext = mContext;
        mOriginalNodes = initOriginalNodes(root);
        mVisibleNodes = initVisibleNodes(root);
    }


    /**
     * Parse the root passed in and create a list of node for the listview
     *
     * @param root
     * @return
     */
    private List<TreeNode> initOriginalNodes(TreeNode root) {
        return null;
    }

    private List<TreeNode> initVisibleNodes(TreeNode root) {
        return null;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
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
                            int location = (Integer) v.getTag();
                            refreshNodeSubTree(location);
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

    }


    /**
     * Update the expand indicator and change padding based on level of node accordingly
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
