package br.com.sdvs.innova.cep.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sdvs.innova.cep.domain.Localidade;
import br.com.sdvs.innova.cep.repository.LocalidadeRepository;

@Service
public class ImportacaoLocalidade {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Value("${base.cep.path}")
    private String baseCepPath;

    @Autowired
    LocalidadeRepository localidadeRepository;

    @Autowired
    ImportacaoBairro importacaoBairro;
    
    public void inicio(){

        FileInputStream inputStream = null;
        Scanner scanner = null;
        int i = 1;

        try {
            inputStream = new FileInputStream(baseCepPath.concat("LOG_LOCALIDADE.TXT"));
            scanner = new Scanner(inputStream, "ISO-8859-1");

            List<Localidade> localidades = new ArrayList<Localidade>();
            
            while(scanner.hasNextLine()){
                
                String line = scanner.nextLine();

                if(line.trim().length() > 0){
                    localidades.add( this.getLocalidade(line) );
                }

                if(i % batchSize == 0) {
                    this.setLocalidade(localidades);
                    localidades.clear();
                }
                i++;
            }

            this.setLocalidade(localidades);
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) scanner.close();
            importacaoBairro.inicio();
        }
    }

    private void setLocalidade(List<Localidade> localidades){
        try {
            localidadeRepository.saveAll(localidades);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Localidade getLocalidade(String line){
        
        String[] localidades = line.split("@");
        Localidade localidade = new Localidade();
        localidade.setLocNu( Long.parseLong( localidades[0] ));
        localidade.setUfeSg(localidades[1]);
        localidade.setLocNo(localidades[2]);
        localidade.setCep(localidades[3]);
        return localidade;
    }
}