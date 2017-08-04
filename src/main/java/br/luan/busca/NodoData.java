package br.luan.busca;

import java.util.ArrayList;
import java.util.List;

import prefuse.data.Edge;
import prefuse.data.Node;

public class NodoData {

	public Node nodo;
	public List<Edge> aresta = new ArrayList<Edge>();
	
	public Node pesNodo(String nome){
		if(this.nodo.getString("nome").equals(nome)){
			return this.nodo;
		}
		else 
			return null;
	}
}
