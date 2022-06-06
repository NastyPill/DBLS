FROM postgres
MAINTAINER valera
ENV POSTGRES_USER=user
ENV POSTGRES_PASSWORD=pass
ENV POSTGRES_DB=postgres
COPY schema/creation/script.sql /docker-entrypoint-initdb.d/
COPY initData/pg_hba.conf /var/lib/postgresql/data/pg_hba.conf
COPY initData/postgresql.auto.conf /var/lib/postgresql/data/postgresql.auto.conf
COPY initData/postgresql.conf /var/lib/postgresql/data/postgresql.conf
CMD chown -R postgres /var/lib/postgresql/data/
CMD chmod -R 750 /var/lib/postgresql/data/
CMD chmod -R 777 /var/lib/postgresql/data/postgresql.conf
CMD chmod -R 777 /var/lib/postgresql/data/pg_hba.conf
CMD chmod -R 777 /var/lib/postgresql/data/postgresql.auto.conf
USER postgres
CMD postgres