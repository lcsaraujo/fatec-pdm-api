package br.edu.fatec.controller;

import br.edu.fatec.model.Cliente;
import jdk.javadoc.doclet.Reporter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.ReflectPermission;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

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

            return this.listaClientes.stream().filter(c -> c.getNome()
                    .toUpperCase().startsWith(nome.toUpperCase()))
                    .collect(Collectors.toList());

        }

        return this.listaClientes;
    }

    @DeleteMapping(path = "/clientes/{id}")
    public ResponseEntity<?> deletarCliente(@PathVariable(name = "id") Integer id){

        for (Cliente c : this.listaClientes){
            if(c.getCodigo().equals(id)){
                this.listaClientes.remove(c);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }

    @PutMapping(path = "/clientes/{id}")
    public ResponseEntity<?> alterarPorId(@PathVariable Integer id, @RequestBody Cliente cliente){

        for (Cliente c : this.listaClientes){
            if(id.equals(c.getCodigo())){
                c.setNome(cliente.getNome());
                c.setEmail(cliente.getEmail());
                return ResponseEntity.status(HttpStatus.OK).body(c);
            }
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
    }
}
