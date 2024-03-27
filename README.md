# Vocab

# Table of Contents
1. Introduction
2. Features
3. installation
4. Testing
5. Contributing
6. License

# 1. Introduction
The goal is to create a spaced repition vocabulary learning Web  Application (just vocabulary learner for now).
I tried to stick to REST architecture and the project is built in java with springboot (jpa CRUD)
and HTML & CSS for the views.

# 2. Features
- Login and registration
- Creating multiple decks and adding as much cards as you want (learn what ever you want)
- listings all cards and editing cards

(
- Security is not implemented as of right now! So no accesstokens or sessions. I had first 
used sessions in my projcet as you can see when you look at older versions of the project, but
realised its not rest compatibel (statelessness) and also i used Deck as identifier and after 
switching to rest using now only Id as resource identifier!
)

# 3. Installation
- prerequisite: installed IDE, JDK (I used 21.0.1 2023-10-17 LTS)

1. git clone https://github.com/NeXo333Xo/Vocab.git
2. execute the commands in starth2.sh to start the h2 server.
3. run the project and connect under localhost:8080 and  to the h2 server under localhost:8082

# 4. Testing
Testing is short in the project, because of much usage of throwables and not much experience
with it.
Run the project and start the server and test the website on localhost:8080
username: marlon password: 1234 or
username: max    password: 1234 

# 5. Contributing
Feel free to contribute

# 6. License
MIT LICENSE

