package com.whenling.module.domain.repository;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<T, I extends Serializable> extends JpaRepository<T, I> {

}
