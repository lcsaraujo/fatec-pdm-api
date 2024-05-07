package br.edu.fatec.controller;

import br.edu.fatec.model.Cliente;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ReflectPermission;
import java.util.ArrayList;
import java.util.List;

@RestController
public class ClientController {

    private List<Cliente> listaClientes = new ArrayList<Cliente>();

    @PostMapping(path = "/clientes")
    public ResponseEntity<?> cadastrarCliente(@RequestBody Cliente cliente){
        Integer codigo = (int) (Math.random() * 1000);

        for(Cliente c: listaClientes){
            if(c.getEmail().equals(cliente.getEmail())){
                return ResponseEntity.status(HttpStatus.CONFLICT).body("Cliente já cadastrado");
            }
        }


        cliente.setCodigo(codigo);
        this.listaClientes.add(cliente);

        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }

    @GetMapping(path = "/clientes/{id}")
    public ResponseEntity<?> consultarClientePorId(@PathVariable(name = "id") Integer id){

        Cliente clienteProcurado = null;

        for(Cliente c : this.listaClientes){
            if(id.equals(c.getCodigo())){
                clienteProcurado = c;
            }
        }

        if (clienteProcurado == null){
            return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }else{
            return ResponseEntity.ok(clienteProcurado);
        }
    }

    @GetMapping(path = "/teste")
    public String getPing()
    {
        return "Ta funçonano 👍!";
    }
    @GetMapping(path = "/clientes")
    public List<Cliente> consultarCliente(@RequestParam(name = "nome", required = false) String nome){

        List<Cliente> filtro = new ArrayList<Cliente>();

        if (nome != null && !nome.isEmpty()){
            for (Cliente c : this.listaClientes){
                if(c.getNome().contains(nome)){
                    filtro.add(c);
                }
            }
            return filtro;
        }

        return this.listaClientes;
    }
}
