package com.firma.psp.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "privileges")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Privilege {

	@Id
	@Column(name = "privilege_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;

	@Column(name = "name")
	private String name;

	@ManyToMany(mappedBy = "privileges")
	private List<Role> roles;

}
