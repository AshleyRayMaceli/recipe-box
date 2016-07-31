![Homepage and search page](search_homepage.png)
![Recipe page](recipe.png)

# _Recipe Box_

#### _A web app where the user can add recipes, ingredients and tags to a database with SQL_

#### By _**Ashley Maceli and Adam Craig**_

## Description

_A web app designed with Java that allows a user to utilize a database to create, view, edit, and remove recipes, ingredients, and tags(categories). Ingredients and tags have a many-to-many relationship with recipes._

##Database Tables

![Database](sqldesign.png)

## Setup/Installation Requirements

* _SETTING UP THE DATABASE AND TEST DATABASE_
* _Clone repository to desktop_
* _Use console to enter directory with all files_
* _In a new console window run the command 'postgres' and keep running_
* _In bash console run the command 'psql cooking < cooking.sql'_
* _In a new console window run the command 'psql' then 'CREATE DATABASE cooking;'_
* _For test database run the command '\c cooking' to connect to the database_
* _To create the test database run the command 'CREATE DATABASE cooking_test WITH TEMPLATE cooking;'_
* _RUNNING THE WEB APP_
* _In console run the command 'gradle run'_
* _Go to http://localhost:4567/_

## Known Bugs

_Duplicate ingredients and tags within the same recipe are currently allowed (unintended)._

## Support and contact details

_For all issues and support, please contact:
Ashley Maceli at ashley.maceli@gmail.com
Adam Craig at ajcraig@suffolk.edu_

## Technologies Used

_Java, SQL, Spark, Velocity, HTML, CSS, Gradle, JUnit, FluentLenium_

### License

The MIT License (MIT)

Copyright (c) 2016 Ashley Maceli & Adam Craig

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
