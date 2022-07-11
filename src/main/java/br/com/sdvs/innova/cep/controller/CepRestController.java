package br.com.sdvs.innova.cep.controller;

import br.com.sdvs.innova.cep.domain.Bairro;
import br.com.sdvs.innova.cep.domain.Localidade;
import br.com.sdvs.innova.cep.domain.Logradouro;
import br.com.sdvs.innova.cep.domain.Uf;
import br.com.sdvs.innova.cep.repository.BairroRepository;
import br.com.sdvs.innova.cep.repository.LocalidadeRepository;
import br.com.sdvs.innova.cep.repository.LogradouroRepository;
import br.com.sdvs.innova.cep.repository.UfRepository;
import br.com.sdvs.innova.cep.service.ImportacaoCEP;
import br.com.sdvs.innova.pessoa.domain.Endereco;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
        Page<Uf> ufs = ufRepository.findAll(PageRequest.of(0, 1000, Sort.by(Sort.Direction.ASC, "ufeSg")));
        return new ResponseEntity<>(ufs, HttpStatus.OK);
    }

    @GetMapping(value = "/localidade/{ufeSg}")
    public ResponseEntity<?> getLocalidade(@PathVariable String ufeSg){     
        Uf uf = new Uf();
           uf.setUfeSg(ufeSg);
        Page<Localidade> localidades = localidadeRepository.findAllByUf(uf, PageRequest.of(0, 2000, Sort.by(Sort.Direction.ASC, "locNo")));
        return new ResponseEntity<>(localidades, HttpStatus.OK);
    }

    @GetMapping(value = "/bairro/{locNo}")
    public ResponseEntity<?> getBairro(@PathVariable String locNo){     
        Localidade localidade = localidadeRepository.findByLocNo(locNo);
        Page<Bairro> bairros = bairroRepository.findAllByLocalidade(localidade, PageRequest.of(0, 2000, Sort.by(Sort.Direction.ASC, "baiNo")));
        return new ResponseEntity<>(bairros, HttpStatus.OK);
    }

    @GetMapping(value = "/pesquisar/{cep}")
    public ResponseEntity<?> getPesquisar(@PathVariable String cep){     
        return logradouroRepository.findByCep(cep)
        .map(logradouro -> {
            return setEndereco(logradouro);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/pesquisarLog/{logradouro}")
    public ResponseEntity<?> getPesquisarLog(
        @PathVariable String logradouro,
        @PageableDefault(page = 0, size = 5, sort = "cep", direction = Sort.Direction.ASC) Pageable pageable) 
    {     
        Page<Logradouro> page = logradouroRepository.findByLogNoContaining(logradouro, pageable);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

    private ResponseEntity<Endereco> setEndereco(Logradouro logradouro) {
        Endereco endereco = new Endereco();
        endereco.setCep( logradouro.getCep());
        endereco.setLogradouro( logradouro.getLogNo().toUpperCase());
        endereco.setComplemento( logradouro.getLogComplemento().toUpperCase());
        endereco.setUf( logradouro.getBairro().getUf().getUfeSg());
        endereco.setCidade( logradouro.getBairro().getLocalidade().getLocNo().toUpperCase());
        endereco.setBairro( logradouro.getBairro().getBaiNo().toUpperCase());
        return ResponseEntity.ok().body( endereco );
    }
}
