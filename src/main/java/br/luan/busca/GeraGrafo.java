/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.luan.busca;

import br.luan.grafomv.GrafoPanel;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import prefuse.data.Node;

/**
 *
 * @author Luan Lins
 */
public class GeraGrafo {

    public static ArrayList<Nodo> gerar(String nos, String vertices, GrafoPanel visual) throws InterruptedException {
        String[] arrayNos = nos.split(",");
        int index = 0;
        ArrayList<Nodo> nosCriados = new ArrayList<Nodo>();
        for (String no : arrayNos) {
            Nodo n = new Nodo(no);
            n.index = index;
            Node nG = visual.getGrafo().addNode();
            nG.set("nome", no);
            nG.set("cor", 0);
            
            nosCriados.add(n);
            index = index +1;
        }
        
        
        
        Thread.sleep(2000);

        for (Nodo n : nosCriados) {
            Pattern pp = Pattern.compile("(" + n.nome + "-(\\w+))");
            Matcher mp = pp.matcher(vertices);
            while(mp.find()){
                for(Nodo a : nosCriados){
                    if(a.nome.matches(mp.group(2))){
                        n.adjacencias.add(a);
                        a.adjacencias.add(n);
                        Node s = visual.getGrafo().getNode(n.index);
                        Node f = visual.getGrafo().getNode(a.index);
                        visual.getGrafo().addEdge(s,f);
                        
                    }
                }
            }
        }
        
        visual.Renderizar();
        return nosCriados;
    }
    
}
