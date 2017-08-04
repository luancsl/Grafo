package br.luan.grafomv;

import java.awt.geom.Rectangle2D;
import java.util.Iterator;
import prefuse.action.layout.Layout;
import prefuse.visual.DecoratorItem;
import prefuse.visual.VisualItem;
 
/**
 * Classe respons�vel pela posi��o do valor da aresta
 * @author Valdemir Praxedes Trindade
 *
 */
public class FinalDecoratorEdgeLayout extends Layout
{
	
    public FinalDecoratorEdgeLayout(String group) {
        super(group);
    }
 
    public void run(double frac) {
        Iterator iter = m_vis.items(m_group);
        while ( iter.hasNext() ) {
            DecoratorItem decorator = (DecoratorItem)iter.next();
            VisualItem decoratedItem = decorator.getDecoratedItem();
            Rectangle2D bounds = decoratedItem.getBounds();
            
            double x = bounds.getCenterX();
            double y = bounds.getCenterY();
            
            setX(decorator, null, x);
            setY(decorator, null, y);
           
        }
    }
    
    
}
