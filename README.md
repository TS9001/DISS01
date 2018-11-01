AXIV Social Stream parser app
==================
Small application for downloading of papers from social media stream.
So far it goes thought yor twitter home page and crawls for links leading to axiv.org and downloads PDF's.
Additionally you can list accounts to add and go trough 300 of their last statuses.

**If you want to contribute to development of this application, please don't hesitate to contact me**

Installation
------------
You can use maven and docker so the installation should be pretty straight forward.
Container is not polished yet, when I will have time I plan to return
to it and possibly add kubernetes (not so useful now but it would be great to have this as webapp so it can be useful in the future)

To configure your MongoDB access please change /src/main/resource/twitter.properties. (not very good placement, i know :))

Look for the same place, to change configuration of twitter connection and the path where to store papers.

Dependencies:
* Twitter developer accounts: <https://developer.twitter.com/>
* Twitter4J: <http://twitter4j.org/en/>
* SLF4J: <https://www.slf4j.org/>
* Spring-Boot: <http://spring.io/projects/spring-boot>
* MongoDB: <https://www.mongodb.com/>
* Maven: <https://maven.apache.org/download.cgi>
* Docker: <https://www.docker.com/>
