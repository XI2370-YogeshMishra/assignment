
I have also used swagger for documention and the url for swagger is as follows.
http://localhost:8080/swagger-ui.html


End points Resource Uri:
http://localhost:8080/api/players/ 
To get All the Player List (Get request)
http://localhost:8080/api/players/ {playerid}
To get the details of player by playerID(Get request)
http://localhost:8080/api/players/match/{matchId}
To get the player detail by match id(Get request)
http://localhost:8080/api/players/league/{leagueid} 
To get the league matches details (Get request)
http://localhost:8080/api/players/add
For adding/Participating new Player(Post request)

Match Controller

http://localhost:8080/api/matches 
This will provide all the dynamic created matches for registered players(Get request)
http://localhost:8080/api/matches/{matchid} 
This end point will provide details of match by match id (Get request)
http://localhost:8080//api/matches/edit/{matchId}
This end point is used for updating match result and closing the round.(Post request)

League Controller

http://localhost:8080/api/leagues 
This end point will provide all the available league matches.(Get request)
http://localhost:8080/api/leagues /add      (Post Request)
This end point is used to add a new league match.(Post request)
http://localhost:8080//api/leagues/{WiningPlayerID}
This end point is used for updating winner as well as sending email to the winner(Put request)