docker-machine start managervm
docker-machine start worker1vm
docker-machine ls
docker-machine ssh managervm "docker node ls"
docker-machine scp docker-compose.yml managervm:~
docker-machine ssh managervm "mkdir pg-init-scripts"
docker-machine ssh worker1vm "mkdir pg-init-scripts"
docker-machine scp pg-init-scripts/create-multiple-postgresql-databases.sh managervm:~/pg-init-scripts
docker-machine scp pg-init-scripts/create-multiple-postgresql-databases.sh worker1vm:~/pg-init-scripts
docker-machine ssh managervm "docker stack rm services"
docker-machine ssh managervm "docker stack deploy -c docker-compose.yml services"
docker-machine ssh managervm "docker stack ls services"
