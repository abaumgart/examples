
import nodes.Listener;
import nodes.Talker;
import org.ros.RosCore;
import org.ros.node.DefaultNodeMainExecutor;
import org.ros.node.NodeConfiguration;
import org.ros.node.NodeMain;
import org.ros.node.NodeMainExecutor;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;

public class ROSJavaTest {

    private static RosCore mRosCore;

    public static void main(String [] args) throws UnknownHostException {

        mRosCore = RosCore.newPublic("6a36967c0c57", 11311);
        mRosCore.start();
        try {
            mRosCore.awaitStart(5, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Ros core started");

        startChatter();

        //TODO Improve this way
        while (true) {}
    }

    private static void startChatter() throws UnknownHostException {

        NodeMainExecutor e = DefaultNodeMainExecutor.newDefault();

        System.out.println("Starting listener node...");
        NodeConfiguration listenerConfig = NodeConfiguration.newPrivate();
        //String host = InetAddress.getLocalHost().getHostAddress();
        //NodeConfiguration nodeConfiguration = NodeConfiguration.newPublic(host);
        listenerConfig.setMasterUri(mRosCore.getUri());
        listenerConfig.setNodeName("Listener");
        NodeMain listener = new Listener();
        e.execute(listener, listenerConfig);

        System.out.println("Starting talker node...");
	    NodeConfiguration talkerConfig = NodeConfiguration.newPrivate();
	    talkerConfig.setMasterUri(mRosCore.getUri());
	    talkerConfig.setNodeName("Talker");
	    NodeMain talker = new Talker();
	    e.execute(talker, talkerConfig);

    }

}
