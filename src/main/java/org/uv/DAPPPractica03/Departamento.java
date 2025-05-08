package org.uv.DAPPPractica03;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

@Entity
@Table(name = "departamentos")
public class Departamento implements Serializable {
    @GeneratedValue(generator = "departamentos_clave_seq",
            strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(name = "departamentos_clave_seq",
            sequenceName = "departamentos_clave_seq",initialValue = 1,
            allocationSize = 1)
   
    @Column
    @Id
    private Long clave;
    
    @Column
    private String nombre;
        
    @JsonIgnore
    @OneToMany(mappedBy = "depto",cascade = CascadeType.ALL,orphanRemoval=false)
    private List<Empleado> lstEmpleados;

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

    public List<Empleado> getLstEmpleados() {
        return lstEmpleados;
    }

    public void setLstEmpleados(List<Empleado> lstEmpleados) {
        this.lstEmpleados = lstEmpleados;
    }
    
}
