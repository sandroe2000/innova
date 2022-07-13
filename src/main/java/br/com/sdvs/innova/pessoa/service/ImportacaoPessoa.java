package br.com.sdvs.innova.pessoa.service;

import java.io.FileInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sdvs.innova.cep.repository.EnderecoRepository;
import br.com.sdvs.innova.pessoa.domain.Endereco;
import br.com.sdvs.innova.pessoa.domain.Pessoa;
import br.com.sdvs.innova.pessoa.domain.Sexo;
import br.com.sdvs.innova.pessoa.domain.Status;
import br.com.sdvs.innova.pessoa.domain.TipoDePublico;
import br.com.sdvs.innova.pessoa.repository.PessoaRepository;
import br.com.sdvs.innova.pessoa.repository.SexoRepository;
import br.com.sdvs.innova.pessoa.repository.StatusRepository;
import br.com.sdvs.innova.pessoa.repository.TipoDePublicoRepository;

@Service
public class ImportacaoPessoa {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Value("${base.cep.path}")
    private String baseCepPath;

    @Autowired
    private PessoaRepository repository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private SexoRepository sexoRepository;

    @Autowired
    private TipoDePublicoRepository tipoDePublicoRepository;

    @Autowired
    private EnderecoRepository enderecoRepository;

    private Status ATIVO = null;
    private Status CANCELADO = null;

    private Sexo MASCULINO = null;
    private Sexo FEMININO = null;

    private TipoDePublico CLIENTE_COMUM = null;
    private TipoDePublico LIMINAR_JUD_C_CARTA= null;
    private TipoDePublico SEGURADO_VITALICIO = null;
    private TipoDePublico LIMINAR_JUDICIAL= null;

    public void inicio(){

        FileInputStream inputStream = null;
        Scanner scanner = null;
        int i = 1;
        
        try {

            ATIVO = statusRepository.findByDescricao("ATIVO");
            CANCELADO = statusRepository.findByDescricao("CANCELADO");

            MASCULINO = sexoRepository.findByDescricao("MASCULINO");
            FEMININO = sexoRepository.findByDescricao("FEMININO");

            CLIENTE_COMUM = tipoDePublicoRepository.findByDescricao("CLIENTE COMUM");
            LIMINAR_JUD_C_CARTA= tipoDePublicoRepository.findByDescricao("LIMINAR JUD.C/CARTA");
            SEGURADO_VITALICIO = tipoDePublicoRepository.findByDescricao("SEGURADO VITALICIO");
            LIMINAR_JUDICIAL= tipoDePublicoRepository.findByDescricao("LIMINAR JUDICIAL");

            inputStream = new FileInputStream(baseCepPath.concat("CONCIERG.TXT"));
            scanner = new Scanner(inputStream, "UTF-8");

            List<Pessoa> pessoas = new ArrayList<Pessoa>();
            
            while(scanner.hasNextLine()){
                
                String line = scanner.nextLine();

                if(line.trim().length() > 0){
                    pessoas.add( this.getPessoa(line) );
                }

                if(i % batchSize == 0) {
                    this.setPessoa(pessoas);
                    pessoas.clear();
                }
                i++;
            }

            this.setPessoa(pessoas);
        
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if(scanner != null) scanner.close();
            this.endereco();
        }
    }

    private void endereco(){

        FileInputStream inputStream = null;
        Scanner scanner = null;
        int i = 1;
        
        try {

            inputStream = new FileInputStream("/home/sandro/Dev/innova/samples/DATA_.TXT");
            scanner = new Scanner(inputStream, "UTF-8");

            List<Endereco> enderecos = new ArrayList<Endereco>();
            
            while(scanner.hasNextLine()){
                
                String line = scanner.nextLine();

                if(line.trim().length() > 0){
                    enderecos.add( this.getEndereco(line) );
                }

                if(i % batchSize == 0) {
                    this.setEndereco(enderecos);
                    enderecos.clear();
                }
                i++;
            }

            this.setEndereco(enderecos);
        
        } catch (Exception e) {

            e.printStackTrace();

        } finally {

            if(scanner != null) scanner.close();

        }
    }

    private void setEndereco(List<Endereco> enderecos){
        try {
            enderecoRepository.saveAll(enderecos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Endereco getEndereco(String line){
        
        Endereco endereco = new Endereco();
        Pessoa pessoa = new Pessoa();
        String corporativo = this.separaString(line, 11, 26);
        String cpf = this.separaString(line, 0, 11) ;
        pessoa = repository.findByCorporativoAndCpf(corporativo, cpf);
        
        endereco.setLogradouro( this.separaString(line, 349, 399) );
        //endereco.setNumero( this.separaString(line, 0, 11) );
        //endereco.setComplemento( this.separaString(line, 0, 11) );
        endereco.setBairro( this.separaString(line, 399, 424) );
        endereco.setCidade( this.separaString(line, 424, 449) );
        endereco.setUf( this.separaString(line, 449, 451) );
        endereco.setCep( this.separaString(line, 451, 459) );
        endereco.setTipo( "RESIDENCIAL");
        endereco.setPrincipal(true);
        endereco.setPessoa(pessoa);

        return endereco;
    }

    private void setPessoa(List<Pessoa> pessoas){
        try {
            repository.saveAll(pessoas);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Pessoa getPessoa(String line){

        Pessoa pessoa = new Pessoa();

        pessoa.setCpf( this.separaString(line, 0, 11) );
        pessoa.setCorporativo( this.separaString(line, 11, 26) );
        pessoa.setNome( this.separaString(line, 26, 61) );
        pessoa.setNascimento( this.separaLocalDate(line, 223, 231) );
        
        if(this.separaString(line, 231, 251).equalsIgnoreCase("FEMININO")){
            pessoa.setSexo( FEMININO );
        }else{
            pessoa.setSexo( MASCULINO );
        }

        if(this.separaString(line, 96, 116).equalsIgnoreCase("ATIVO")){
            pessoa.setStatus( ATIVO );
        }else{
            pessoa.setStatus( CANCELADO );
        }

        if(this.separaString(line, 116, 136).equalsIgnoreCase("LIMINAR JUD.C/CARTA")){
            pessoa.setPublico( LIMINAR_JUD_C_CARTA );
        }else if(this.separaString(line, 116, 136).equalsIgnoreCase("SEGURADO VITALICIO")){
            pessoa.setPublico( SEGURADO_VITALICIO );
        }else if(this.separaString(line, 116, 136).equalsIgnoreCase("LIMINAR JUDICIAL")){
            pessoa.setPublico( LIMINAR_JUDICIAL );
        }else{
            pessoa.setPublico( CLIENTE_COMUM );
        }

        String ddd = this.separaInteger(line, 460, 465);
        String numero = this.separaInteger(line, 466, 476);

        if(ddd != null && numero != null){
            if(numero.length() > 8){
                pessoa.setCelular( "("+ddd+") ".concat(numero) );
            }else{
                pessoa.setTelefone( "("+ddd+") ".concat(numero) );
            }
        }

        return pessoa;
    }
    
    private String separaString(String line, int ini, int fim){

        String retorno = null;

        try {
            retorno = line.substring(ini, fim).trim();            
        } catch (Exception e) {            
            e.printStackTrace();
        }

        return retorno;
    }

    private String separaInteger(String line, int ini, int fim){

        String retorno = null;
        Integer numero = 0;

        try {
            numero = Integer.valueOf( line.substring(ini, fim).trim().equals("") ? "0" :  line.substring(ini, fim).trim());      
            if(numero > 0){
                retorno = String.valueOf(numero);
            }      
        } catch (Exception e) {            
            e.printStackTrace();
        }

        return retorno;
    }

    private LocalDate separaLocalDate(String line, int ini, int fim){

        LocalDate retorno  = null;

        try {
            retorno = LocalDate.parse(line.substring(ini, fim), DateTimeFormatter.ofPattern("ddMMyyyy"));            
        } catch (Exception e) {            
            e.printStackTrace();
        }

        return retorno;
    }
}