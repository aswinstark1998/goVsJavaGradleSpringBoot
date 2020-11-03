package com.wellsfargo.aswin.entity;
/*
 * Created by Aswin. A, 03/11/2020
 */

public class BookModal {
	private Integer id;
	private String isbn;
	private String title;
	private Author author;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Author getAuthor() {
		return author;
	}
	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public BookModal() {

	}
	public BookModal(Integer id, String isbn, String title, Author author) {
		super();
		this.id = id;
		this.isbn = isbn;
		this.title = title;
		this.author = author;
	}
}
