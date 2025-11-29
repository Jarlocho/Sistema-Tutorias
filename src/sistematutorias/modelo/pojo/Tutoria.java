/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistematutorias.modelo.pojo;

import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author HP
 */
public class Tutoria {
    private int idTutoria;
    private int idTutor;     
    private LocalDate fecha;
    private LocalTime horaInicio;
    private byte[] evidencia;    

    public Tutoria() {
    }

    public Tutoria(int idTutoria, int idTutor, LocalDate fecha, LocalTime horaInicio, byte[] evidencia) {
        this.idTutoria = idTutoria;
        this.idTutor = idTutor;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.evidencia = evidencia;
    }

    public int getIdTutoria() {
        return idTutoria;
    }

    public void setIdTutoria(int idTutoria) {
        this.idTutoria = idTutoria;
    }

    public int getIdTutor() {
        return idTutor;
    }

    public void setIdTutor(int idTutor) {
        this.idTutor = idTutor;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public byte[] getEvidencia() {
        return evidencia;
    }

    public void setEvidencia(byte[] evidencia) {
        this.evidencia = evidencia;
    }

}
