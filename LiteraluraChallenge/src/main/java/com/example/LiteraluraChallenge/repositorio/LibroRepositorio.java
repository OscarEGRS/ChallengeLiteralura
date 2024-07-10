package com.example.LiteraluraChallenge.repositorio;

import com.example.LiteraluraChallenge.modelo.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LibroRepositorio extends JpaRepository<Libro, Long> {
    List<Libro> findByIdioma(String idioma);
    Optional<Libro> findByTitulo(String titulo);
}



