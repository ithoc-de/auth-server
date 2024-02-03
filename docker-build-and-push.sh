docker build -t olihock/auth-server:1.1.0 .
docker login
docker push olihock/auth-server:1.1.0
