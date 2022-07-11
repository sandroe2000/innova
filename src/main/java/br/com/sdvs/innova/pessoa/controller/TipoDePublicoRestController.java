package br.com.sdvs.innova.pessoa.controller;

import br.com.sdvs.innova.pessoa.domain.TipoDePublico;
import br.com.sdvs.innova.pessoa.repository.TipoDePublicoRepository;
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
@RequestMapping("publicos")
public class TipoDePublicoRestController {

    @Autowired
    private final TipoDePublicoRepository repository;

    public TipoDePublicoRestController(TipoDePublicoRepository repository) {
        this.repository = repository;
    }

    @GetMapping(value = "/search")
    public ResponseEntity<Page<TipoDePublico>> search(
            @RequestParam("search") String search,
            @PageableDefault(page = 0, size = 5, sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        Page<TipoDePublico> page;
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
    public ResponseEntity<Page<TipoDePublico>> create(@Valid @RequestBody TipoDePublico entity) {
        TipoDePublico result = repository.save(entity);
        return this.search(result.getDescricao(), null);
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<?> update(@Valid @PathVariable("id") long id, @RequestBody TipoDePublico entity) {
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