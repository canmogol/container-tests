# DENO

```shell
root@ubuntu-s-4vcpu-8gb-ams3-01:~/container-tests/deno-hono# docker run --rm -it -p 8080:8080 --cpus=1 --memory=256m --name deno-server deno-server:latest

root@ubuntu-s-4vcpu-8gb-ams3-01:~# wrk -t10 -c500 -d30s --latency http://localhost:8080/external
Running 30s test @ http://localhost:8080/external
  10 threads and 500 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   109.95ms   22.76ms 263.05ms   78.70%
    Req/Sec   455.97    105.35     0.93k    77.57%
  Latency Distribution
     50%  105.21ms
     75%  117.78ms
     90%  139.52ms
     99%  193.09ms
  136362 requests in 30.08s, 98.18MB read
Requests/sec:   4533.76
Transfer/sec:      3.26MB
```

Test Runs 
```
(100%CPU, ~200MB RAM)
1   256   176754 ******
1   512   174198
1   1024  154149

(120%CPU, ~200MB RAM)
2   256   176769
2   512   189316
2   1024  193556

(120%CPU, ~200MB RAM)
4   256   192097
4   512   164451
4   1024  169390
```


# GO FIBER

```shell
root@ubuntu-s-4vcpu-8gb-ams3-01:~/container-tests/go-fiber# docker run --rm -it -p 8080:8080 --cpus=1 --memory=256m --name go-fiber go-hello

root@ubuntu-s-4vcpu-8gb-ams3-01:~# wrk -t10 -c500 -d30s --latency http://localhost:8080/external
Running 30s test @ http://localhost:8080/external
  10 threads and 500 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency    70.24ms   27.05ms 228.58ms   69.57%
    Req/Sec   716.51    138.81     1.19k    70.60%
  Latency Distribution
     50%   71.33ms
     75%   87.77ms
     90%  101.47ms
     99%  145.10ms
  214152 requests in 30.06s, 149.70MB read
Requests/sec:   7125.31
Transfer/sec:      4.98MB
```

Test Runs 
```
(100%CPU, ~40MB RAM)
1   256   220957
1   512   234836
1   1024  235133

(140%CPU, ~40MB RAM)
2   256   315346
2   512   309287
2   1024  315592

(140%CPU, ~40MB RAM)
4   256   308941
4   512   312175
4   1024  318755
```


# Spring GraalVM

```shell
root@ubuntu-s-4vcpu-8gb-ams3-01:~/container-tests/spring-graal-vt# docker run --rm -it -p 8080:8080 --cpus=1 --memory=256m --name spring-graal-vt spring-graal-vt

root@ubuntu-s-4vcpu-8gb-ams3-01:~# wrk -t10 -c500 -d30s --latency http://localhost:8080/external
Running 30s test @ http://localhost:8080/external
  10 threads and 500 connections
  Thread Stats   Avg      Stdev     Max   +/- Stdev
    Latency   352.36ms  158.24ms   1.95s    74.27%
    Req/Sec   154.27     79.31   393.00     63.06%
  Latency Distribution
     50%  334.20ms
     75%  434.52ms
     90%  526.55ms
     99%  782.49ms
  42757 requests in 30.08s, 29.77MB read
Requests/sec:   1421.41
Transfer/sec:      0.99MB
```

Test Runs for GraalVM Native
```
CPU/MEM   #REQs
1   256   42757
1   512   49720

2   256   87122 ******
2   512   93314
2   1024  88016

4   256   87548
4   512   96454
4   1024  93219
```

Test Runs for JDK 21
```
1   256   ------
1   512   35152
1   1024  40987

2   256   ------
2   512   118025 ******
2   1024  111414

4   256   ------
4   512   139080
4   1024  134993
```


# Fly.io REGIONS (with Gateway)


dfw	Dallas, Texas (US)	✓
fra	Frankfurt, Germany	✓
hkg	Hong Kong, Hong Kong	✓

lhr	London, United Kingdom	✓
sin	Singapore, Singapore	✓
syd	Sydney, Australia	✓
nrt	Tokyo, Japan	✓



# Install Git & Docker

```shell
# Install utils
apt install -y unzip zip build-essential

# Install Git
apt install -y git-all

# Install wrk
apt install -y wrk

# Clean up docker
for pkg in docker.io docker-doc docker-compose docker-compose-v2 podman-docker containerd runc; do sudo apt-get remove $pkg; done

# Add Docker's official GPG key:
sudo apt-get update
sudo apt-get install -y ca-certificates curl
sudo install -m 0755 -d /etc/apt/keyrings
sudo curl -fsSL https://download.docker.com/linux/ubuntu/gpg -o /etc/apt/keyrings/docker.asc
sudo chmod a+r /etc/apt/keyrings/docker.asc

# Add the repository to Apt sources:
echo \
  "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/ubuntu \
  $(. /etc/os-release && echo "${UBUNTU_CODENAME:-$VERSION_CODENAME}") stable" | \
  sudo tee /etc/apt/sources.list.d/docker.list > /dev/null
sudo apt-get update


sudo apt-get install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

sudo docker run hello-world


```
