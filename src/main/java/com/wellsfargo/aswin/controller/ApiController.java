package com.wellsfargo.aswin.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.wellsfargo.aswin.entity.Author;
import com.wellsfargo.aswin.entity.BookModal;

/*
 * @Author - Aswin. A
 * @Email - asweinnovate@gmail.com
 */

@RestController
public class ApiController {

	@RequestMapping(value = "/govsjava", method = RequestMethod.POST)
	public ResponseEntity<Object> testFunc(@RequestBody JsonNode books) {
//		BookModal book = new BookModal();
//		book.setTitle(books.get(0).get("title").textValue());
//		book.setAuthor(new Author(books.get(0).get("author").get("firstname").textValue(), books.get(0).get("author").get("lastname").textValue()));
//		book.setId(1001);
//		book.setIsbn("afashj");
		ArrayList<BookModal> responseArrayList = new ArrayList<>();
		System.out.println("SIZE: "+ books.size());
		
		int countRecord = 0;
		for (int c=0; c<books.size(); c++) {
			BookModal book = new BookModal();
			book.setTitle(books.get(0).get("title").textValue());
			book.setAuthor(new Author(books.get(c).get("author").get("firstname").textValue(), books.get(c).get("author").get("lastname").textValue()));
			book.setId(books.get(c).get("id").asInt());
			book.setIsbn(books.get(c).get("isbn").textValue());
			
			if (book.getIsbn().isEmpty()) {
				System.out.println("ISBN is empty !");
			}

			if (book.getTitle().isEmpty()) {
				System.out.println("Title is empty !");
			}

			if (book.getAuthor().getFirstname().isEmpty()) {
				System.out.println("Firstname of Author is empty !");
			}

			String searchString = book.getIsbn();

			int countMatchISBN = -1; // One match will be withitself.

			System.out.println("Started for record: " + countRecord);
			for (int d=0; d<books.size(); d++) {
				if (books.get(d).get("isbn").textValue().equals(searchString)) {
					countMatchISBN++;
				}
//				System.out.println("ISBN duplicates for "+ searchString+": "+countMatchISBN);
			}
			countRecord++;
			
			book.setIsbn(encrypt(book.getIsbn()));
			book.setTitle(encrypt(book.getTitle()));
			book.getAuthor().setFirstname((encrypt(book.getAuthor().getFirstname())));
			book.getAuthor().setLastname((encrypt(book.getAuthor().getLastname())));
			
			responseArrayList.add(book);		
		}
		
		for (BookModal book : responseArrayList) {
			book.setIsbn(encrypt(book.getIsbn()));
			book.setTitle(encrypt(book.getTitle()));
			book.getAuthor().setFirstname((encrypt(book.getAuthor().getFirstname())));
			book.getAuthor().setLastname((encrypt(book.getAuthor().getLastname())));
		}
		for (BookModal book : responseArrayList) {
			book.setIsbn(decrypt(book.getIsbn()));
			book.setIsbn(decrypt(book.getIsbn()));
			book.setTitle(decrypt(book.getTitle()));
			book.setTitle(decrypt(book.getTitle()));
			book.getAuthor().setFirstname((decrypt(book.getAuthor().getFirstname())));
			book.getAuthor().setFirstname((decrypt(book.getAuthor().getFirstname())));
			book.getAuthor().setLastname((decrypt(book.getAuthor().getLastname())));
			book.getAuthor().setLastname((decrypt(book.getAuthor().getLastname())));

		}
		responseArrayList = sortBookById(responseArrayList);
		return ResponseEntity.status(HttpStatus.OK).body(responseArrayList);
	}

	public ArrayList<BookModal> sortBookById(ArrayList<BookModal> books) {
		Collections.sort(books, (b1, b2) -> b1.getId().compareTo(b2.getId()));
		return books;
	}

	public String encrypt(String data) {
		StringBuilder result = new StringBuilder(data.length());
		for (char c : data.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				result.append((char) (c + 1));
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}

	public String decrypt(String data) {
		StringBuilder result = new StringBuilder(data.length());
		for (char c : data.toCharArray()) {
			if (Character.isAlphabetic(c)) {
				result.append((char) (c - 1));
			} else {
				result.append(c);
			}
		}
		return result.toString();
	}
}
