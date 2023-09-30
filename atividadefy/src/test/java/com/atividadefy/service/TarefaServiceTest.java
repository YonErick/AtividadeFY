package com.atividadefy.service;

import com.atividadefy.model.entity.Tarefa;
import com.atividadefy.repository.TarefaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("test")
public class TarefaServiceTest {

    @SpyBean
    private TarefaService tarefaService;

    @MockBean
    private TarefaRepository tarefaRepository;

    @Test
    public void deveSalvarUmaTarefaComSucesso() {
        // Cenário
        Tarefa tarefa = criarTarefa();

        Mockito.when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        // Ação
        Tarefa resultado = tarefaService.salvarTarefa(tarefa);

        // Verificação
        Assertions.assertNotNull(resultado.getId());
    }

    @Test
    public void naoDeveSalvarUmaTarefaComNomeNulo() {
        // Cenário
        Tarefa tarefa = criarTarefa();
        tarefa.setNome(null);

        // Ação e Verificação
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tarefaService.salvarTarefa(tarefa);
        });

        Mockito.verify(tarefaRepository, Mockito.never()).save(tarefa);
    }

    @Test
    public void deveAtualizarUmaTarefaComSucesso() {
        // Cenário
        Tarefa tarefa = criarTarefa();
        tarefa.setId(1L);

        Mockito.when(tarefaRepository.existsById(tarefa.getId())).thenReturn(true);
        Mockito.when(tarefaRepository.save(tarefa)).thenReturn(tarefa);

        // Ação
        Tarefa resultado = tarefaService.atualizarTarefa(tarefa.getId(), tarefa);

        // Verificação
        Assertions.assertEquals(tarefa.getId(), resultado.getId());
    }

    @Test
    public void naoDeveAtualizarUmaTarefaInexistente() {
        // Cenário
        Tarefa tarefa = criarTarefa();
        tarefa.setId(1L);

        Mockito.when(tarefaRepository.existsById(tarefa.getId())).thenReturn(false);

        // Ação e Verificação
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tarefaService.atualizarTarefa(tarefa.getId(), tarefa);
        });

        Mockito.verify(tarefaRepository, Mockito.never()).save(tarefa);
    }

    @Test
    public void deveDeletarUmaTarefaComSucesso() {
        // Cenário
        Long id = 1L;

        Mockito.when(tarefaRepository.existsById(id)).thenReturn(true);

        // Ação
        tarefaService.deletarTarefa(id);

        // Verificação
        Mockito.verify(tarefaRepository).deleteById(id);
    }

    @Test
    public void naoDeveDeletarUmaTarefaInexistente() {
        // Cenário
        Long id = 1L;

        Mockito.when(tarefaRepository.existsById(id)).thenReturn(false);

        // Ação e Verificação
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            tarefaService.deletarTarefa(id);
        });

        Mockito.verify(tarefaRepository, Mockito.never()).deleteById(id);
    }

    private Tarefa criarTarefa() {
        return new Tarefa();
    }
}
