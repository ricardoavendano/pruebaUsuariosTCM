package com.co.prueba.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -629636322850605729L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "idusuario")
	private Long idUsuario;

	@Column(nullable = false, name = "nombre")
	private String nombre;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "idUsuariopk")
	private List<Tarea> tareaList;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Tarea> getTareaList() {
		return tareaList;
	}

	public void setTareaList(List<Tarea> tareaList) {
		this.tareaList = tareaList;
	}

	@Override
	public int hashCode() {
		return Objects.hash(idUsuario, nombre, tareaList);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Usuario other = (Usuario) obj;
		return Objects.equals(idUsuario, other.idUsuario) && Objects.equals(nombre, other.nombre)
				&& Objects.equals(tareaList, other.tareaList);
	}

	@Override
	public String toString() {
		return "Usuario [idUsuario=" + idUsuario + ", nombre=" + nombre + ", tareaList=" + tareaList + "]";
	}

}
