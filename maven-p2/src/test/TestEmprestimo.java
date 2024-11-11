package test;

import part2.barbara.*;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestEmprestimo {

    @Test
    void testAlunoNaoCadastrado() {
        Aluno aluno = new Aluno("999", false); 
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1)); 
        
        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emprestimo.emprestar(livros, aluno);
        });
        
        assertEquals("Aluno não cadastrado", thrown.getMessage());
    }

    @Test
    void testAlunoComPendencias() {
        Aluno aluno = new Aluno("123", true); 
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1));
        
        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emprestimo.emprestar(livros, aluno);
        });
        
        assertEquals("Aluno possui débitos", thrown.getMessage());
    }

    @Test
    void testEmprestaLivroReservado() {
        Aluno aluno = new Aluno("123", false);
        Livro livroReservado = new Livro(1);
        livroReservado.setReservado(true);  
        List<Livro> livros = new ArrayList<>();
        livros.add(livroReservado);
        
        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emprestimo.emprestar(livros, aluno);
        });
        
        assertEquals("Livro está reservado e não pode ser emprestado", thrown.getMessage());
    }

    @Test
    void testEmpresta5LivrosSucesso() {
        Aluno aluno = new Aluno("123", false);
        List<Livro> livros = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            livros.add(new Livro(i));
        }
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.emprestar(livros, aluno);
        
        assertEquals(5, emprestimo.getQuantidadeLivrosEmprestados());
    }

    @Test
    void testNaoEmprestaMaisDe5Livros() {
        Aluno aluno = new Aluno("123", false);
        List<Livro> livros = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            livros.add(new Livro(i));
        }
        
        Emprestimo emprestimo = new Emprestimo();
        IllegalArgumentException thrown = Assertions.assertThrows(IllegalArgumentException.class, () -> {
            emprestimo.emprestar(livros, aluno);
        });
        
        assertEquals("O limite máximo de livros que pode ser emprestado é 5", thrown.getMessage());
    }

    @Test
    void testCalculaDataDeDevolucaoPara1ou2Livros() {
        Aluno aluno = new Aluno("123", false);
        List<Livro> livros = new ArrayList<>();
        livros.add(new Livro(1));
        livros.add(new Livro(2));
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.emprestar(livros, aluno);
        
        Date now = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 2);  // prazo padrão de 2 dias

        for (Item item : emprestimo.getItensEmprestados()) {
            assertEquals(calendar.getTime().getDay(), item.getDataDevolucao().getDay());
        }
    }

    @Test
    void testCalculaDataDeDevolucaoPara3Livros() {
        Aluno aluno = new Aluno("123", false);
        List<Livro> livros = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            livros.add(new Livro(i));
        }
        
        Emprestimo emprestimo = new Emprestimo();
        emprestimo.emprestar(livros, aluno);
        
        Date now = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.DAY_OF_MONTH, 5);  // 2 dias padrão + 3 dias extras

        for (Item item : emprestimo.getItensEmprestados()) {
            assertEquals(calendar.getTime().getDay(), item.getDataDevolucao().getDay());
        }
    }
}
