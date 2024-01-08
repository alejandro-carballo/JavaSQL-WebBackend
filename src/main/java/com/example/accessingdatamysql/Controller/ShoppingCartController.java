package com.example.accessingdatamysql.Controller;

import com.example.accessingdatamysql.Classes.Book;
import com.example.accessingdatamysql.Classes.User;
import com.example.accessingdatamysql.Classes.Wishlist;
import com.example.accessingdatamysql.Repositories.UserRepository;
import com.example.accessingdatamysql.Repositories.BookRepository;
import com.example.accessingdatamysql.Repositories.ShoppingCartRepository;
import com.example.accessingdatamysql.Repositories.WishlistRepository;
import com.example.accessingdatamysql.Classes.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;

@Controller
@RequestMapping (path = "/shoppingCart")

public class ShoppingCartController {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ShoppingCartRepository shoppingCartRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    @PostMapping(path = "/add")
    public @ResponseBody String addNewCart (@RequestParam String username,
        @RequestParam Integer isbn) {
        if(userRepository.findByName(username).isPresent()){
            if(shoppingCartRepository.findByUser(username).isPresent()){
                ShoppingCart cart = shoppingCartRepository.findByUser(username).get();
                cart.setUser(username);
                cart.addBook(isbn);
                shoppingCartRepository.save(cart);
            } else {
                ShoppingCart cart = new ShoppingCart();
                cart.setUser(username);
                cart.addBook(isbn);
                shoppingCartRepository.save(cart);
            }
            return "saved";
        } else return "User doesn't exist";
    }

    @PutMapping(path = "/removeAll")
    public @ResponseBody String removeAllBooks (@RequestParam String username){
        ShoppingCart cart = shoppingCartRepository.findByUser(username).get();
        cart.removeAllBooks();
        shoppingCartRepository.save(cart);
        return "removed all entries";
    }

    @GetMapping (path = "/all")
    public @ResponseBody Iterable<ShoppingCart> getBooks (){
       return shoppingCartRepository.findAll();
    }

    @PostMapping (path = "/addBook")
    public @ResponseBody String addBook (@RequestParam String username, @RequestParam Integer isbn){
        if(userRepository.findByName(username).isPresent()){
            if (!shoppingCartRepository.findByUser(username).isPresent()) {
                ShoppingCart cart = shoppingCartRepository.findByUser(username).get();
                cart.addBook(isbn);
                shoppingCartRepository.save(cart);
                return "saved";
            } else {
                ShoppingCart cart = new ShoppingCart();
                Book x = bookRepository.findById(isbn).get();
                cart.addBook(isbn);
                shoppingCartRepository.save(cart);
                return "saved";
            }
        } else return "user doesn't exist";
    }

    @DeleteMapping (path = "/deleteBook")
    public @ResponseBody String deleteBooks (@RequestParam String username,
        @RequestParam String password, @RequestParam int isbn) {
        User n = userRepository.findByName(username).get();
        if (password != n.getPassword()) {
            return "Incorrect password";
        }

        if (!shoppingCartRepository.findByUser(username).isPresent()) {
            ShoppingCart cart = shoppingCartRepository.findByUser(username).get();
            cart.removeBook(isbn);
        }

            return "deleted";
    }
    @PutMapping (path = "/addToCart")
    public @ResponseBody String addCart (@RequestParam String username,
                                             @RequestParam String password, @RequestParam int isbn, @RequestParam ArrayList booksList) {
        User n = userRepository.findByName(username).get();
        if (password != n.getPassword()) {
            return "Incorrect password";
        }

        if (!shoppingCartRepository.findByUser(username).isPresent()) {
            Wishlist wish = wishlistRepository.findByUser(username).get();
            ShoppingCart cart = shoppingCartRepository.findByUser(username).get();
            wish.removeBook(isbn);
            cart.addBook(isbn);
            shoppingCartRepository.save(cart);
        }

        return "Added to Cart";
    }
}
