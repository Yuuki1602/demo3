package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import test.service.ProductNotFoundException;
import test.product.Product;
import test.service.ProductService;

import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping("/product")
    public String showProductList(Model model){
        List<Product> listProducts =service.listAll();
        model.addAttribute("listProducts",listProducts);
        return "product";
    }
    @GetMapping("/product/new")
    public String showNewForm(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("pageTitle","Add New Product");
        return "product_new";
    }
    @PostMapping("/product/save")
    public String saveProduct(Product product, RedirectAttributes ra){
        service.save(product);
        ra.addFlashAttribute("message","Lưu thành công !");
        return "redirect:/product";
    }
}
