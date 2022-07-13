package br.com.sdvs.innova.cep.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sdvs.innova.cep.domain.Bairro;
import br.com.sdvs.innova.cep.repository.BairroRepository;

@Service
public class ImportacaoBairro {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Value("${base.cep.path}")
    private String baseCepPath;

    @Autowired
    BairroRepository bairroRepository;

    @Autowired
    ImportacaoLogradouro importacaoLogradouro;
    
    public void inicio(){

        FileInputStream inputStream = null;
        Scanner scanner = null;
        int i = 1;

        try {
            inputStream = new FileInputStream(baseCepPath.concat("LOG_BAIRRO.TXT"));
            scanner = new Scanner(inputStream, "ISO-8859-1");

            List<Bairro> bairros = new ArrayList<Bairro>();
            
            while(scanner.hasNextLine()){
                
                String line = scanner.nextLine();

                if(line.trim().length() > 0){
                    bairros.add( this.getBairro(line) );
                }

                if(i % batchSize == 0) {
                    this.setBairro(bairros);
                    bairros.clear();
                }
                i++;
            }

            this.setBairro(bairros);
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) scanner.close();
            importacaoLogradouro.inicio();
        }
    }

    private void setBairro(List<Bairro> bairros){
        try {
            bairroRepository.saveAll(bairros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Bairro getBairro(String line){
        
        String[] bairros = line.split("@");

        Bairro bairro = new Bairro();
        bairro.setBaiNu( Long.parseLong(bairros[0]) );
        bairro.setUfeSg(bairros[1]);
        bairro.setLocNu( Long.parseLong(bairros[2]) );
        bairro.setBaiNo( bairros[3]);
        return bairro;
    }
}