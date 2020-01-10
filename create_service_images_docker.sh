
docker rmi services/discovery
cd services/discovery-service
docker build --build-arg JAR_FILE=build/libs/*.jar -t services/discovery .
cd ../..
docker rmi services/gateway
cd services/gateway-service
docker build --build-arg JAR_FILE=build/libs/*.jar -t services/gateway .
cd ../..
docker rmi services/result
cd services/result-service
docker build --build-arg JAR_FILE=build/libs/*.jar -t services/result .
cd ../..
docker rmi services/session
cd services/session-service
docker build --build-arg JAR_FILE=build/libs/*.jar -t services/session .
cd ../..
docker rmi services/task-executor
cd services/task-executor-service
docker build --build-arg JAR_FILE=build/libs/*.jar -t services/task-executor .
cd ../..
docker rmi services/task
cd services/task-service
docker build --build-arg JAR_FILE=build/libs/*.jar -t services/task .
cd ../..
docker rmi services/test
cd services/test-service
docker build --build-arg JAR_FILE=build/libs/*.jar -t services/test .
cd ../..
docker login --username=vee97 --password=RabbitVlad1997
docker tag services/discovery vee97/discovery
docker push vee97/discovery
docker tag services/gateway vee97/gateway
docker push vee97/gateway
docker tag services/result vee97/result
docker push vee97/result
docker tag services/session vee97/session
docker push vee97/session
docker tag services/task-executor vee97/task-executor
docker push vee97/task-executor
docker tag services/task vee97/task
docker push vee97/task
docker tag services/test vee97/test
docker push vee97/test
