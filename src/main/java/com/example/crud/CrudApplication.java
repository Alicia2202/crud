package com.example.crud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//es el punto de arranque de la aplicación hace que Spring escanee el paquete com.example.crud, encuentre tu identidad, tu repositorio y tu controlador, los conecte entre sí y levante un servidor web en el puerto 8080. por eso todas las clases deben estar en ese paquete o subpaquetes
@SpringBootApplication
public class CrudApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudApplication.class, args);
	}

}
