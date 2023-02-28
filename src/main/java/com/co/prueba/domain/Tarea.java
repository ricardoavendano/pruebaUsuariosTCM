package com.co.prueba.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "tarea")
public class Tarea implements Serializable {

	private static final long serialVersionUID = -5936329650033303260L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(nullable = false, name = "idtarea")
	private Long idTarea;

	@Column(nullable = false, name = "estado")
	private Long estado;

	@Column(nullable = false, name = "fechaejecucion")
	private Date fechaEjecucion;

	@Column(nullable = false, name = "fechacierre")
	private Date fechaCierre;

	@JoinColumn(name = "IDUSUARIOPK", referencedColumnName = "IDUSUARIO", nullable = false)
	@ManyToOne(optional = false)
	private Usuario idUsuariopk;

	public Long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	public Long getEstado() {
		return estado;
	}

	public void setEstado(Long estado) {
		this.estado = estado;
	}

	public Date getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(Date fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public Date getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(Date fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Usuario getIdUsuariopk() {
		return idUsuariopk;
	}

	public void setIdUsuariopk(Usuario idUsuariopk) {
		this.idUsuariopk = idUsuariopk;
	}

	@Override
	public int hashCode() {
		return Objects.hash(estado, fechaCierre, fechaEjecucion, idTarea, idUsuariopk);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Tarea other = (Tarea) obj;
		return Objects.equals(estado, other.estado) && Objects.equals(fechaCierre, other.fechaCierre)
				&& Objects.equals(fechaEjecucion, other.fechaEjecucion) && Objects.equals(idTarea, other.idTarea)
				&& Objects.equals(idUsuariopk, other.idUsuariopk);
	}

	@Override
	public String toString() {
		return "Tarea [idTarea=" + idTarea + ", estado=" + estado + ", fechaEjecucion=" + fechaEjecucion
				+ ", fechaCierre=" + fechaCierre + ", idUsuariopk=" + idUsuariopk + "]";
	}

}
