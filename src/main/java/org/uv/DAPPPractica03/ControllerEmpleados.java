package org.uv.DAPPPractica03;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

@RestController
@RequestMapping("/empleados")
public class ControllerEmpleados {

    @Autowired
    RepositoryEmpleados repositoryEmpleado;

    @Autowired
    RepositoryDepartamentos repositoryDepartamentos;

    @GetMapping()
    public List<Empleado> list() {
        return repositoryEmpleado.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Empleado> get(@PathVariable Long id) {

        Optional<Empleado> resEmp = repositoryEmpleado.findById(id);
        if (resEmp.isPresent()) {
            return ResponseEntity.ok(resEmp.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<Empleado> post(@RequestBody Empleado entrada) {
        Optional<Departamento> depto =
                repositoryDepartamentos.findById(entrada.getDepto().getClave());
        if (depto.isPresent()) {
            entrada.setDepto(depto.get());
            Empleado empNew = repositoryEmpleado.save(entrada);
        return ResponseEntity.ok(empNew);
    }else{
    return ResponseEntity.notFound().build();
    }
}

@PutMapping("/{id}")
public ResponseEntity<Empleado> put(@PathVariable Long id, @RequestBody Empleado empleadoDetalles) {
        Optional<Empleado> empleadoExistente = repositoryEmpleado.findById(id);

        if (empleadoExistente.isPresent()) {
            Empleado empleadoActualizado = empleadoExistente.get();
            empleadoActualizado.setNombre(empleadoDetalles.getNombre());
            empleadoActualizado.setDireccion(empleadoDetalles.getDireccion());
            empleadoActualizado.setTelefono(empleadoDetalles.getTelefono());

            repositoryEmpleado.save(empleadoActualizado);
            return ResponseEntity.ok(empleadoActualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/(id)")
public ResponseEntity<Empleado> delete(@PathVariable Long id) {
        Optional<Empleado> resEmp = repositoryEmpleado.findById(id);

        if (resEmp.isPresent()) {
            Empleado empDeleted = resEmp.get();
            repositoryEmpleado.delete(resEmp.get());
            return ResponseEntity.ok(empDeleted);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @ExceptionHandler(Exception.class)
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error message")
public void handleError() {
    }

}
