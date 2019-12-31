FROM openjdk:8u232-jre-stretch
COPY target/DemoGraphQL-0.0.1-SNAPSHOT.zip /tmp
RUN unzip /tmp/DemoGraphQL-0.0.1-SNAPSHOT.zip
WORKDIR /DemoGraphQL-0.0.1-SNAPSHOT
RUN chmod a+x run.sh
EXPOSE 8080/tcp 
EXPOSE 9997/tcp
EXPOSE 9998/tcp
CMD /DemoGraphQL-0.0.1-SNAPSHOT/run.sh
