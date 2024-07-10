package com.example.LiteraluraChallenge;

import com.example.LiteraluraChallenge.modelo.Libro;
import com.example.LiteraluraChallenge.servicio.LibroServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class LiteraluraChallengeApplication implements CommandLineRunner {

	@Autowired
	private LibroServicio libroServicio;

	public static void main(String[] args) {
		SpringApplication.run(LiteraluraChallengeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("Seleccione una opción:");
			System.out.println("1. Listar libros por título");
			System.out.println("2. Listar libros por autor");
			System.out.println("3. Listar libros por idioma");
			System.out.println("4. Listar libros registrados");
			System.out.println("5. Listar libros registrados por idioma");
			System.out.println("6. Salir");

			int eleccion = scanner.nextInt();
			scanner.nextLine();  // Consume newline

			switch (eleccion) {
				case 1:
					System.out.print("Ingrese el título del libro: ");
					String titulo = scanner.nextLine();
					List<Map<String, Object>> librosPorTitulo = libroServicio.buscarLibrosPorTitulo(titulo);
					registrarLibro(librosPorTitulo, scanner);
					break;
				case 2:
					System.out.print("Ingrese el autor del libro: ");
					String autor = scanner.nextLine();
					List<Map<String, Object>> librosPorAutor = libroServicio.buscarLibrosPorAutor(autor);
					registrarLibro(librosPorAutor, scanner);
					break;
				case 3:
					System.out.print("Ingrese el idioma del libro: ");
					String idioma = scanner.nextLine();
					List<Map<String, Object>> librosPorIdioma = libroServicio.buscarLibrosPorIdioma(idioma);
					registrarLibro(librosPorIdioma, scanner);
					break;
				case 4:
					List<Libro> todosLosLibros = libroServicio.obtenerTodosLosLibros();
					todosLosLibros.forEach(System.out::println);
					break;
				case 5:
					System.out.print("Ingrese el idioma: ");
					idioma = scanner.nextLine();
					List<Libro> librosPorIdiomaRegistrados = libroServicio.obtenerLibrosPorIdioma(idioma);
					librosPorIdiomaRegistrados.forEach(System.out::println);
					break;
				case 6:
					System.out.println("Saliendo...");
					return;
				default:
					System.out.println("Opción no válida.");
			}
		}
	}

	private void registrarLibro(List<Map<String, Object>> libros, Scanner scanner) {
		if (libros.isEmpty()) {
			System.out.println("No se encontraron libros.");
			return;
		}

		for (int i = 0; i < libros.size(); i++) {
			Map<String, Object> libro = libros.get(i);
			System.out.println((i + 1) + ". " + libro.get("title") + " - " + libro.get("authors"));
		}

		System.out.print("Seleccione un libro para registrar (0 para cancelar): ");
		int seleccion = scanner.nextInt();
		scanner.nextLine();  // Consume newline

		if (seleccion > 0 && seleccion <= libros.size()) {
			Map<String, Object> libroSeleccionado = libros.get(seleccion - 1);
			String titulo = (String) libroSeleccionado.get("title");

			if (libroServicio.existeLibroPorTitulo(titulo)) {
				System.out.println("El libro ya está registrado.");
				return;
			}

			Libro libro = new Libro();
			libro.setTitulo(titulo);
			libro.setAutor(((List<Map<String, String>>) libroSeleccionado.get("authors")).get(0).get("name"));
			libro.setIdioma(((List<String>) libroSeleccionado.get("languages")).get(0));
			libroServicio.guardarLibro(libro);
			System.out.println("Libro registrado exitosamente.");
		} else {
			System.out.println("Registro cancelado.");
		}
	}
}
