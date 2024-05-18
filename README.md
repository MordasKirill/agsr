To launch project:
Execute this command is CMD: docker-compose up --build

To test:
Call the following endoint to get access JWT token

http://localhost:8080/realms/agsr/protocol/openid-connect/token

Use following args in body:
![image](https://github.com/MordasKirill/agsr/assets/46963005/c4bd6ec0-5a78-47a0-8366-3e762505ac05)

client_id:springsecurity

username:test (Root user, has Patient and Pracririoner roles, also patient (patient role) and practitioner(practitioner role) users can be user)

password:admin

grant_type:password

client_secret:O9JNnP0Irzf5qhEB70Lq1SttANgFr4N5

Last step is to call any endpoint, using Authorization

For example:GET http://localhost:8081/agsr/api/patients

![image](https://github.com/MordasKirill/agsr/assets/46963005/ef84db78-88c8-48fe-aec5-785eac04c35b)

Expected result codes: 200 - Success; 401 - Incorrect token; 403 - Not enought roles to access endpoint

Other endpoints:

GET http://localhost:8081/agsr/api/patients/{id}

POST http://localhost:8081/agsr/api/patients

DELETE http://localhost:8081/agsr/api/patients/{id}

PUT http://localhost:8081/agsr/api/patients/{id}




Необходимо создать REST-сервис с авторизацией по протоколу OAuth2 используя OpenSource решение для сервера авторизации.  

1. Создать REST-сервис, который содержит CRUD-методы для сущности Patient

Пример сущности в формате JSON:

 {

    id" : "d8ff176f-bd0a-4b8e-b329-871952e32e1f",

   "name": "Иванов Иван Иванович",

   "gender": "male",

   "birthDate": "2024-01-13T18:25:43",

  }


2. Каждый метод сервиса должен быть доступен только авторизованному пользователю с правами на выполнение метода:

 методы GET - > Role: Practitioner -> Permission: Patient.Read

 методы GET - > Role: Patient -> Permission: Patient.Read

 методы POST/PUT- > Role: Practitioner -> Permission: Patient.Write

 методы Delete- > Role: Practitioner -> Permission: Patient.Delete


3. Добавить в проект описание методов API с помощью swagger.


4. Разработать консольное приложения для добавления через API 100 сгенерированных сущностей Patient.


5. Сделать Postman-коллекцию для демонстрации методов


6. Создать Dockerfile и реализовать запуск разработанного программного обеспечения (включая БД) в виде docker-контейнеров.


7. Результат выполнения тестового задания положить в открытый репозитарий git-сервера (GitHub, GitLab, ect.).

