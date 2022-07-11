package br.com.sdvs.innova.cep.service;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.com.sdvs.innova.cep.domain.Bairro;
import br.com.sdvs.innova.cep.domain.Localidade;
import br.com.sdvs.innova.cep.domain.Logradouro;
import br.com.sdvs.innova.cep.domain.Uf;
import br.com.sdvs.innova.cep.repository.LogradouroRepository;

@Service
public class ImportacaoLogradouro {

    @Value("${spring.jpa.properties.hibernate.jdbc.batch_size}")
    private int batchSize;

    @Autowired
    LogradouroRepository logradouroRepository;
    
    public void inicio(){

        FileInputStream inputStream = null;
        Scanner scanner = null;
        int i = 1;

        try {
            String[] files = {
                "LOG_LOGRADOURO_RJ.TXT",
            };
            /*"LOG_LOGRADOURO_SC.TXT",
            "LOG_LOGRADOURO_SP.TXT",
            "LOG_LOGRADOURO_TO.TXT",
            "LOG_LOGRADOURO_SE.TXT"
            "LOG_LOGRADOURO_RS.TXT",
            "LOG_LOGRADOURO_RR.TXT",
            "LOG_LOGRADOURO_RO.TXT",
            "LOG_LOGRADOURO_RN.TXT",
            "LOG_LOGRADOURO_PR.TXT",
            "LOG_LOGRADOURO_PI.TXT",
            "LOG_LOGRADOURO_PE.TXT",
            "LOG_LOGRADOURO_PB.TXT",
            "LOG_LOGRADOURO_PA.TXT",
            "LOG_LOGRADOURO_MT.TXT",
            "LOG_LOGRADOURO_MS.TXT",
            "LOG_LOGRADOURO_MG.TXT",
            "LOG_LOGRADOURO_MA.TXT",
            "LOG_LOGRADOURO_GO.TXT",
            "LOG_LOGRADOURO_ES.TXT",
            "LOG_LOGRADOURO_DF.TXT",
            "LOG_LOGRADOURO_CE.TXT",
            "LOG_LOGRADOURO_BA.TXT",
            "LOG_LOGRADOURO_AP.TXT",
            "LOG_LOGRADOURO_AM.TXT",
            "LOG_LOGRADOURO_AL.TXT",
            "LOG_LOGRADOURO_AC.TXT"
            };*/

            for (String file : files) {              
                inputStream = new FileInputStream("/home/sandro/Dev/innova/samples/CEP/Delimitado/"+file);
                scanner = new Scanner(inputStream, "ISO-8859-1");

                List<Logradouro> logradouros = new ArrayList<Logradouro>();
                
                while(scanner.hasNextLine()){
                    
                    String line = scanner.nextLine();

                    if(line.trim().length() > 0){
                        logradouros.add( this.getLogradouro(line) );
                    }

                    if(i % batchSize == 0) {
                        this.setLogradouro(logradouros);
                        logradouros.clear();
                    }
                    i++;
                }
                this.setLogradouro(logradouros);
            }
        
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(scanner != null) scanner.close();
        }
    }

    private void setLogradouro(List<Logradouro> logradouros){
        try {
            logradouroRepository.saveAll(logradouros);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Logradouro getLogradouro(String line){
        
        String[] logradouros = line.split("@");

        Uf uf = new Uf();
        uf.setUfeSg(logradouros[1]);

        Localidade localidade = new Localidade();
        localidade.setLocNu( Long.parseLong(logradouros[2]) );

        Bairro bairro = new Bairro();
        bairro.setBaiNu( Long.parseLong(logradouros[3]) );

        Logradouro logradouro = new Logradouro();
        logradouro.setLogNu( Long.parseLong(logradouros[0]) );
        logradouro.setBairro(bairro);
        logradouro.setLogNo(logradouros[5]);
        logradouro.setLogComplemento(logradouros[6]);
        logradouro.setCep(logradouros[7]);
        logradouro.setTloTx(logradouros[8]);
        logradouro.setLogNoAbrev(logradouros[10]);
        
        return logradouro;
    }
}