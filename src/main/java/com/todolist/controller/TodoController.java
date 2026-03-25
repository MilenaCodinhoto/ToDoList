package com.todolist.controller;

import com.todolist.dao.TodoDao;
import com.todolist.model.Todo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TodoController {

    @Autowired
    private TodoDao todoDao;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("todos", todoDao.listar());
        return "index";
    }

    @GetMapping("/deletar")
    public String deletar(@RequestParam("id") Long id) {
        todoDao.deletar(id);
        return "redirect:/";
    }

    @GetMapping("/editar")
    public String editar(@RequestParam("id") Long id, Model model) {
        model.addAttribute("todo", todoDao.buscarPorId(id));
        return "editar";
    }

    @PostMapping("/add")
    public String add(@RequestParam("titulo") String titulo) {
        Todo todo = new Todo();
        todo.setTitulo(titulo);
        todo.setDescricao("");
        todo.setConcluida(false);
        todoDao.criar(todo);
        return "redirect:/";
    }

    @PostMapping("/atualizar")
    public String atualizar(@RequestParam("id") Long id, @RequestParam("titulo") String titulo, @RequestParam("descricao") String descricao) {
        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitulo(titulo);
        todo.setDescricao(descricao);
        todo.setConcluida(false);
        todoDao.atualizar(todo);
        return "redirect:/";
    }
}


