package test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import test.repository.ProductRepository;
import test.service.ExportExcel;
import test.service.ProductNotFoundException;
import test.product.Product;
import test.service.ProductService;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    @GetMapping("/product/edit/{id}")
    public String showEditForm(@PathVariable(name = "id") Long id,Model model ,RedirectAttributes ra){
        try {
            Product product = service.get(id);
            model.addAttribute("product",product);
            model.addAttribute("pageTitle", "Edit Product(ID : "+ id +") ");
            return "product_new";
        } catch (ProductNotFoundException e) {
            ra.addFlashAttribute("message",e.getMessage());
            return "redirect:/product";
        }
    }
    @GetMapping("/product/delete/{id}")
    public String deleteProduct(@PathVariable(name = "id") Long id,RedirectAttributes ra) {
        try {
            service.delete(id);
            ra.addFlashAttribute("message","Product ID " +id+ "đã bị xóa  ");
        } catch (ProductNotFoundException e) {
            ra.addFlashAttribute("message", e.getMessage());
        }
        return "redirect:/product";
    }
    @GetMapping("/product/createExcel")
    public ResponseEntity<InputStreamResource> getFile() {
        String filename = "products.xlsx";
        InputStreamResource file = new InputStreamResource(service.load());

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
