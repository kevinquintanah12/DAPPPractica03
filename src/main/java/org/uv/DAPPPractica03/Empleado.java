package org.uv.DAPPPractica03;


import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name="empleados2")
public class Empleado implements Serializable {
    
    @Id
    @GeneratedValue(generator = "empleados2_clave_seq",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "empleados2_clave_seq",
            sequenceName = "empleados2_clave_seq", initialValue = 1,
            allocationSize = 1)
    @Column
    private Long clave;
    
    @Column
    private String nombre;
    
    @Column
    private String direccion;
    
    @Column
    private String telefono;
    
    @ManyToOne
    @JoinColumn(name="clave_depto")
    private Departamento depto;

    public Long getClave() {
        return clave;
    }

    public void setClave(Long clave) {
        this.clave = clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Departamento getDepto() {
        return depto;
    }

    public void setDepto(Departamento depto) {
        this.depto = depto;
    }

    

    
}
