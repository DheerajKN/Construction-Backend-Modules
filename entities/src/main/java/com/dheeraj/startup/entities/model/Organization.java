package com.dheeraj.startup.entities.model;

import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "organization")
public class Organization {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "org_id")
	private UUID orgId;

	@Column(name = "org_name")
	private String orgName;

	@JsonIgnore
	@OneToMany(targetEntity = UserRole.class, mappedBy = "org", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = false)
	private List<UserRole> userRoles;
}
