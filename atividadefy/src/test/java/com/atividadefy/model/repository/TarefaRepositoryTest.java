package com.atividadefy.model.repository;

import com.atividadefy.model.entity.Tarefa;
import com.atividadefy.repository.TarefaRepository;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TarefaRepositoryTest {

    @Autowired
    private TarefaRepository tarefaRepository;

    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void devePersistirUmaTarefaNaBaseDeDados() {
        Tarefa tarefa = criarTarefa();
        Tarefa tarefaSalva = tarefaRepository.save(tarefa);
        assertThat(tarefaSalva.getId()).isNotNull();
    }

    @Test
    public void deveBuscarUmaTarefaPorId() {
        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);
        Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById(tarefa.getId());
        assertThat(tarefaEncontrada).isPresent();
        assertThat(tarefaEncontrada.get().getId()).isEqualTo(tarefa.getId());
    }

    @Test
    public void deveRetornarNuloAoBuscarTarefaPorIdQuandoNaoExistirNaBase() {
        Optional<Tarefa> tarefaEncontrada = tarefaRepository.findById("Tarefa Inexistente");
        assertThat(tarefaEncontrada).isEmpty();
    }

    @Test
    public void deveVerificarAExistenciaDeUmaTarefaPorId() {
        Tarefa tarefa = criarTarefa();
        entityManager.persist(tarefa);
        boolean resultado = tarefaRepository.existsById(tarefa.getId());
        assertThat(resultado).isTrue();
    }

    @Test
    public void deveRetornarFalsoQuandoNaoHouverTarefaCadastradaComOId() {
        boolean resultado = tarefaRepository.existsById("Tarefa Inexistente");
        assertThat(resultado).isFalse();
    }

    private Tarefa criarTarefa() {
        Tarefa tarefa = new Tarefa();
        return tarefa;
    }
}
