package com.priyanka.Ecomwithjwt.controller;

import com.priyanka.Ecomwithjwt.entity.ImageModel;
import com.priyanka.Ecomwithjwt.entity.Product;
import com.priyanka.Ecomwithjwt.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@Slf4j
public class ProductController {
    @Autowired
    private ProductService productService;
    @PreAuthorize("hasRole('Admin')")
    @PostMapping(value = {"/addNewProduct"}, consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Product addNewProduct(@RequestPart("product") Product product,
                                 @RequestPart("imageFile") MultipartFile[] file) {
        try {
            for (MultipartFile file1:
                  file) {
                log.debug("file1: {}, {}, {}",file1.getName(),file1.getSize(),file1.getBytes());
            }
            Set<ImageModel> images = uploadImage(file);
            product.setProductImages(images);
            return productService.addNewProduct(product);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return null;
        }

    }
    public Set<ImageModel> uploadImage(MultipartFile[] multipartFiles) throws IOException {
        Set<ImageModel> imageModels = new HashSet<>();

        for (MultipartFile file: multipartFiles) {
            log.debug("file: {}",file.getBytes());
            log.debug("file: {}",file.getContentType());
            log.debug("file: {}",file.getOriginalFilename());
            log.debug("file: {}",file.getName());
            log.debug("file: {}",file.getSize());
            ImageModel imageModel = new ImageModel(
                    file.getOriginalFilename(),
                    file.getContentType(),
                    file.getBytes()
            );
            imageModels.add(imageModel);
        }

        return imageModels;
    }
//    @PreAuthorize("hasRole('Admin')")
@GetMapping({"/getAllProducts"})
public List<Product> getAllProducts(@RequestParam(defaultValue = "0") int pageNumber) {
    List<Product> result = productService.getAllProducts(pageNumber);
    System.out.println("Result size is "+ result.size());
   return result;
}

    @PreAuthorize("hasRole('Admin')")
    @DeleteMapping("/deleteProductDetails/{productId}")
    public void deleteProductDetails(@PathVariable("productId") Integer productId){
        System.out.println("productId = " + productId);
        this.productService.deleteProductDetails(productId);

    }
    @GetMapping({"/getProductDetailsById/{productId}"})
    public Product getProductDetailsById(@PathVariable("productId") Integer productId){
        return productService.getProductDetailsById(productId);

    }
    @PreAuthorize("hasRole('User')")
    @GetMapping({"/getProductDetails/{isSingleProductCheckout}/{productId}"})
    public List<Product> getProductDetails(@PathVariable("isSingleProductCheckout") boolean isSingleProductCheckout,@PathVariable("productId") Integer productId){
        return productService.getProductDetails(isSingleProductCheckout,productId);

    }

}
