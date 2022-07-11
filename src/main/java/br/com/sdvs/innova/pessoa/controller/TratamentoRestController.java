package br.com.sdvs.innova.pessoa.controller;

import br.com.sdvs.innova.pessoa.domain.Tratamento;
import br.com.sdvs.innova.pessoa.repository.TratamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("tratamentos")
public class TratamentoRestController {

    @Autowired
    private final TratamentoRepository repository;

    public TratamentoRestController(TratamentoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/searchPaged")
    public ResponseEntity<Page<Tratamento>> searchPaged(
            @RequestParam("search") String search,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Tratamento> page;
        page = repository.findAllByDescricaoContaining(search, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    @GetMapping(path = {"/{id}"})
    public ResponseEntity<?> findById(@PathVariable long id) {
        return repository.findById(id)
            .map(record -> ResponseEntity.ok().body(record)
            ).orElse(ResponseEntity.notFound().build());
}

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Tratamento create(@Valid @RequestBody Tratamento entity) {
        return repository.save(entity);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") long id, @RequestBody Tratamento entity) {
        return repository.findById(id)
            .map(record -> {
                return ResponseEntity.ok().body(repository.save(entity));
            }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping(path = {"/{id}"})
    public ResponseEntity<?> delete(@PathVariable long id) {
        return repository.findById(id)
            .map(record -> {
                repository.deleteById(id);
                return ResponseEntity.ok().build();
            }).orElse(ResponseEntity.notFound().build());
    }
}