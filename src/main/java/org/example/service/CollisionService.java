package org.example.service;

import com.opencsv.CSVReader;
import org.example.DTO.LatLon;
import org.example.entity.CollisionEntity;
import org.example.repository.CollisionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static org.example.util.Util.*;

@Service
public class CollisionService {
    @Autowired
    private CollisionRepository repository;
    private final RestTemplate restTemplate;


    public CollisionService(){
        this.restTemplate = new RestTemplate();
    }


    public void importCsv(String path) {
        try (CSVReader reader = new CSVReader(new FileReader(path))) {
            Long i = 0L;
            String[] fields;
            CollisionEntity entity;
            List<CollisionEntity> lst_collision = new ArrayList<>();
            //skip first line
            fields = reader.readNext();
            while ((fields = reader.readNext()) != null) {
                IO.println(String.format("Processando la %d istanza",i));
                i++;
                entity = new CollisionEntity();
                entity.setCrash_date(parseDate(fields[0]));
                entity.setCrash_time(parseTime(fields[1]));

                entity.setBorough(fields[2].isEmpty() ? null : fields[2]);
                entity.setZip_code(fields[3].isEmpty() ? null : fields[3]);

                entity.setLatitude(parseDouble(fields[4]));
                entity.setLongitude(parseDouble(fields[5]));
                //entity.setLocation(fields[6]);

                entity.setOn_street_name(fields[7].isEmpty() ? null : fields[7]);
                entity.setCross_street_name(fields[8].isEmpty() ? null : fields[8]);
                entity.setOff_street_name(fields[9].isEmpty() ? null : fields[9]);

                entity.setNumber_of_persons_injured(fields[10].isEmpty() ? null: parseInt(fields[10]));
                entity.setNumber_of_persons_killed(fields[11].isEmpty() ? null: parseInt(fields[11]));
                entity.setNumber_of_pedestrians_injured(fields[12].isEmpty() ? null: parseInt(fields[12]));
                entity.setNumber_of_pedestrians_killed(fields[13].isEmpty() ? null: parseInt(fields[13]));
                entity.setNumber_of_cyclist_injured(fields[14].isEmpty() ? null: parseInt(fields[14]));
                entity.setNumber_of_cyclist_killed(fields[15].isEmpty() ? null: parseInt(fields[15]));
                entity.setNumber_of_motorist_injured(fields[16].isEmpty() ? null: parseInt(fields[16]));
                entity.setNumber_of_motorist_killed(fields[17].isEmpty() ? null: parseInt(fields[10]));

                entity.setContributing_factor_vehicle_1(fields[18].isEmpty() ? null : fields[18]);
                entity.setContributing_factor_vehicle_2(fields[19].isEmpty() ? null : fields[19]);
                entity.setContributing_factor_vehicle_3(fields[20].isEmpty() ? null : fields[20]);
                entity.setContributing_factor_vehicle_4(fields[21].isEmpty() ? null : fields[21]);
                entity.setContributing_factor_vehicle_5(fields[22].isEmpty() ? null : fields[22]);

                entity.setCollision_id(fields[23].isEmpty() ? null: Long.parseLong(fields[23]));

                entity.setVehicle_type_code1(fields[24].isEmpty() ? null : fields[24]);
                entity.setVehicle_type_code2(fields[25].isEmpty() ? null : fields[25]);
                entity.setVehicle_type_code_3(fields[26].isEmpty() ? null : fields[26]);
                entity.setVehicle_type_code_4(fields[27].isEmpty() ? null : fields[27]);
                entity.setVehicle_type_code_5(fields[28].isEmpty() ? null : fields[28]);

                lst_collision.add(entity);


            }

            repository.saveAll(lst_collision);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void reverseGeocoding(){
        List<Object[]> rows = repository.getLatLongFromBoroughNotNull();

        List<LatLon> geopoints = rows.stream()
                .map(r-> new LatLon((Long) r[0], (Double)r[1],(Double)r[2]))
                .toList();

        IO.println(String.format("Numero di righe trovate: %d",geopoints.size()));

        for(LatLon l: geopoints){
            String url = "https://nominatim.openstreetmap.org/reverse?format=json&lat="+
                    l.latitude()+
                    "&lon=" + l.longitude() + "&zoom=14&addressdetails=1";
            String response = restTemplate.getForObject(url,String.class);

            try{
                JsonNode root = new ObjectMapper().readTree(response);
                JsonNode addressNode = root.get("address");
                String suburb;

                if(addressNode != null && addressNode.get("suburb")!= null){
                    suburb = addressNode.get("suburb").asString();
                    System.out.println(suburb);

                    repository.updateBorough(l.id(),suburb);
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }

            try{
                Thread.sleep(1001);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        System.exit(0);
    }
}
