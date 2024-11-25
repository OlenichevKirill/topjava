MealRestController

getAll
#### curl "http://localhost:8080/topjava/rest/meals"
#
get
#### curl "http://localhost:8080/topjava/rest/meals/100003"
#
filter without parameters
#### curl "http://localhost:8080/topjava/rest/meals/filter"
#
filter with parameters
#### curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-31&endTime=13:00"
#
delete
#### curl -X DELETE "http://localhost:8080/topjava/rest/meals/100003"
#
create
#### curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\": \"2024-11-25T16:11\", \"description\": \"text\", \"calories\": 2000}" "http://localhost:8080/topjava/rest/meals"
#
update
#### curl -X PUT -H "Content-Type: application/json" -d "{\"dateTime\": \"2024-11-25T16:58\", \"description\": \"updatedDescription\", \"calories\": 1500}" "http://localhost:8080/topjava/rest/meals/100003"