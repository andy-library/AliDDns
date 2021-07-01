FROM java:8
WORKDIR /usr/src/app
COPY target/AliDDns-0.0.1-SNAPSHOT.jar /usr/src/app/

CMD ["--server.port=8080"]

EXPOSE 8080

ENTRYPOINT ["java","-jar","/usr/src/app/AliDDns-0.0.1-SNAPSHOT.jar"]