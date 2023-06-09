package com.project.service;

import com.exception.ResourceNotFoundException;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.project.model.Image;
import com.project.repository.ImageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Service
public class ImageServiceImpl implements ImageService {
    private static final String COLLECTION_NAME = "images";
	@Autowired
    private ImageRepository imageRepository;

//	@Override
//	public Image create(Image image) {
//	    DatabaseReference imagesRef = FirebaseDatabase.getInstance().getReference("image");
//	    String imageId = imagesRef.push().getKey();
//	    image.setId(imageId);
//
//	    imagesRef.child(String.valueOf(imageId)).setValue(image)
//	            .addOnSuccessListener(aVoid -> System.out.println("Image uploaded to Firebase successfully"))
//	            .addOnFailureListener(e -> System.out.println("Failed to upload image to Firebase: " + e.getMessage()));
//
//	    return image;
//	}


	
	
	
//    @Override
//    public Image create(Image image) {
//        return imageRepository.save(image);
//    }
    public Image create(Image image) {
    	
        Image savedImage = imageRepository.save(image);
        Firestore db = FirestoreClient.getFirestore();
        CollectionReference imagesCollection = db.collection(COLLECTION_NAME);
        Long fireId =  savedImage.getId();

        // Save the product data to Firestore
        DocumentReference productDocument = imagesCollection.document();
        productDocument.set(savedImage);
        return savedImage; 
        }

    @Override
    public List<Image> viewAll() {
        return (List<Image>) imageRepository.findAll();
    }

//    @Override
//    public Image viewById(long id) {
//        return imageRepository.findById(id).get();
//    }
    public Image viewById(Long id) throws InterruptedException, ExecutionException
    {
    	Firestore dbFirestore = FirestoreClient.getFirestore();
    	DocumentReference dbDocument = dbFirestore.collection("flipbookproject").document(String.valueOf(id));
        ApiFuture<DocumentSnapshot> future  = dbDocument.get();
        DocumentSnapshot document = future.get();
        Image image;
        if(document.exists())
        {
        	image = document.toObject(Image.class);
        	return image;
        }
        return null;
    }
   	
	@Override
	public String deleteImage(long id) {
		String str =null;
		Optional<Image> img = imageRepository.findById(id);
		if(img.isPresent())
		{
			imageRepository.deleteById(id);
			str = new String("Image Deleted Successfully");
		}
		else
		{
			throw new ResourceNotFoundException("Image ","id",id);
		}
		return str;
	}

	@Override
	public Image updateImage(long id, Image image) {
		Image existingImage = imageRepository.findById(id).orElseThrow(()->
		new ResourceNotFoundException("Image", "id", id));
		
		//we are getting data from client an set in existing passenger
		existingImage.setImage(image.getImage());
		
		imageRepository.save(existingImage);
		return existingImage;
	}

	@Override
	public Image compareData(long id, Image image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Image> findByContent(String content) {
		
		return imageRepository.findByContent(content);
	}

	

	

	
	
	

//	@Override
//	public List<Image> findByName(String name) {
//		
//		List<Image> image =  imageRepository.findByName(name);
//		return image;
//	}
	
}
