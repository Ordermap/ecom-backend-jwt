package com.priyanka.Ecomwithjwt.service;

import com.priyanka.Ecomwithjwt.entity.ImageModel;
import com.priyanka.Ecomwithjwt.entity.Product;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
class ProductServiceTest {

    @Autowired
    private ProductService productService;
    @Test
    void addNewProduct() {

        Product product=new Product();
        product.setProductName("test product");
        MockMultipartFile multipartFilesmock=new MockMultipartFile("file", "orig", null, "bar".getBytes());

        MultipartFile[] multipartFiles = {multipartFilesmock};
        try {
            product.setProductImages(uploadImage(multipartFiles));
            productService.addNewProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
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
}