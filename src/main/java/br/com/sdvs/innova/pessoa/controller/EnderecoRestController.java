package br.com.sdvs.innova.pessoa.controller;

import br.com.sdvs.innova.cep.repository.EnderecoRepository;
import br.com.sdvs.innova.pessoa.domain.Endereco;
import br.com.sdvs.innova.pessoa.domain.Pessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import javax.validation.Valid;

@RestController
@RequestMapping("enderecos")
public class EnderecoRestController {

    @Autowired
    private final EnderecoRepository repository;

    public EnderecoRestController(EnderecoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/paged")
    public ResponseEntity<Page<Endereco>> paged(
        @RequestParam("idPessoa") Long idPessoa, 
        @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) 
    {
        Page<Endereco> page;
        Pessoa pessoa = new Pessoa();
        pessoa.setId(idPessoa);
        page = repository.findAByPessoa(pessoa, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable long id){
        return repository.findById(id)
            .map(record -> ResponseEntity.ok().body( record )
            ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> create(@Valid @RequestBody List<Endereco> entity){
        List<Endereco> enderecos = repository.saveAll(entity);
        return new ResponseEntity<>(enderecos, HttpStatus.CREATED);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") long id, @RequestBody Endereco entity) {
        return repository.findById(id)
            .map(record -> {
                return ResponseEntity.ok().body(repository.save(entity));
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path ={"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
            .map(record -> {
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }
}