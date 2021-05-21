package com.spring.store.controller;

import com.spring.store.model.Book;
import com.spring.store.repos.BookRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Map;
import java.util.UUID;

@Controller
public class MainController {

    @Autowired
    private BookRepo bookRepo;

    @Value("${upload.path}")
    private String uploadPath;

    @GetMapping("/")
    public String index(Model model) {

        Iterable<Book> books = bookRepo.findAll();
        model.addAttribute("books", books);

        return "index";
    }

    @GetMapping("/main")
    public String main(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Book> books;

        if (!filter.isEmpty()) {
            books = bookRepo.findByAuthorContainsOrNameContains(filter, filter);
        } else books = bookRepo.findAll();

        model.addAttribute("books", books);
        model.addAttribute("filter", filter);

        return "main";
    }

    @GetMapping("/search")
    public String search(@RequestParam(required = false, defaultValue = "") String filter, Model model) {
        Iterable<Book> books;

        if (!filter.isEmpty()) {
            books = bookRepo.findByAuthorContainsOrNameContains(filter, filter);
        } else books = bookRepo.findAll();

        model.addAttribute("books", books);

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