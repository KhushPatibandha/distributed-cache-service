
# distributed cache service

A rapid and scalable data storage system distributed across multiple nodes, designed to sustain high-performance reading. Utilizes Zookeeper for configuration management and consistent hashing for efficient key-value storage and retrieval under the hood.

## System idea
![image](https://github.com/KhushPatibandha/distributed-cache-service/assets/104303438/6feee4ea-b823-4f3a-a9e6-6e3cfd03798b)

## Setup
Clone the project
```
git clone https://github.com/KhushPatibandha/distributed-cache-service.git
```
Go to the project directory
```
cd .\distributed-cache-service\
```
kubectl install
```
https://kubernetes.io/docs/tasks/tools/
```
MiniKube install
```
https://minikube.sigs.k8s.io/docs/start/
```

## Deployment

```
cd K8SYamlFiles/
```
Run Zookeeper server on your minikube cluster
```
kubectl apply -f zkDeployment.yaml
kubectl apply -f zkService.yaml
```
Launch the cache client
```
kubectl apply -f clientDeployment.yaml
kubectl apply -f clientService.yaml
```
Launch cache servers
```
kubectl apply -f serverDeployment.yaml 
```
Port forwarding (Most Important)
```
kubectl port-forward <name of client pod> 5000:5000
```
## API Reference

#### Get item

```http
  GET /api/get/<yourKey>
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `cache key` | `string` | Give cache key to retrive it's value |

#### Post Key-Value

```http
  POST /api/post?key=<yourKey>&value=<yourValue>
```

| Parameter | Type     | Description                       |
| :-------- | :------- | :-------------------------------- |
| cache key and value      | `string, string` | Post your key-value |


## Auto scaling

Run autoscaling bash file.
```
bash autoScalingScript.sh 
```

### Stress test

Create a traffic pod
```
kubectl apply -f trafficGen.yaml
```

SSH into traffic pod
```
kubectl exec -it trafficgen -- sh 
```

install wrk benchmark tool
```
apk add wrk
```

Test
```
wrk -c 10 -t 10 -d 300s -H "Connection: Close" http://cache-client:5000/api/get/khush
```
## TODO

Implement rehashing of keys once a new server is scaled up or scaled down. The current implementation doesn't have rehashing for keys, hence it might result in cache misses if servers are scaled up. It will work perfectly once they are scaled down again.