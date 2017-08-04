/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.luan.busca;

import br.luan.grafomv.GrafoPanel;
import java.util.LinkedList;
import java.util.Queue;
import prefuse.data.Node;

/**
 *
 * @author Luan Lins
 */
public class BFS {

    private int custo = 2;
    //dffdfd;
    private GrafoPanel visual;
    private boolean inicioFim = false;

    public BFS(GrafoPanel visual) {
        this.visual = visual;
    }

    public void buscar(Nodo inicio, Nodo fim) throws InterruptedException {
        Queue<Nodo> queue = new LinkedList<Nodo>();

        if (inicio == null) {
            return;
        }

        inicio.visitado = true;

        queue.add(inicio);

        while (!queue.isEmpty()) {

            Nodo r = queue.remove();
            if (r.nome.matches(fim.nome)) {
                Node nG = visual.getGrafo().getNode(r.index);
                nG.set("cor", 1);
                visual.Renderizar();
                Thread.sleep(1000);
                
                this.inicioFim = true;
            }
            this.custo = this.custo + 1;
            if (!(this.inicioFim)) {
                Node nG = visual.getGrafo().getNode(r.index);
                nG.set("cor", 1);
                visual.Renderizar();
                Thread.sleep(1000);
                nG.set("cor", 0);
                visual.Renderizar();
                for (Nodo n : r.adjacencias) {
                    if (!(n.visitado)) {
                        queue.add(n);
                        n.visitado = true;
                    }
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
