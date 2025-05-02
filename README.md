# Spring RESTful Application
This is a simple service to keep trace of student's grades on exams.
## Launching
### Requirements
- Git (to dowload the repository using the console)
- Docker Desktop
- JDK 21 or newer
- Postman or other instrument for testing API
### Steps
1. Download repository from site and unzip in some place on disk or clone repository by command on the command line:
   
   ```bash
   git clone https://github.com/LightlyAfternoon/RestServiceHomework.git
   ```
   
3. Run Docker Desktop
4. Execute command on the command line in RestServiceHomework folder:

   ```bash
   docker-compose -f docker-compose.yml up
   ```

## Work with service
For working with service you can use such instrument as Postman - sending HTTP requests on URL <http://localhost:8082/>.
- Get all teachers: send GET request on http://localhost:8082/teacher/
- Get a teacher with id: send GET request on http://localhost:8082/teacher/id
- Add a teacher: send POST request on http://localhost:8082/teacher/ with JSON body:
  
  ```json
  {
        "id": 0,
        "firstName": "Teacher name",
        "lastName": "Teacher last name",
        "patronymic": "Teacher patronymic"
  }
  ```
  
- Edit a teacher with id: send PUT request on http://localhost:8082/teacher/id with JSON body:
  
  ```json
  {
        "id": 0,
        "firstName": "New teacher name",
        "lastName": "New teacher last name",
        "patronymic": "New teacher patronymic"
  }
  ```

- Delete a teacher with id: send DELETE request on http://localhost:8082/teacher/id
- Add a record connecting the subject with id1 and the teacher it is teaching with id2: send POST request on http://localhost:8082/subject/id2/teacher/id2
