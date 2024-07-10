package com.example.LiteraluraChallenge.servicio;

import com.example.LiteraluraChallenge.modelo.Libro;
import com.example.LiteraluraChallenge.repositorio.LibroRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
public class LibroServicio {
    @Autowired
    private LibroRepositorio libroRepositorio;

    public List<Libro> obtenerTodosLosLibros() {
        return libroRepositorio.findAll();
    }

    public List<Libro> obtenerLibrosPorIdioma(String idioma) {
        return libroRepositorio.findByIdioma(idioma);
    }

    public Libro guardarLibro(Libro libro) {
        return libroRepositorio.save(libro);
    }

    public boolean existeLibroPorTitulo(String titulo) {
        return libroRepositorio.findByTitulo(titulo).isPresent();
    }

    public List<Map<String, Object>> buscarLibrosPorTitulo(String titulo) {
        String url = "https://gutendex.com/books?title=" + titulo;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("results");
    }

    public List<Map<String, Object>> buscarLibrosPorAutor(String autor) {
        String url = "https://gutendex.com/books?author=" + autor;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("results");
    }

    public List<Map<String, Object>> buscarLibrosPorIdioma(String idioma) {
        String url = "https://gutendex.com/books?languages=" + idioma;
        RestTemplate restTemplate = new RestTemplate();
        Map<String, Object> response = restTemplate.getForObject(url, Map.class);
        return (List<Map<String, Object>>) response.get("results");
    }
}
