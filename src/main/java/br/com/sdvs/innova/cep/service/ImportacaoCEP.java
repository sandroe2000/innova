package br.com.sdvs.innova.cep.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sdvs.innova.cep.domain.Uf;
import br.com.sdvs.innova.cep.repository.UfRepository;

@Service
public class ImportacaoCEP {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Autowired
    UfRepository ufRepository;

    @Autowired
    ImportacaoLocalidade importacaoLocalidade;
    
    public void inicio(){

        FileInputStream inputStream = null;
        Scanner scanner = null;
        int i = 1;

        try {
            inputStream = new FileInputStream("/home/sandro/Dev/innova/samples/CEP/Delimitado/LOG_FAIXA_UF.TXT");
            scanner = new Scanner(inputStream, "ISO-8859-1");

            List<Uf> ufs = new ArrayList<Uf>();
            
            while(scanner.hasNextLine()){
                
                String line = scanner.nextLine();

                if(line.trim().length() > 0){
                    ufs.add( this.getUf(line) );
                }

                if(i % batchSize == 0) {
                    this.setUf(ufs);
                    ufs.clear();
                }
                i++;
            }

            this.setUf(ufs);
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) scanner.close();
            importacaoLocalidade.inicio();
        }
    }

    private void setUf(List<Uf> ufs){
        try {
            ufRepository.saveAll(ufs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Uf getUf(String line){
        
        String[] ufs = line.split("@");
        Uf uf = new Uf();
           uf.setUfeSg(ufs[0]);
           uf.setUfeCepIni(ufs[1]);
           uf.setUfeCepFim(ufs[2]);
        return uf;
    }
}