# Getting Started

# Construction Backend
This Repository consists of the code in Maven Module structure for the supporting the Construction Application on the backend.
External Services like Database is provisioned through Docker

## Tech Stack

| Purpose  |      Tool     |
|----------|:-------------:|
| Programming Language |  Java 8 |
| Web Framework | Spring Boot |
| Relational Database | PostgreSQL |
| Database GUI Tool | PgAdmin |
| Authentication & Authorization | JWT |

## Setup

### Setting up Maven Multi-Module Structure
Refer this video: https://www.youtube.com/watch?v=6DZDox7JG9o

### Database Configuration
1. Run the docker-compose command
```
docker-compose -f Dockerfile.yml up -d
```

2. Then try to access pgAdmin via localhost:5050 with credentials: admin@admin.com, root

3. Create new Server by adding some Server Name and then going to Connection Tab and fetch the Host address
```
docker ps -> Fetch CONTAINER ID for Postgres
docker inspect <container id from above> | grep IPAddress
```
*For more detailed Explaination. Refer: https://towardsdatascience.com/how-to-run-postgresql-and-pgadmin-using-docker-3a6a8ae918b5*

4. Pass the IPAddress to Host Address and then for Username / Password: Refer the Dockerfile.yml

5. Then press Save to get connected to Postgres

6. To Stop all the Docker instances
```
docker stop $(docker ps -q)
```

