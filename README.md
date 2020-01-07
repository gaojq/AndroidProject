# AndroidProject
### Description
This application is for runners to share their favorite pictures they took during their running, and record their favorite locations they find while they run, more over there will be running time recorded in side of the local database, which is for the users to check their running history and running status.

##### Loginin
This app allows new users to sign up through the main page, if the username hasn’t been used.
Also, this section is able to detect invalid login, because the either the username or the password is not matching up with the one that stored in the server. Also, it is able to show all the users that exist in the server.
![image](https://user-images.githubusercontent.com/22155166/71921133-3a1aba00-3156-11ea-94e4-f35275661249.png)
  
##### UserList from server
The userlist section can show all the users that exist in the sever and each user on the list is clickable. The tool bar section can let users to share photos from their camera row or library.
![image](https://user-images.githubusercontent.com/22155166/71921150-4b63c680-3156-11ea-8909-8d927c7e4c63.png)
  
##### Share Photo
Enable to let all users to share their photos which they took from the surroundings or others, and the picture that user select will stored in the online server under the user’s name. In this case, other user can check out the picture that you have post.
![image](https://user-images.githubusercontent.com/22155166/71921246-7cdc9200-3156-11ea-9dab-966de8a348ca.png)
  
##### Remember Location
In the location section, people can add new location into this listview and can direct into this location through click. Currently the added new place is temporarily stored in sharedPreferences, so when the user logout, the data will be disappear.
![image](https://user-images.githubusercontent.com/22155166/71921268-882fbd80-3156-11ea-8709-450eb1e26125.png)
![image](https://user-images.githubusercontent.com/22155166/71921299-95e54300-3156-11ea-8d88-ac2c6f48a629.png)
![image](https://user-images.githubusercontent.com/22155166/71921323-a09fd800-3156-11ea-9c4d-7a6339429540.png)
 
##### Timmer
This timer function allows runners to count time every time they run, also it will show the current speed and average speed that the runner runs, moreover it can tell the total distance that the runner has run. The whole timer counting process is running in a service backend, and the service is connected with the UI thread by using Intent and Broadcast Receiver.
![image](https://user-images.githubusercontent.com/22155166/71921351-b2817b00-3156-11ea-9711-47287b948b54.png)
   
##### Create History
After the running activity, the user can click the stop button which can lead him to the saving page, where the user can decide if he/she want to keep this or not, if the user click on the button save, the new record will be stored in the local database. If click delete, it will direct back to the countTime page.
![image](https://user-images.githubusercontent.com/22155166/71921374-bb724c80-3156-11ea-86b7-33dda1639ea4.png)
 
##### History List
The history can be checked on the activity detail, which is a clickable listview, which can also show the activity title, time, total distance, and average speed.
![image](https://user-images.githubusercontent.com/22155166/71921465-eeb4db80-3156-11ea-9992-e58aec53411b.png)
 
##### Edit Page
When the listView is clicked, it will direct to the editActivity which looks exact like the saving activity, however, this activity directly read data from the database, so if the save button is clicked it will update the database data, if the delete button is clicked it will set the database line into null which will help delete.
![image](https://user-images.githubusercontent.com/22155166/71921419-d8a71b00-3156-11ea-8fa0-1d9d5534dcd2.png)
 
