package com.apilivros.apilivros.entities;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
	
	@OneToOne
	@JoinColumn(name = "user_id")
	private User user;
	
	@OneToMany(mappedBy = "rent")
	private List<Book> books = new ArrayList<>();
	
	public Rent() {
	}

	

	public Rent(Long id, Double price, Instant initDate, Instant devolutionDate, User user) {
		super();
		this.id = id;
		this.price = price;
		this.initDate = initDate;
		this.devolutionDate = devolutionDate;
		this.user = user;
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

	public List<Book> getBooks() {
		return books;
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
}