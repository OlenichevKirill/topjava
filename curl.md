Get all meals
#### curl "http://localhost:8080/topjava/rest/meals"
#
Get meal by id = 100003
#### curl "http://localhost:8080/topjava/rest/meals/100003"
#
Get meals without filter 
#### curl "http://localhost:8080/topjava/rest/meals/filter"
#
Get meals filtered by date and time
#### curl "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-31&endTime=13:00"
#
Delete meal by id = 100003
#### curl -X DELETE "http://localhost:8080/topjava/rest/meals/100003"
#
Create new meal
#### curl -X POST -H "Content-Type: application/json" -d "{\"dateTime\": \"2024-11-25T16:11\", \"description\": \"text\", \"calories\": 2000}" "http://localhost:8080/topjava/rest/meals"
#
Update meal by id = 100003
#### curl -X PUT -H "Content-Type: application/json" -d "{\"dateTime\": \"2024-11-25T16:58\", \"description\": \"updatedDescription\", \"calories\": 1500}" "http://localhost:8080/topjava/rest/meals/100003"