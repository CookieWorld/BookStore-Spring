package com.spring.store.controller;

import com.spring.store.model.Book;
import com.spring.store.model.OrderLine;
import com.spring.store.repos.BookRepo;
import com.spring.store.repos.OrderLineRepo;
import com.spring.store.repos.OrderRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
public class MainController {
    @Autowired
    private BookRepo bookRepo;

    @Autowired
    private OrderLineRepo orderLineRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String index(@PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable, Model model) {

        Page<Book> books = bookRepo.findAll(pageable);
        model.addAttribute("books", books);
        model.addAttribute("url", "/");

        return "index";
    }

    @GetMapping("/main")
    public String main(
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {
        Page<Book> books;

        if (!filter.isEmpty()) {
            books = bookRepo.findByAuthorContainsOrNameContains(filter, filter, pageable);
        } else books = bookRepo.findAll(pageable);

        model.addAttribute("books", books);
        model.addAttribute("filter", filter);
        model.addAttribute("url", "/main");

        return "main";
    }

    @PostMapping("/")
    public String edit(@RequestParam("bookId") Book book, @RequestParam Integer quantity, @RequestParam Integer price) {
        Optional<Book> byId = bookRepo.findById(book.getId());
        Book book1 = byId.get();
        book1.setQuantity(quantity);
        book1.setPrice(price);
        bookRepo.save(book1);
        return "redirect:/main";
    }

    @PostMapping("/remove")
    public String remove(@RequestParam("bookId") Book book) {
        List<OrderLine> allOrderLinesByBookId = orderLineRepo.findAllByBook_Id(book.getId());
        for(OrderLine orderLine : allOrderLinesByBookId) {
            orderLine.setAuthor(book.getAuthor());
            orderLine.setName(book.getName());
            orderLine.setPrice(book.getPrice());
            orderLine.setBook(null);
        }
        bookRepo.deleteById(book.getId());
        return "redirect:/main";
    }

    @GetMapping("/search")
    public String search(
            @RequestParam(required = false, defaultValue = "") String filter,
            @PageableDefault(sort = {"id"}, direction = Sort.Direction.ASC) Pageable pageable,
            Model model) {
        Page<Book> books;

        if (!filter.isEmpty()) {
            books = bookRepo.findByAuthorContainsOrNameContains(filter, filter, pageable);
        } else books = bookRepo.findAll(pageable);

        model.addAttribute("books", books);
        model.addAttribute("url", "/search");

        return "index";
    }

    @PostMapping("/main")
    public String add(
            @Valid Book book,
            BindingResult bindingResult,
            Model model,
            @RequestParam("file") MultipartFile file) throws IOException {
        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("book", book);
        } else {
            if (file != null) {
                File uploadDir = new File(uploadPath);

                if (!uploadDir.exists()) {
                    uploadDir.mkdir();
                }
                String uuidFile = UUID.randomUUID().toString();
                String resultFilename = uuidFile + "." + file.getOriginalFilename();

                file.transferTo(new File(uploadPath + "/" + resultFilename));

                book.setFilename(resultFilename);
            }

            model.addAttribute("book", null);

            bookRepo.save(book);
        }
        Iterable<Book> books = bookRepo.findAll();

        model.addAttribute("books", books);

        return "redirect:/main";
    }
}