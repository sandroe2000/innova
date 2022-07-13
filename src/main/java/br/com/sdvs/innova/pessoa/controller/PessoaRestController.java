package br.com.sdvs.innova.pessoa.controller; 

import br.com.sdvs.innova.pessoa.domain.Pessoa;
import br.com.sdvs.innova.pessoa.dto.Identificacao;
import br.com.sdvs.innova.pessoa.repository.*;
import br.com.sdvs.innova.pessoa.repository.specification.IdentificacaoSpecificationsBuilder;
import br.com.sdvs.innova.pessoa.service.ImportacaoPessoa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("pessoas")
public class PessoaRestController {

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private ImportacaoPessoa importacaoPessoa;

    @GetMapping(value = "/importacaoPessoa")
    public String importacaoPessoa(){        
        importacaoPessoa.inicio();
        return "OK";
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable long id){
        return repository.findById(id)
            .map(record -> ResponseEntity.ok().body( record )
            ).orElse(ResponseEntity.notFound().build());
    }

    @PostMapping(value = "/identificacao")
    public ResponseEntity<Page<Pessoa>> identificacao(
            @RequestBody Identificacao identificacao,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable){
        IdentificacaoSpecificationsBuilder builder = new IdentificacaoSpecificationsBuilder();                
        
        builder.with(identificacao);
              
        Specification<Pessoa> specs = builder.build();
        Page<Pessoa> page = repository.findAll(specs, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Pessoa create(@Valid @RequestBody Pessoa entity){
        return repository.save(entity);
    }

    @PutMapping(value="/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") long id, @RequestBody Pessoa pessoa) {
        return repository.findById(id)
            .map(record -> {
                return ResponseEntity.ok().body(repository.save(pessoa));
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