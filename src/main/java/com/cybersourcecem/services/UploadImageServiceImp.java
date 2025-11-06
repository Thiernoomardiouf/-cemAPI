package com.cybersourcecem.services;

import com.cybersourcecem.entities.ProductImage;
import com.cybersourcecem.repositories.ProductImageRepository;
import com.cybersourcecem.repositories.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.apache.commons.                                                                                                                                                                                                                           io.FilenameUtils;

@Service
public class UploadImageServiceImp implements UploadImageService {
    private final ProductImageRepository repository;
    private final ProductRepository productRepository;
    public UploadImageServiceImp(
            final ProductImageRepository repository,
            final ProductRepository productRepository
    ){
        this.repository = repository;
        this.productRepository = productRepository;
    }

    @Override
    public void uploadImage(MultipartFile file, long productId)  throws IOException {
        // LOGIC
        UUID uuid = UUID.randomUUID();
        String fileName = StringUtils.cleanPath(uuid.toString()+"."+getExtensionByApacheCommonLib(file.getOriginalFilename()));
        ProductImage image = new ProductImage();

        image.setImage(file.getBytes());
        image.setSize(file.getSize());
        image.setType(file.getContentType());
        image.setName(fileName);
        image.setProduct(this.productRepository.getReferenceById(productId));

        repository.save(image);
    }

    private String getExtensionByApacheCommonLib(String filename) {

        return FilenameUtils.getExtension(filename);
    }

    @Override
    public Stream<ProductImage> loadAllImagesByProduct(long productId) {
        List<ProductImage> list =  new ArrayList<>();
        for(ProductImage pImg: this.repository.findAll())
            if(pImg.getProduct().getId() == productId) list.add(pImg);
        return list.stream();
    }

    @Override
    public ProductImage getOneImage(long id) {
        return this.repository.getReferenceById(id);
    }
}
