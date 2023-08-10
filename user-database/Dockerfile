FROM openjdk:17

WORKDIR /usr/src/

CMD ["ls"]

CMD ["cd ./terget"]

CMD ["ls"]

COPY target/user-database.jar /usr/src/

CMD ["java","-jar","user-database.jar"]