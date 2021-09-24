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
   "reason":"GAME_PRESERVATION",
   "timeElapsed":5000
}
```

## Performing Cell Operations

* PATCH /games/{gamesId}/cell

Payload example:

```
{
   "rowIndex":8,
   "cellIndex":3,
   "timeElapsed":4000,
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
   "timeElapsed":5000,
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

-----------------

API test

We ask that you complete the following challenge to evaluate your development skills. Please use the programming language and framework discussed during your interview to accomplish the following task.

PLEASE DO NOT FORK THE REPOSITORY. WE NEED A PUBLIC REPOSITORY FOR THE REVIEW.

## The Game
Develop the classic game of [Minesweeper](https://en.wikipedia.org/wiki/Minesweeper_(video_game))

## Show your work

1.  Create a Public repository ( please dont make a pull request, clone the private repository and create a new plublic one on your profile)
2.  Commit each step of your process so we can follow your thought process.

## What to build
The following is a list of items (prioritized from most important to least important) we wish to see:
* Design and implement  a documented RESTful API for the game (think of a mobile app for your API)
* Implement an API client library for the API designed above. Ideally, in a different language, of your preference, to the one used for the API
* When a cell with no adjacent mines is revealed, all adjacent squares will be revealed (and repeat)
* Ability to 'flag' a cell with a question mark or red flag
* Detect when game is over
* Persistence
* Time tracking
* Ability to start a new game and preserve/resume the old ones
* Ability to select the game parameters: number of rows, columns, and mines
* Ability to support multiple users/accounts

## Deliverables we expect:
* URL where the game can be accessed and played (use any platform of your preference: heroku.com, aws.amazon.com, etc)
* Code in a public Github repo
* README file with the decisions taken and important notes

## Time Spent
You need to fully complete the challenge. We suggest not spending more than 3 days total.  Please make commits as often as possible so we can see the time you spent and please do not make one commit.  We will evaluate the code and time spent.

What we want to see is how well you handle yourself given the time you spend on the problem, how you think, and how you prioritize when time is sufficient to solve everything.

Please email your solution as soon as you have completed the challenge or the time is up
