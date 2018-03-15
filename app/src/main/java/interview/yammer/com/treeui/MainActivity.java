package interview.yammer.com.treeui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    private TreeAdapter treeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.list);
        treeAdapter = new TreeAdapter(this, new TreeNodeImpl(getParentNode()));
        listView.setAdapter(treeAdapter);
    }

    private NodeData getParentNode() {
        NodeData parent = new NodeData("Products");
        NodeData electronics = new NodeData("electronics");
        List<String> electronic = new ArrayList();
        electronic.add("computers");
        electronic.add("phones");
        electronics.setChildren(electronic);
        NodeData cloth = new NodeData("cloth");
        NodeData shoes = new NodeData("shoes");
        NodeData hats = new NodeData("hats");
        NodeData sun_hat = new NodeData("sun hat");
        NodeData winterhat = new NodeData("winter hat");
        hats.addChild(sun_hat);
        hats.addChild(winterhat);
        NodeData fashion = new NodeData("fashion");
        fashion.addChild(cloth);
        fashion.addChild(shoes);
        fashion.addChild(hats);

        parent.addChild(electronics);
        parent.addChild(fashion);
        return parent;
    }
}
