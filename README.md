## api.lootfans


## Pod Übersicht

## REST-Applikation
Eine Schnittstelle gebaut mit Java, Spring, JPA und Hibernate 
wird über eine Build stage zu einem Maven-Artefakt kompilliert.

In einer java Laufzeitumgebung wird das Artefakt deployed. Siehe Dockerfile

## Datenbank
Eine Postgres Datenbank zum persistieren der Daten von der Spring Boot API und
Keycloak, welche auch eine REST-Schnittstelle darstellt.

## KeyCloak
https://www.keycloak.org/ 

Ein IAM-Service für Verwaltungssystem für das Identitäts- und Zugriffsmanagement
Benutzer. Sessions, Tokens und Identity provider (Social Login, etc) werden von KeyCloak verwaltet. 

## Minio
Ein Object Storage System welches wie Amazon S3 ist, nur das es lokal ausgerichtet ist.
Auf Minio werden unstrukturierte Daten wie Fotos und Videos abgelegt. Zusätzlich können
aber auch Logs und BackUps drauf abgelegt werden.



