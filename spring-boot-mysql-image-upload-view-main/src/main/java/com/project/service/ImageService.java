package com.project.service;

import com.project.model.Image;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ImageService {
    public Image create(Image image);
    public List<Image> viewAll();
  
   //	List<Image> findByName(String name);
    String deleteImage(long id);
	Image updateImage(long id,Image image);
	Image compareData(long id, Image image);
	public List<Image> findByContent(String content);
	
	
}
