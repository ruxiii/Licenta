# Licenta

***E/R***  - https://app.diagrams.net/#G1T7educXcqF1XYm2mvA7KJQ9-c6r3mKnA#%7B%22pageId%22%3A%22C5RBs43oDa-KdzZeNtuy%22%7D

***trello***  - https://trello.com/b/GoCR6k4F/licenta

***15.02.2024***  - setup done(?) + connection with the mysql database + frontend project made

***16.02.2024***  - e/r diagram done + its implementation + entities + repositories + relationships based on foreign keys + uploaded images kind of started

***17.02.2024***  - starting point for images + CRUD on user + services + controllers + mappers files made

***22.02.2024***  - authetication almost done (not sure about that?) + authentication table that extracts only the fields needed for user + create user done an verified from postman (regarding authentication spring security should be uncommented from pom.xml and so the other files - HomeController and authentication package) 

***23.02.2024***  - CRUD on departments, teams, events(this is not tested yet) + FK problem done for users and teams

***25.02.2024***  - CRUD on maps (without pictures) + CRUD on events finished

***07.03.2024***  - CRUD on places + CRUD on reservations not finished (it doesn't work with event) + Backend connected to frontend

***08.03.2024***  - CRUD almost done on reservations (it works for now but it's not that correct) + list of all objects exposed in frontend 

***15.03.2024***  - Add a user in the database from frontend + some minor changes in the backend in order to work 

***16.03.2024***  - UserId now generated from backend (make sure there will be a pop up that shows the user his id) + frontend adapted for backend + fields introduced in the database with uppercase + dark mode added (only works for all users and to add a user in the database; others should be made after this example) + confirm password added + password hashed in the database 

***17.03.2024***  - Pagination for users 

***23.03.2024***  - Popup with the userId when creating an user 

***31.03.2024***  - Home page added, name changed, favicon changed

***14.04.2024***  - Authentication started seriously in the backend, admin added in both authentication and user table, these things tested from postman (min 58 from https://www.youtube.com/watch?v=TeBt0Ike_Tk)

***15.04.2024***  - UserFullDto and UserDto modified so that it fits to create an user correctly (TBD if so), all things locked if you're not logged in (only works in backend for now), login done in backend but in SecurityConfig the access for the endpoints needs to be modified, see TODO in that file (***TODO: enpoint for admin*** min 1:40:15 from https://www.youtube.com/watch?v=TeBt0Ike_Tk; authentication can only be made withe the token jwt or smth -> this will give the role for the person who is using the app; so if i get a 403 Unauthorized and i didn t logged with the token before it is normal because THE TOKEN GIVES THE ROLE)  

***17.04.2024***  - Login endpoint, admin files created for /admin, home adapted to login endpoint, register -> create an user, login -> /login but the submit button doesn't work, started adapting the frontend to the jwt token from backend (min 23:30 from https://www.youtube.com/watch?v=YUqi1IjLX8I)

***19.04.2024***  - Validators for email, password and confirm password (dynamic), token exposed in the frontend but for some reason when i submit the loginform first in console appears 401, then it works BUT when i try to access an endpoint that only an user can access, it doesn't work (i have to finish the last part of this https://www.youtube.com/watch?v=YUqi1IjLX8I)

***20.04.2024***  - Authentication and authorization work in the backend (tested from postman), but the Bearer token doesn't want to be included in the header, create an account and login work without an error, correction when creating an account (the submit became active even if the passwords weren't the same), color changes on the app, pop-up updated   

***21.04.2024***  - Authentication and authorization work in both backend and frontend!!!!!!!!!!!!!!!!!!!

***26.04.2024***  - Authentication and authorization work pretty good in both backend and frontend, Hello {{user}}, constraint so that only the users from the database can be logged, logout button, some authorizations added (have a look later)  

***27.04.2024***  - Buttons appear based on the roles, problem done with /home and /welcome, dep id added with uppercase in the database, AS AN ADMIN: add a new department from frontend, constaint so that there aren't two departments with the same id, delete a department, update endpoint added, but the update doesn't work yet
