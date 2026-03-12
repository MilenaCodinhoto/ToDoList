package com.todolist;

import com.todolist.dao.TodoDao;
import com.todolist.model.Todo;

public class MainTeste {
    public static void main(String[] args) {
        TodoDao dao = new TodoDao();

        //Criar
        Todo t1 = new Todo();
        t1.setTitulo("Estudar java");
        dao.criar(t1);

        System.out.println("Antes:");
        dao.listar().forEach(t ->
                System.out.println("ID: " + t.getId() + " - " + t.getTitulo()));

        //Editar
        t1.setTitulo("Estudar DAO MySQL");
        t1.setConcluida(true);
        dao.atualizar(t1);

        //Listar de novo
        dao.listar().forEach(t ->
                System.out.println("Atualizado: " + t.getTitulo() + " (" + t.isConcluida() + ")")
        );

        //Criar
        Todo t2 = new Todo();
        t2.setTitulo("Estudar php");
        dao.criar(t2);

        //Listar todas
        System.out.println("Com t2:");
        dao.listar().forEach(t ->
                System.out.println("ID: " + t.getId() + " - " + t.getTitulo()));

        //Deletar
        Long apagarId = t1.getId();
        dao.deletar(apagarId);
        System.out.println("Tarefa" + apagarId + " - " + t1.getTitulo() + " apagada");

        // Final de como ficou a lista
        System.out.println("Após delete:");
        dao.listar().forEach(t ->
                System.out.println("Sobrou: " + t.getTitulo()));
    }
}
