package test.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import test.product.Product;
import test.repository.ProductRepository;

import java.io.ByteArrayInputStream;
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
    public Product get(Long id) throws ProductNotFoundException {
       Optional<Product> result= repo.findById(id);
       if(result.isPresent()){
           return result.get();
       }
       throw new ProductNotFoundException("Khong tim thay product voi ID "+id);
    }

    public void delete(Long id) throws ProductNotFoundException {
        Long count = repo.countById(id);
        if(count == null || count == 0){
            throw new ProductNotFoundException("Khong tim thay product voi ID "+id);
        }
        repo.deleteById(id);
    }
    public ByteArrayInputStream load(){
        List<Product> products = repo.findAll();
        ByteArrayInputStream in = ExportExcel.productToExcel(products);
        return  in;
    }

}
