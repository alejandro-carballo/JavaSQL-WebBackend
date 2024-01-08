package com.example.accessingdatamysql.Controller;

import com.example.accessingdatamysql.Classes.*;
import com.example.accessingdatamysql.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Controller	// This means that this class is a Controller
@RequestMapping(path="/book") // This means URL's start with /comment (after Application path)
public class BookController {
	@Autowired// This means to get the bean called userRepository
			   // Which is auto-generated by Spring, we will use it to handle the data
	private BookRepository bookRepository;

	@Autowired
	private AdminRepository adminRepository;

	@Autowired
	private RatingRepository ratingRepository;

	@Autowired
	private AuthorRepository authorRepository;

	@Autowired
	private ShoppingCartRepository shoppingCartRepository;

	@Autowired
	private WishlistRepository wishListRepository;

	@PostMapping(path="/add") // Map ONLY POST Requests
	public @ResponseBody String addNewComment (@RequestParam String username
			, @RequestParam String password, @RequestParam Integer ISBN,
			@RequestParam String bookname, @RequestParam String description,
			@RequestParam Double price, @RequestParam String authorFirstName,
			@RequestParam String authorLastName, @RequestParam String genre,
			@RequestParam String publisher, @RequestParam Integer yearPublished,
			@RequestParam Integer copiesSold) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		if(!(adminRepository.findByName(username).isPresent())) {
			return "Admin name does not exist";
		}
		Admin n = adminRepository.findByName(username).get();
		if(!password.equals(n.getPassword())){
			return "incorrect password";
		}

		Book x = new Book();
		x.setId(ISBN); //set id as ISBN
		x.setName(bookname);
		x.setDescription(description);
		x.setPrice(price);
		x.setAuthorFirstName(authorFirstName);
		x.setAuthorLastName(authorLastName);
		x.setGenre(genre);
		x.setPublisher(publisher);
		x.setYearPublished(yearPublished);
		x.setCopiesSold(copiesSold);

		bookRepository.save(x);
		return "success";
	}

	@DeleteMapping(path="/remove") // Map ONLY POST Requests
	public @ResponseBody String removeComment (@RequestParam Integer id) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		bookRepository.deleteById(id);
		return "removed";
	}

	@DeleteMapping(path="/removeAll") // Map ONLY POST Requests
	public @ResponseBody String removeAllComments () {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		bookRepository.deleteAll();
		return "removed all entries";
	}

	@PutMapping(path="/update") // Map ONLY POST Requests
	public @ResponseBody String updateComment (@RequestParam String username
			, @RequestParam String password, @RequestParam Integer ISBN,
			@RequestParam String bookname, @RequestParam String description,
			@RequestParam Double price, @RequestParam String authorFirstName, @RequestParam String authorLastName,
			@RequestParam String genre,@RequestParam String publisher,
			@RequestParam Integer yearPublished, @RequestParam Integer copiesSold) {
		// @ResponseBody means the returned String is the response, not a view name
		// @RequestParam means it is a parameter from the GET or POST request
		Book x = bookRepository.findById(ISBN).get();
		x.setId(ISBN); //set id as ISBN
		x.setName(bookname);
		x.setDescription(description);
		x.setPrice(price);
		x.setAuthorFirstName(authorFirstName);
		x.setAuthorLastName(authorLastName);
		x.setGenre(genre);
		x.setPublisher(publisher);
		x.setYearPublished(yearPublished);
		x.setCopiesSold(copiesSold);

		bookRepository.save(x);
		return "updated";

	}

	@GetMapping(path="/all")
	public @ResponseBody Iterable<Book> getAllBooks() {
		// This returns a JSON or XML with the users
		return bookRepository.findAll();
	}

	@GetMapping(path="/top10")
	public @ResponseBody Iterable<Book> getTop10() {
		// This returns a JSON or XML with the users
		Sort sort = Sort.by("copiesSold").descending();
		return bookRepository.findTop10ByCopiesSoldGreaterThan(0, sort);
	}

	@GetMapping(path="/allGenre")
	public @ResponseBody Iterable<Book> getBooksGenre(@RequestParam String genre) {
		// This returns a JSON or XML with the users
		return bookRepository.findAllByGenre(genre);
	}

	@GetMapping(path="/shoppingCart")
	public @ResponseBody ArrayList<Book> getShoppingCart(@RequestParam String username) {
		// This returns a JSON or XML with the users
		ArrayList<Book> list = new ArrayList();
		ShoppingCart cart = shoppingCartRepository.findByUser(username).get();

		ArrayList<Integer> isbn = cart.getBook();
		int count = isbn.size();
		Boolean found = false;
		for(int i = 0; i < count; i++){
			list.add(bookRepository.findById(isbn.get(i)).get());
		}

		return list;
	}

	@GetMapping(path="/wishlist")
	public @ResponseBody Iterable<Book> getWishlist(@RequestParam String username) {
		// This returns a JSON or XML with the users
		ArrayList<Book> list = new ArrayList();
		Wishlist wish = wishListRepository.findByUser(username).get();

		ArrayList<Integer> isbn = wish.getBook();
		int count = isbn.size();
		Boolean found = false;
		for(int i = 0; i < count; i++){
			list.add(bookRepository.findById(isbn.get(i)).get());
		}

		return list;
	}

	@GetMapping(path="/rated")
	public @ResponseBody Iterable<Book> ratedBooks(@RequestParam Integer Rating) {
		// This returns a JSON or XML with the users
		Iterable<Book> bookList = bookRepository.findAll();
		Iterator<Book> bookIterator = bookList.iterator();
		Iterable<Rating> ratings = ratingRepository.findAll();
		Book n = new Book();
		Double stars = 0.0;
		Integer amount = 0;
		while(bookIterator.hasNext()){
			n = bookIterator.next();
			ratings = ratingRepository.findAllByBook(n.getName());
			Iterator<Rating> ratingIterator = ratings.iterator();
			if (!ratingIterator.hasNext()){
				bookIterator.remove();
			} else {
				stars = 0.0;
				amount = 0;
				while(ratingIterator.hasNext()){
					Rating rating = ratingIterator.next();
					stars += rating.getStars();
					amount++;
				}
				stars = stars/amount;
				if(stars < Rating){
					bookIterator.remove();
				}
			}
		}
		return bookList;
	}
	@GetMapping(path="/authorFind")
	public @ResponseBody Book AuthorSearch(@RequestParam String firstName, @RequestParam String lastName) {
		// This returns a JSON or XML with the users
		return bookRepository.findByFirstnameAndLastname(firstName, lastName).get();
	}

	@GetMapping(path="/allFrom")
	public @ResponseBody Iterable<Book> getXAmountFromYPos(@RequestParam Integer x, @RequestParam Integer y) {
		Iterable<Book> bookList = bookRepository.findAll();
		int count = 8;//(int) bookRepository.count();
		int counter = 1;
		Iterator<Book> bookListed = bookList.iterator();
		bookListed.next();
		for (int i = 1; i <= count; i++) {
			if (i < x || counter > y) {
				bookListed.remove();
				if (bookListed.hasNext()) {
					bookListed.next();
				}
			} else {
				counter += 1;
				if (bookListed.hasNext()) {
					bookListed.next();
				}
			}
		}
		return bookList;
	}

	@GetMapping(path="/isbnFind")
	public @ResponseBody Book IsbnSearch(@RequestParam Integer isbn) {
		// This returns a JSON or XML with the users
		return bookRepository.findById(isbn).get();
	}
}