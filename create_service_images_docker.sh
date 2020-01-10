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
docker rmi services/front-end
cd services/front-end

