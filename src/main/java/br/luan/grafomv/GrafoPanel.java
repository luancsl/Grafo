package br.luan.grafomv;

import java.awt.event.MouseEvent;
import br.luan.grafomv.FinalDecoratorEdgeLayout;
import br.luan.grafomv.FinalDecoratorLayout;
import prefuse.Constants;
import prefuse.Display;
import prefuse.Visualization;
import prefuse.action.ActionList;
import prefuse.action.RepaintAction;
import prefuse.action.assignment.ColorAction;
import prefuse.action.assignment.DataColorAction;
import prefuse.action.layout.graph.BalloonTreeLayout;
import prefuse.action.layout.graph.ForceDirectedLayout;
import prefuse.controls.DragControl;
import prefuse.controls.FocusControl;
import prefuse.controls.PanControl;
import prefuse.controls.ZoomControl;
import prefuse.data.Graph;
import prefuse.data.Schema;
import prefuse.data.Table;
import prefuse.render.DefaultRendererFactory;
import prefuse.render.EdgeRenderer;
import prefuse.render.LabelRenderer;
import prefuse.render.Renderer;
import prefuse.render.ShapeRenderer;
import prefuse.util.ColorLib;
import prefuse.util.FontLib;
import prefuse.util.PrefuseLib;
import prefuse.visual.VisualItem;
import prefuse.visual.expression.InGroupPredicate;

/**
 * Painel respons�vel por toda a parte visual do grafo
 *
 * @author Luan Lins
 *
 */
public class GrafoPanel extends Display {

    /**
     * Um grafo modela uma rede de n�s conectados por uma cole��o de arestas.
     * Ambos os n�s e as arestas podem ter qualquer n�mero de campos de dados
     * associados. Al�m disso, as arestas s�o direcionadas ou n�o direcionadas,
     * indicando uma direcionalidade poss�vel da conex�o. Cada vantagem tem um
     * n� de origem e um n� de destino, para uma aresta direcionada, isso indica
     * a direcionalidade, para uma aresta n�o direcionada, isso � apenas um
     * artefato da ordem em que os n�s foram especificados quando adicionados ao
     * grafo.
     */
    private Graph grafo;

    public GrafoPanel() {

        super(new Visualization());
        Init_Instancia();
        Init_Tamanho();
        Init_Eventos();
        setHighQuality(true);

    }

    public Graph getGrafo() {
        return grafo;
    }

    private void Init_Instancia() {
        //Criando uma tabela de dados de nodos e arestas
        Table nodo = new Table();
        Table aresta = new Table(0, 1);

        //Configurando as colunas
        nodo.addColumn("nome", String.class);
        nodo.addColumn("cor", Integer.class);
        aresta.addColumn(Graph.DEFAULT_SOURCE_KEY, int.class);
        aresta.addColumn(Graph.DEFAULT_TARGET_KEY, int.class);
        aresta.addColumn("valor", String.class);

        grafo = new Graph(nodo, aresta, true);

        m_vis.addGraph("grafo", grafo);
        m_vis.setInteractive("grafo.edges", null, true);

        final Schema DECORATOR_SCHEMA = PrefuseLib.getVisualItemSchema();

        DECORATOR_SCHEMA.setDefault(VisualItem.INTERACTIVE, false);
        DECORATOR_SCHEMA.setDefault(VisualItem.TEXTCOLOR,
                ColorLib.rgb(0, 0, 0));
        DECORATOR_SCHEMA.setDefault(VisualItem.FONT,
                FontLib.getFont("Tahoma", 12));

        m_vis.addDecorators("nodedec", "grafo.nodes", DECORATOR_SCHEMA);
        m_vis.addDecorators("edgedec", "grafo.edges", DECORATOR_SCHEMA);
    }

    private void Init_Tamanho() {
        setSize(700, 500);
        pan(260, 250);
    }

    private void Init_Eventos() {

        addControlListener(new DragControl());
        addControlListener(new PanControl());
        addControlListener(new ZoomControl());
        addControlListener(new FocusControl());
    }

    public void Renderizar() {
        Renderer nodeR = new ShapeRenderer(50);

        EdgeRenderer edgeR = new EdgeRenderer(prefuse.Constants.EDGE_TYPE_CURVE, prefuse.Constants.EDGE_ARROW_NONE);

        DefaultRendererFactory drf = new DefaultRendererFactory();
        drf.setDefaultRenderer(nodeR);
        drf.setDefaultEdgeRenderer(edgeR);

        drf.add(new InGroupPredicate("nodedec"), new LabelRenderer("nome"));
        drf.add(new InGroupPredicate("edgedec"), new LabelRenderer("valor"));

        m_vis.setRendererFactory(drf);
        m_vis.setValue("grafo.nodes", null, VisualItem.SHAPE,
                new Integer(Constants.SHAPE_ELLIPSE));

        int[] palette = {ColorLib.rgb(255, 255, 255), ColorLib.rgb(190, 190, 255), ColorLib.rgb(255, 255, 255)};

        //new  prefuse.action.assignment.DataColorAction(TOOL_TIP_TEXT_KEY, TOOL_TIP_TEXT_KEY, WIDTH, TOOL_TIP_TEXT_KEY);
        ColorAction nStroke = new ColorAction("grafo.nodes", VisualItem.STROKECOLOR);
        nStroke.setDefaultColor(ColorLib.gray(100));
        DataColorAction fill = new DataColorAction("grafo.nodes", "cor",
                Constants.NOMINAL,
                VisualItem.FILLCOLOR,
                palette);
        ColorAction edges = new ColorAction("grafo.edges",
                VisualItem.STROKECOLOR, ColorLib.gray(10));
        ColorAction arrow = new ColorAction("grafo.edges",
                VisualItem.FILLCOLOR, ColorLib.gray(20));
        ActionList color = new ActionList();
        color.add(nStroke);
        color.add(fill);
        color.add(edges);
        color.add(arrow);

        ActionList layout = new ActionList(ActionList.INFINITY);

        //prefuse.action.layout.graph.FruchtermanReingoldLayout
        //prefuse.action.layout.graph.NodeLinkTreeLayout("grafo",2,50,50,50)
        //prefuse.action.layout.graph.RadialTreeLayout("grafo")
        layout.add(new prefuse.action.layout.graph.RadialTreeLayout("grafo"));
        layout.add(new FinalDecoratorLayout("nodedec"));
        layout.add(new FinalDecoratorEdgeLayout("edgedec"));
        layout.add(new RepaintAction());

        m_vis.putAction("color", color);
        m_vis.putAction("layout", layout);

        m_vis.run("color");
        m_vis.run("layout");

    }

}
