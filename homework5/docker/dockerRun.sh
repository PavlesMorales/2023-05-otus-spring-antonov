docker stop pg-docker
docker rm pg-docker
docker run --rm --name pg-docker \
-e POSTGRES_PASSWORD=pwd \
-e POSTGRES_USER=usr \
-e POSTGRES_DB=testdb \
-p 5430:5432 \
postgres:12