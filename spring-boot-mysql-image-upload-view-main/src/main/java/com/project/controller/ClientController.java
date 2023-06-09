package com.project.controller;

import com.project.model.Image;
import com.project.service.ImageService;
import com.project.service.ImageServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.sql.rowset.serial.SerialException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Controller
public class ClientController {
    @Autowired
    private ImageServiceImpl imageService;
    
    @RequestMapping(path = "/addImage", method = RequestMethod.POST, consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<String> addImagePost(HttpServletRequest request,@RequestParam("image") MultipartFile file ,@RequestParam("content") String content, @RequestParam("heading") String heading) throws IOException, SerialException, SQLException
    {
        byte[] bytes = file.getBytes();
        Blob blob = new javax.sql.rowset.serial.SerialBlob(bytes);
        Image image = new Image();
        image.setImage(blob);
        image.setContent(content);
        image.setHeading(heading);
        imageService.create(image);
        System.out.print(image);
        return ResponseEntity.ok("Image Saved Successfully");
    }
    
    @PostMapping("/compareContent")
   public ResponseEntity<String> compareContent(@RequestParam("id") long id ,@RequestParam("content") String content) throws InterruptedException, ExecutionException
   {
    	Image existingImage = imageService.viewById(id);
        String oldContent = existingImage.getContent();
        
        if (oldContent.equals(content)) {
            return ResponseEntity.ok("Pass : "+id);
        } else {
            return ResponseEntity.ok("Fail : "+id);
        }
   }
    
    @GetMapping("/images/{id}")
    public ResponseEntity<byte[]> getImageBytes(@PathVariable("id") long id, HttpServletRequest request) throws IOException, SQLException, InterruptedException, ExecutionException {
        Image image = imageService.viewById(id);
        byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
        String content = image.getContent();
        String heading = image.getHeading();
       
        String baseUrl = request.getRequestURL().toString();
        
        String imageUrl = baseUrl.substring(0, baseUrl.indexOf("/images")) + "/images/" + id ;
        System.out.print(" URL "+imageUrl+",  Content : "+content+",   Heading  : "+heading);
        //String responseBody = "URL: " + imageUrl + ", Content: " + content + ", Heading: " + heading;
       // return ResponseEntity.ok().body(responseBody);
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
    }
  
   

	@DeleteMapping("/deleteImage/{id}")
	public String deleteImage(@PathVariable("id") long id) {
		
		return imageService.deleteImage(id);
	}
	
	@PutMapping("updateImage/{id}")
	public ResponseEntity<Image> updaUsers(@PathVariable long id,@RequestBody Image image){
		
		return new ResponseEntity<Image>(imageService.updateImage(id,image),HttpStatus.OK);
	}
    
    
    @GetMapping("/Allimages")
    public ResponseEntity<byte[]> getAllImageBytes(HttpServletRequest request) throws IOException, SQLException {
        List<Image> images = imageService.viewAll();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        // Iterate over the images and write the bytes to the ByteArrayOutputStream
        for (Image image : images) {
            byte[] imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
            baos.write(imageBytes);
        }

        // Get the base URL of the request
        String baseUrl = request.getRequestURL().toString();

        // Remove the endpoint part of the URL ("/Allimages") to get the base URL
        String baseUrlWithoutEndpoint = baseUrl.substring(0, baseUrl.lastIndexOf("/"));

        // Construct the complete image URL
        String imageUrl = baseUrlWithoutEndpoint + "/images";
        System.out.print("URL "+imageUrl);
        byte[] allImageBytes = baos.toByteArray();
        

        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(allImageBytes);
    }
    

//  @GetMapping("/imagesByName/{name}")
//  public ResponseEntity<byte[]> getImageBytes(@PathVariable("name") String name, HttpServletRequest request) throws IOException, SQLException {
//      Image image = (Image) imageService.findByName(name);
//       if (image == null) {
//      	 System.out.print("Image not Found");
//          return ResponseEntity.notFound().build();
//      }
//
//      byte[]  imageBytes = image.getImage().getBytes(1, (int) image.getImage().length());
//
//      // Get the base URL of the request
//      String baseUrl = request.getRequestURL().toString();
//
//      // Construct the complete image URL
//      String imageUrl = baseUrl.substring(0, baseUrl.indexOf("/images")) + "/images/" + name;
//      System.out.println("Image URL"+imageUrl);
//
//      return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(imageBytes);
//  }
  

}
