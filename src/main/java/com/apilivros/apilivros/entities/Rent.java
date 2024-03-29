package com.apilivros.apilivros.entities;

import java.time.Instant;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_rent")
public class Rent {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private Double price;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant initDate;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant devolutionDate;
	@Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
	private Instant expectedReturnDate; 
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@ManyToOne
	@JoinColumn(name = "book_id")
	private Book book;
	
	private boolean devolution;
	
	
	public Rent() {
	}

	public Rent(Long id, Double price, Instant initDate, Instant devolutionDate, User user, Book book,
			boolean devolution) {
		this.id = id;
		this.price = price;
		this.initDate = initDate;
		this.devolutionDate = devolutionDate;
		this.user = user;
		this.book = book;
		this.devolution = devolution;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Instant getInitDate() {
		return initDate;
	}

	public void setInitDate(Instant initDate) {
		this.initDate = initDate;
	}

	public Instant getDevolutionDate() {
		return devolutionDate;
	}

	public void setDevolutionDate(Instant devolutionDate) {
		this.devolutionDate = devolutionDate;
	}

	public User getUser() {
		return user;
	}
	
	public void setUser(User user) {
		this.user = user;
	}
	
	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}
	
	public Instant getExpectedReturnDate() {
		return expectedReturnDate;
	}

	public void setExpectedReturnDate(Instant expectedReturnDate) {
		this.expectedReturnDate = expectedReturnDate;
	}

	public boolean isDevolution() {
		return devolution;
	}

	public void setDevolution(boolean devolution) {
		this.devolution = devolution;
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
		Rent other = (Rent) obj;
		return Objects.equals(id, other.id);
	}
	
	 public void applyFine(double amount) {
		 price = amount;
	 }
}