
package com.atividadefy.service.impl;

import com.atividadefy.exception.ResourceNotFoundException;
import com.atividadefy.model.entity.Tarefa;
import com.atividadefy.repository.TarefaRepository;
import com.atividadefy.service.TarefaService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TarefaServiceImpl implements TarefaService{
   
    private TarefaRepository tarefaRepository;

    public List<Tarefa> listarTodasTarefas() {
        return tarefaRepository.findAll();
    }
    
    @Autowired
    public TarefaServiceImpl(TarefaRepository tarefaRepository) {
        this.tarefaRepository = tarefaRepository;
    }
    public Tarefa salvarTarefa(Tarefa tarefa) {
        tarefa.setDataCriacao(LocalDateTime.now());
        tarefa.setDataAtualizacao(LocalDateTime.now());
        return tarefaRepository.save(tarefa);
    }

    public Tarefa atualizarTarefa(Long id, Tarefa tarefaAtualizada) {
        Tarefa tarefaExistente = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o ID: " + id));

        tarefaExistente.setNome(tarefaAtualizada.getNome());
        tarefaExistente.setDescricao(tarefaAtualizada.getDescricao());
        tarefaExistente.setStatus(tarefaAtualizada.getStatus());
        tarefaExistente.setObservacoes(tarefaAtualizada.getObservacoes());
        tarefaExistente.setDataAtualizacao(LocalDateTime.now());

        return tarefaRepository.save(tarefaExistente);
    }

    public Tarefa deletarTarefa(Long id) {
        Tarefa tarefa = tarefaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarefa não encontrada com o ID: " + id));

        tarefaRepository.deleteById(id);
        return tarefa;
    }
}
