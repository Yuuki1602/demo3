package test.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.product.Product;
import test.repository.ProductRepository;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repo;

    public List<Product> listAll(){
        return (List<Product>) repo.findAll();
    }
    public void save(Product product){
        repo.save(product);
    }
    public Product get(Long id) {
        return repo.findById(id).get();
    }

    public void delete(Long id){
        repo.deleteById(id);
    }

}
