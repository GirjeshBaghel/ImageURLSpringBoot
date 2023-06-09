package com.project.repository;

import com.project.model.Image;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImageRepository extends CrudRepository<Image, Long> {

	public List<Image> findByContent(String content);
}
