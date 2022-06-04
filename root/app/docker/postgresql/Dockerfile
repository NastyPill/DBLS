FROM postgres
MAINTAINER valera
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=pass
ENV POSTGRES_DB=postgres
COPY schema/creation/script.sql /docker-entrypoint-initdb.d/
