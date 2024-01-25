package com.apilivros.apilivros.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_book")
public class Book {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String title;
	private Long isbn;
	private Long yearPublication;
	
	@ManyToOne
	@JoinColumn(name = "publisher_id")
	private Publisher publisher;
	
	@ManyToOne
	@JoinColumn(name = "author_id")
	private Author author;
	
	@ManyToOne
	@JoinColumn(name = "rent_id")
	private Rent rent;
	
	public Book() {
	}

	public Book(Long id, String title, Long isbn, Long yearPublication, Publisher publisher, Author author, Rent rent) {
		this.id = id;
		this.title = title;
		this.isbn = isbn;
		this.yearPublication = yearPublication;
		this.publisher = publisher;
		this.author = author;
		this.rent = rent;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Long getIsbn() {
		return isbn;
	}

	public void setIsbn(Long isbn) {
		this.isbn = isbn;
	}

	public Long getYearPublication() {
		return yearPublication;
	}

	public void setYearPublication(Long yearPublication) {
		this.yearPublication = yearPublication;
	}

	public Publisher getPublisher() {
		return publisher;
	}

	public void setPublisher(Publisher publisher) {
		this.publisher = publisher;
	}

	public Author getAuthor() {
		return author;
	}

	public void setAuthor(Author author) {
		this.author = author;
	}
	
	public Rent getRent() {
		return rent;
	}

	public void setRent(Rent rent) {
		this.rent = rent;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Book other = (Book) obj;
		return Objects.equals(id, other.id);
	}
}
