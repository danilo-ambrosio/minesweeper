# minesweeper-API

# Infrastructure Requirements

The only requirement for this application is a [Mongo DB](https://www.mongodb.com/) instance. Therefore, it's necessary to create an environment variable named *MINESWEEPER_DATABASE_URI* through which the application can connect to the Mongo DB.

# Running Minesweeper API

Once the database is running and the environment variable for the database URI is created, execute the following command to run the application: 

```
$ mvn spring-boot:run
```

Having the application up and running, the minesweeper service endpoints will be available at `http://localhost:8080`. Next, it's described how to communicate with this app via REST.

# Rest API

## Configuring a Minesweeper Game

* POST /games

Payload example:

```
{
   "rows":6,
   "columns":10,
   "numberOfMines":8
}
```

## Changing Game Status

* PATCH /games/{gameId}/status
  
Payload example:

```
{
   "reason":"GAME_PRESERVATION"
}
```

## Performing Cell Operations

* PATCH /games/{gamesId}/cell

Payload example:

```
{
   "rowIndex":8,
   "cellIndex":3,
   "type":"UNCOVERING"
}
```

## Querying paused games

* GET /games

Response example:

```
{
   "id":"c70593af-34aa-4f91-b2e8-052e39a7e2d1",
   "status":"PAUSED",
   "startedOn":1632447451304,
   "timeElapsed":5,
   "rows":[
      {
         "index":0,
         "cells":[
            {
               "index":0,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":1,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":2,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":3,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":4,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":5,
               "status":"COVERED",
               "type":"",
               "mines":0
            }
         ]
      },
           {
         "index":5,
         "cells":[
            {
               "index":0,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":1,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":2,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":3,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":4,
               "status":"COVERED",
               "type":"",
               "mines":0
            },
            {
               "index":5,
               "status":"COVERED",
               "type":"",
               "mines":0
            }
         ]
      }
      ...
   ]
}
```

## Registering users

* POST /users

Payload example:

```
{
   "username":"ms-gamer",
   "password":10
}
```

# Heroku

Access the Minesweeper UI on Heroku at `https://dg-minesweeper.herokuapp.com/`.

# Development History

The development history of this project along with the time spent on each code increment is recorded on the [changelog](https://github.com/danilo-ambrosio/minesweeper/blob/master/changelog.md).
