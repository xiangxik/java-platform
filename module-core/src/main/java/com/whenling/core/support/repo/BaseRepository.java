package com.whenling.core.support.repo;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BaseRepository<T, I extends Serializable> extends JpaRepository<T, I> {

}
