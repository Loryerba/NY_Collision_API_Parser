package org.example.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "collision")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CollisionEntity {

    @Id
    private Long collision_id; // primary key

    private LocalDate crash_date;
    private LocalTime crash_time;

    private String borough;
    private String zip_code;

    private Double latitude;
    private Double longitude;
    private String location;

    private String on_street_name;
    private String off_street_name;
    private String cross_street_name;

    private Integer number_of_persons_injured;
    private Integer number_of_persons_killed;
    private Integer number_of_pedestrians_injured;
    private Integer number_of_pedestrians_killed;
    private Integer number_of_cyclist_injured;
    private Integer number_of_cyclist_killed;
    private Integer number_of_motorist_injured;
    private Integer number_of_motorist_killed;

    private String contributing_factor_vehicle_1;
    private String contributing_factor_vehicle_2;
    private String contributing_factor_vehicle_3;
    private String contributing_factor_vehicle_4;
    private String contributing_factor_vehicle_5;

    private String vehicle_type_code1;
    private String vehicle_type_code2;
    private String vehicle_type_code_3;
    private String vehicle_type_code_4;
    private String vehicle_type_code_5;

}
