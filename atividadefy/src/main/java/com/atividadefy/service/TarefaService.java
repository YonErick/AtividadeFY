package com.atividadefy.service;

import com.atividadefy.model.entity.Tarefa;

	public interface TarefaService {
		
		Tarefa salvarTarefa(Tarefa tarefa);
		Tarefa atualizarTarefa(Long long1, Tarefa tarefa);
		Tarefa deletarTarefa(Long id);
		
	}