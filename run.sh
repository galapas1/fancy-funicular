#!/usr/bin/env bash

#mvn clean # omit to retain db
mvn compile package 

java -jar ./target/fancy-funicular-0.0.1-SNAPSHOT.jar --spring.config.location=target/classes/application.properties
