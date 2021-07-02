FROM java:8
MAINTAINER Andy.Yang

WORKDIR /app
COPY target/AliDDns.jar /app/

CMD ["--server.port=8080"]
EXPOSE 8080

RUN ln -sf /usr/share/zoneinfo/Asia/Shanghai /etc/localtime
RUN echo 'Asia/Shanghai' >/etc/timezone

ENTRYPOINT ["java","-jar","/app/AliDDns.jar"]