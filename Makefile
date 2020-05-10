run:
	./gradlew assemble
	docker-compose up -d --build --no-recreate 

stop:
	docker-compose stop

logs:
	docker-compose logs -f app-redis

redis-cli:
	redis-cli -h 127.0.0.1 -p 6370
