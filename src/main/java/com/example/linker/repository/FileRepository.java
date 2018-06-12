package com.example.linker.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.linker.model.File;

public interface FileRepository extends JpaRepository<File, Integer>{

}