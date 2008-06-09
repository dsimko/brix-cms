package brix.plugin.site.page;

import javax.jcr.Node;
import javax.jcr.Workspace;

import brix.jcr.NodeWrapperFactory;
import brix.jcr.RepositoryUtil;
import brix.jcr.api.JcrNode;
import brix.jcr.api.JcrSession;
import brix.jcr.wrapper.BrixFileNode;
import brix.jcr.wrapper.BrixNode;

public class Page extends AbstractContainer
{

    public static NodeWrapperFactory FACTORY = new NodeWrapperFactory()
    {
        @Override
        public boolean canWrap(JcrNode node)
        {
            return PageSiteNodePlugin.TYPE.equals(getNodeType(node));
        }

        @Override
        public JcrNode wrap(JcrNode node)
        {
            return new Page(node, node.getSession());
        }

        @Override
        public void initializeRepository(Workspace workspace)
        {
            RepositoryUtil.registerMixinType(workspace, PageSiteNodePlugin.TYPE, false, false);
        }
    };

    public Page(Node delegate, JcrSession session)
    {
        super(delegate, session);
    }

    public static Page initialize(JcrNode node)
    {
        BrixNode brixNode = (BrixNode)node;
        BrixFileNode.initialize(node, "text/html");
        brixNode.setNodeType(PageSiteNodePlugin.TYPE);

        return new Page(node.getDelegate(), node.getSession());
    }
}