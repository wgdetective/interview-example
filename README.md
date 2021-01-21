# Wladimir Litvinov Java Interview Example

## Description
Rest application for simple elections.
User can get list of candidates.
User can vote for candidate.
User can check current results of elections.

Candidates are stored in Json file.
One user should be allowed to vote only one time.
User is identified by passport s/n.
Results are stored in application memory.

System should have a good architecture.

## User stories
### 1) As a User, I want to get the list of candidates so that I can vote.

Request:
`GET v1/candidates`

Response:
`200 OK`
```json
[
    {      
        "id" : 1,      
        "name" : "A. Luck"  
    },
    {
        "id" : 2,
        "name" : "S. Tich"
    },
    {
        "id" : 3,
        "name" : "N. Unkn"
    }
]
```

### 2) As a User, I want to vote for the candidate so that number of voices changed.

Request:
`POST v1/vote`
```json
{
  "name" : "Wladimir Litvinov",
  "passport" : "123455",
  "candidateId" : 3
}
```

Response:
`200 OK`
```json
{
  "message" : "Your voice was accepted."
}
```

#### Corner cases:
- candidateId should be validated, if there is not such candidate User should get such response:
  `400 Bad Request`
```json
{
  "message" : "Error: there is no such candidate."
}
```
- if User already voted, then voice shouldn't be counted, and User should get response:
  `400 Bad Request`
```json
{
  "message" : "Error: you already voted."
}
```

### 3) As a User, I want to get current elections results, and I get them.

Request:
`GET v1/results`

Response:
`200 OK`
```json
[
  {
    "id" : 1,
    "name" : "A. Luck",
    "voices" : 0,
    "percentage" : 0
  },
  {
    "id" : 2,
    "name" : "S. Tich",
    "voices" : 0,
    "percentage" : 0
  },
  {
    "id" : 3,
    "name" : "N. Unkn",
    "voices" : 1,
    "percentage" : 100
  }
]
```