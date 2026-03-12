package com.todolist.dao;

import com.todolist.model.Todo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TodoDao {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/todos?useSSL=false&serverTimezone=UTC";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "101005";

    public void criar(Todo todo) {
        String sql = "INSERT INTO todo (titulo, descricao, concluida) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

             pstmt.setString(1, todo.getTitulo());
             pstmt.setString(2, todo.getDescricao());
             pstmt.setBoolean(3, todo.isConcluida());
             pstmt.executeUpdate();

             ResultSet rs = pstmt.getGeneratedKeys();
             if (rs.next()) {
                todo.setId(rs.getLong(1));
             }
        } catch (SQLException e) {
             throw new RuntimeException("Erro ao criar todo: " + e.getMessage(), e);
        }
    }

    public void deletar(Long id) {
        String sql = "DELETE FROM todo WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setLong(1, id);
            int linhas = pstmt.executeUpdate();

            if (linhas == 0) {
                throw new RuntimeException("Todo com ID " + id + " não encontrado");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar todo ID " + id + ": " + e.getMessage(), e);
        }
    }

    public void atualizar(Todo todo) {
        String sql = "UPDATE todo SET titulo = ?, descricao = ?, concluida = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

             pstmt.setString(1, todo.getTitulo());
             pstmt.setString(2, todo.getDescricao());
             pstmt.setBoolean(3, todo.isConcluida());
             pstmt.setLong(4, todo.getId());

             int linhas = pstmt.executeUpdate();
             if (linhas == 0) {
                 throw new RuntimeException("Todo com ID " + todo.getId() + " não encontrado");
             }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar todo ID " + todo.getId() + ": " + e.getMessage(), e);
        }
    }

    public List<Todo> listar() {
        List<Todo> todos = new ArrayList<>();
        String sql = "SELECT * FROM todo ORDER BY id";

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Todo todo = new Todo();
                todo.setId(rs.getLong("id"));
                todo.setTitulo(rs.getString("titulo"));
                todo.setDescricao(rs.getString("descricao"));
                todo.setConcluida(rs.getBoolean("concluida"));
                todos.add(todo);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao listar todos: " + e.getMessage(), e);
        }
        return todos;
    }
}
