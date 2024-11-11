package part2.barbara;

import java.util.ArrayList;
import java.util.List;

public class Controle {
  public boolean emprestar(String raAluno, int quantLivros, int[] codigos) {
	  boolean retorno = true;
	  /*Aqui voc� deve instaciar um objeto aluno*/
	  Aluno aluno = new Aluno(raAluno);
	   //Verifica se o aluno existe
	  if (!aluno.verficaAluno()) {
		  System.out.println("Aluno InexistenteraAluno");
		  retorno = false;
      }
	   //Verifica se o aluno possui algum Debito
	  if (!aluno.verificaDebito()) {
		  System.out.println("Aluno em Debito");
		  retorno = false;
      }
	  
	  //Caso o aluno n�o tenha d�bitos e exista
	  if(retorno) {   
		//Cria um conjunto de livros
		  List<Livro> livros = new ArrayList<Livro>();  
	     /*Para cada livro verifica  se � exemplar da biblioteca 
                   e s� deixar� emprestar os livros que n�o s�o */
        
          for(int i = 0; i< quantLivros; i++) {  
        	  Livro l = new Livro(codigos[i]); 
			   //caso o livro n�o seja exemplar da biblioteca permite emprestar  
		   
              if (!l.verificaLivro()) {
            	  livros.add(l);             	  
              }
           }
		     /*Chama o m�todo delegado do aluno de emprestar cliente, passando o conjunto de livros como parametro caso tenha pelo menos um livro a ser emprestado*/
		
		   if (livros.size() > 0) {   
		     retorno = aluno.emprestar(livros);
		     return retorno;
		   }
		   return false;
	  	}
	  	else
		  return retorno;
  	}
}
