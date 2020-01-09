docker-machine rm managervm -y
docker-machine create --driver hyperv managervm
docker-machine rm worker1vm -y
docker-machine create --driver hyperv worker1vm
docker-machine rm worker2vm -y
docker-machine create --driver hyperv worker2vm
docker-machine rm worker3vm -y
docker-machine create --driver hyperv worker3vm