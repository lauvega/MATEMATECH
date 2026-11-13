package modelo;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "EVALUACION")
public class evaluacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_evaluacion")
    private Long idEvaluacion;

    @Column(nullable = false, length = 150)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "tiempoLimite", nullable = false)
    private Integer tiempoLimite;

    @Column(name = "fechaCreacion", nullable = false)
    private LocalDate fechaCreacion;

    public evaluacion() {}

    public Long getIdEvaluacion() { return idEvaluacion; }
    public void setIdEvaluacion(Long idEvaluacion) { this.idEvaluacion = idEvaluacion; }
    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Integer getTiempoLimite() { return tiempoLimite; }
    public void setTiempoLimite(Integer tiempoLimite) { this.tiempoLimite = tiempoLimite; }
    public LocalDate getFechaCreacion() { return fechaCreacion; }
    public void setFechaCreacion(LocalDate fechaCreacion) { this.fechaCreacion = fechaCreacion; }
}
