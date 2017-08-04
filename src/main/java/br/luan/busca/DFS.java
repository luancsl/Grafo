/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.luan.busca;

import br.luan.grafomv.GrafoPanel;
import java.util.ArrayList;
import prefuse.data.Node;

/**
 *
 * @author Luan Lins
 */
public class DFS {

    private GrafoPanel visual;
    private boolean inicioFim = false;
    private int custo = 0;

    public DFS(GrafoPanel visual) {
        this.visual = visual;
    }

    public void buscar(Nodo inicio, Nodo fim) throws InterruptedException {
        
        Node nG = visual.getGrafo().getNode(inicio.index);
        System.out.print("Cor antes no visual: "+nG.get("cor")+"\n\n");
        nG.set("cor", 1);
        visual.Renderizar();
        Thread.sleep(1000);
        nG.set("cor",2);
        visual.Renderizar();
        System.out.print("No: "+inicio.nome+"\n");
        System.out.print("No visual: "+nG.get("nome")+"\n");
        System.out.print("Cor no visual: "+nG.get("cor")+"\n\n");

        if (inicio.nome.matches(fim.nome)) {
            System.out.print("Entrou inicioFim");
            this.inicioFim = true;

        }

        inicio.visitado = true;
       

        if (!(this.inicioFim)) {

            this.custo = this.custo + 1;
            for (Nodo n : inicio.adjacencias) {

                if (!(n.visitado)) {
                    buscar(n, fim);
                }

            }

        }

    }

    public void clear() {
        this.inicioFim = false;
        this.custo = 0;
    }

    public int getCusto() {
        return this.custo;
    }

}
