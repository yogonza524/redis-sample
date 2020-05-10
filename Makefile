run:
	./gradlew clean assemble jibDockerBuild
	docker-compose up

stop:
	docker-compose stop

logs:
	docker-compose logs -f app-redis

redis-cli:
	redis-cli -h 127.0.0.1 -p 6370
