# Backend REST service Explore With Me


## Сервис для публикации и поиска событий

Позволяет пользователям делиться информацией об интересных событиях и находить компанию для участия в них. Реализовано 2 сервиса:
* **основной сервис** для работы с событиями; 
* **сервис статистики** хранит количество просмотров и позволит делать различные выборки для анализа работы приложения.

## Требования
* git
* jdk 11
* maven
* docker

## Установка и запуск
### Шаг 1. Скачивание проекта
```
git clone https://github.com/yelgazin/java-explore-with-me.git
```

### Шаг 2. Сборка проекта
```
cd java-explore-with-me
mvn install
```

### Шаг 3. Запуск проекта

```
docker-compose up
```

## Спецификация API
Cпецификация в формате Swagger доступна по следующим эндпоинтам:
* спецификация основного сервиса: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html);
* спецификация сервиса статистики: [http://localhost:9090/swagger-ui.html](http://localhost:9090/swagger-ui.html).


## Технологический стэк

![java](https://img.shields.io/badge/java-%23ed8b00.svg?logo=openjdk&logoColor=white&style=flat)
![spring](https://img.shields.io/badge/spring-%236db33f.svg?logo=spring&logoColor=white&style=flat)
![postgres](https://img.shields.io/badge/postgres-%23336791.svg?logo=postgresql&logoColor=white&style=flat)
![postgis](https://img.shields.io/badge/postgis-blue)
![hibernate](https://img.shields.io/badge/Hibernate-59666C?style=flat&logo=Hibernate&logoColor=white)
![postman](https://img.shields.io/badge/Postman-FF6C37?style=flat&logo=postman&logoColor=white)




