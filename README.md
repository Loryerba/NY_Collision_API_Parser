# NY_Collision_API_Parser

## Abstract
This repository provides Java code for parsing the CSV file available at [Motor Vehicle Collisions – Crashes](https://data.cityofnewyork.us/Public-Safety/Motor-Vehicle-Collisions-Crashes/h9gi-nx95/about_data) and for parsing the JSON responses from the [Nominatim Reverse Geocoding API](https://nominatim.openstreetmap.org/ui/search.html).

## Specification
This repository contains a simple Java project built with Maven that provides the following functionalities:
- [x] Parsing CSV files containing NYC motor vehicle collision data
- [x] Parsing JSON responses from the Nominatim OpenStreetMap reverse geocoding service

In particular, the reverse geocoding service is required to resolve locations that belong to unknown boroughs but have latitude and longitude values defined.

## Framework
This project uses the **Spring Boot** framework along with a local PostgreSQL database. All dependencies can be found in the `pom.xml` file.

## Other Info
This repository was created for the Data Visualization exam at the **Università degli Studi di Milano-Bicocca** as part of the Master’s Degree in Computer Science.
