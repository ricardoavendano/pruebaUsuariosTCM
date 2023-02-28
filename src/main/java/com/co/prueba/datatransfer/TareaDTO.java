package com.co.prueba.datatransfer;

public class TareaDTO {

	private Long idTarea;
	private String estado;
	private String fechaEjecucion;
	private String fechaCierre;
	private Long diasRetraso;
	private UsuarioDTO idUsuario;

	public Long getIdTarea() {
		return idTarea;
	}

	public void setIdTarea(Long idTarea) {
		this.idTarea = idTarea;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getFechaEjecucion() {
		return fechaEjecucion;
	}

	public void setFechaEjecucion(String fechaEjecucion) {
		this.fechaEjecucion = fechaEjecucion;
	}

	public String getFechaCierre() {
		return fechaCierre;
	}

	public void setFechaCierre(String fechaCierre) {
		this.fechaCierre = fechaCierre;
	}

	public Long getDiasRetraso() {
		return diasRetraso;
	}

	public void setDiasRetraso(Long diasRetraso) {
		this.diasRetraso = diasRetraso;
	}

	public UsuarioDTO getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(UsuarioDTO idUsuario) {
		this.idUsuario = idUsuario;
	}

}
