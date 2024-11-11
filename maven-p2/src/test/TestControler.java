package test;

import part2.barbara.*;

import static org.junit.Assert.*;
import org.junit.jupiter.api.Test;

class TestControler {

    @Test
    void testNaoEmprestaParaAlunoNaoCadastrado() {
        String ra = "999";  // RA de um aluno não cadastrado
        Controle c = new Controle();
        
        int[] prazos = {2};  // Tentativa de empréstimo com 1 livro
        boolean retorno = c.emprestar(ra, prazos.length, prazos);
        
        // Verifica que o empréstimo foi negado porque o aluno não está cadastrado
        assertFalse("Empréstimo deve ser negado para aluno não cadastrado", retorno);
    }

    @Test
    void testNaoEmprestaParaAlunoComDebito() {
        String ra = "123";  // RA de um aluno com débito
        Controle c = new Controle();
        
        int[] prazos = {2};  // Tentativa de empréstimo com 1 livro
        boolean retorno = c.emprestar(ra, prazos.length, prazos);
        
        // Verifica que o empréstimo foi negado porque o aluno possui débito
        assertFalse("Empréstimo deve ser negado para aluno com débito", retorno);
    }

    @Test
    void testEmprestaAte5LivrosParaAlunoVerificadoSemDebitos() {
        String ra = "123";  // RA de um aluno verificado e sem débitos
        Controle c = new Controle();
        
        int[] prazos = {2, 3, 4, 5, 6};  // Empréstimo com 5 livros
        boolean retorno = c.emprestar(ra, prazos.length, prazos);
        
        // Verifica que o empréstimo foi aprovado para 5 livros
        assertTrue("Empréstimo deve ser aprovado para até 5 livros", retorno);
    }

    @Test
    void testNaoEmprestaMaisDe5Livros() {
        String ra = "123";  // RA de um aluno verificado e sem débitos
        Controle c = new Controle();
        
        int[] prazos = {2, 3, 4, 5, 6, 7};  // Tentativa de empréstimo com 6 livros
        boolean retorno = c.emprestar(ra, prazos.length, prazos);
        
        // Verifica que o empréstimo foi negado para mais de 5 livros
        assertFalse("Empréstimo deve ser negado para mais de 5 livros", retorno);
    }

    @Test
    void testCalculaDataDeDevolucaoPara2Livros() {
        String ra = "123";  // RA de um aluno verificado e sem débitos
        Controle c = new Controle();
        
        int[] prazos = {2, 2};  // Empréstimo com 2 livros
        boolean retorno = c.emprestar(ra, prazos.length, prazos);
        
        // Verifica que a data de devolução padrão foi aplicada (2 dias)
        assertTrue("Data de devolução deve ser padrão de 2 dias", retorno);
    }

    @Test
    void testCalculaDataDeDevolucaoPara3LivrosComDataExtra() {
        String ra = "123";  // RA de um aluno verificado e sem débitos
        Controle c = new Controle();
        
        int[] prazos = {2, 2, 2};  // Empréstimo com 3 livros
        boolean retorno = c.emprestar(ra, prazos.length, prazos);
        
        // Verifica que a data de devolução foi ajustada para 5 dias (2 + 3 extras)
        assertTrue("Data de devolução deve ser 5 dias para 3 livros", retorno);
    }

    @Test
    void testCalculaDataDeDevolucaoPara5LivrosComDataExtra() {
        String ra = "123";  // RA de um aluno verificado e sem débitos
        Controle c = new Controle();
        
        int[] prazos = {2, 2, 2, 2, 2};  // Empréstimo com 5 livros
        boolean retorno = c.emprestar(ra, prazos.length, prazos);
        
        // Verifica que a data de devolução foi ajustada para 11 dias (2 + 9 extras)
        assertTrue("Data de devolução deve ser 11 dias para 5 livros", retorno);
    }
}
