package br.com.sdvs.innova.cep.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.sdvs.innova.cep.domain.Bairro;
import br.com.sdvs.innova.cep.domain.Localidade;
import br.com.sdvs.innova.cep.domain.Logradouro;
import br.com.sdvs.innova.cep.domain.Uf;
import br.com.sdvs.innova.cep.repository.BairroRepository;
import br.com.sdvs.innova.cep.repository.LocalidadeRepository;
import br.com.sdvs.innova.cep.repository.LogradouroRepository;
import br.com.sdvs.innova.cep.repository.UfRepository;
import br.com.sdvs.innova.cep.service.ImportacaoCEP;

@RestController
@RequestMapping("ceps")
public class CepRestController {

    @Autowired
    private UfRepository ufRepository;

    @Autowired
    private LocalidadeRepository localidadeRepository;

    @Autowired
    private BairroRepository bairroRepository;

    @Autowired
    private LogradouroRepository logradouroRepository;

    @Autowired
    private ImportacaoCEP importacaoCEP;

    @GetMapping(value = "/importacaoCEP")
    public String importacaoUF(){        
        importacaoCEP.inicio();
        return "OK";
    }

    @GetMapping(value = "/uf")
    public ResponseEntity<?> getUF(){        
        Page<Uf> ufs = ufRepository.findAll( setPageRequest(1000, "ufeSg") );
        return new ResponseEntity<>(ufs, HttpStatus.OK);
    }

    @GetMapping(value = "/localidade/{ufeSg}")
    public ResponseEntity<?> getLocalidade(@PathVariable String ufeSg){     
        Page<Localidade> localidades = localidadeRepository.findAllByUfeSg( ufeSg, setPageRequest(2000, "locNo") );
        return new ResponseEntity<>(localidades, HttpStatus.OK);
    }

    @GetMapping(value = "/bairro/{locNu}")
    public ResponseEntity<?> getBairro(@PathVariable Long locNu){     
        Page<Bairro> bairros = bairroRepository.findAllByLocNu(locNu, setPageRequest(2000, "baiNo"));
        return new ResponseEntity<>(bairros, HttpStatus.OK);
    }

    @GetMapping(value = "/pesquisar/{cep}")
    public ResponseEntity<?> getPesquisar(@PathVariable String cep){     
        return logradouroRepository.findByCep(cep)
            .map(logradouro -> ResponseEntity.ok()
            .body( logradouro )).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/pesquisarLog/{logradouro}")
    public ResponseEntity<?> getPesquisarLog(
        @PathVariable String logradouro,
        @PageableDefault(page = 0, size = 5, sort = "cep", direction = Sort.Direction.ASC) Pageable pageable) 
    {     
        Page<Logradouro> page = logradouroRepository.findByLogNoContaining("%"+logradouro+"%", pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    private PageRequest setPageRequest(int size, String column){
        return PageRequest.of(0, size, Sort.by(Sort.Direction.ASC, column));
    }
}