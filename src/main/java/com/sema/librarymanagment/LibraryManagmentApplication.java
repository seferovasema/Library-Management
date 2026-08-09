package com.sema.librarymanagment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class LibraryManagmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryManagmentApplication.class, args);
    }

}
