package br.luan.busca;

import java.util.ArrayList;

/*
 * 
 */

 public class Nodo  {
		
		public String nome;
		public int valor = 0;
                public boolean visitado = false;
		public Nodo pai = null;
                public int index = 0;
		public ArrayList<Nodo> adjacencias = new ArrayList<Nodo>();
		
		
		public Nodo(String nome){
                    this.nome = nome;
		}
		

}
