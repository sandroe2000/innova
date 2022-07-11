package br.com.sdvs.innova.pessoa.controller;

import br.com.sdvs.innova.pessoa.domain.Sexo;
import br.com.sdvs.innova.pessoa.repository.SexoRepository;
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
@RequestMapping("sexos")
public class SexoRestController {

    @Autowired
    private SexoRepository repository;

    @GetMapping(value = "/searchPaged")
    public ResponseEntity<Page<Sexo>> searchPaged(
            @RequestParam("search") String search,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<Sexo> page;
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
    public Sexo create(@Valid @RequestBody Sexo entity) {
        return repository.save(entity);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") long id, @RequestBody Sexo entity) {
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