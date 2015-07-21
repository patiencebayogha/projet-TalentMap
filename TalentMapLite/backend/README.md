# Talent Map Lite

1. Context
2. How to run
3. Configuration
3. Development
	1. Hierarchy
4. Production



Context
------



How to run
------

**Prerequires:**

- Install MogoDB
	- [http://www.mongodb.org/downloads](http://www.mongodb.org/downloads)
- Install Node.js
	- [http://nodejs.org/download/](http://nodejs.org/download/)
- Install Ruby et Sass
	- [http://rubyinstaller.org/](http://rubyinstaller.org/)
	- puis "gem install sass"

----------


**Run MongoDB:**


    mongod.exe --dbpath e:\mongodb\

Where "e:\mongodb\" is a MongoDB data path previously created.

----------



**Clone GitHub repository:**

After creation of a work directory, place you inside it and clone the repository :

    git clone https://github.com/alertmama/stage2015.git


----------

**Run the backend**


- Install TML backend dependencies

Backend is writtend in Node.JS, so you may use NPM to install dependencies :


    cd stage2015/TalentMapLite/backend
	npm install

- Run TML backend

TML backend could be run in both development or production profile, the only difference between the two profile is the configuration file used (see the following "configuration" section for more details).

by default, development profile is used, but it is possible to explicitly set the profile by setting the NODE_ENV environment variable as follow :

    NODE_ENV=production

or

	NODE_ENV=development
	DEBUG=*

It is now possible to start TML by running :

	npm run build
	npm run start


----------

**Run the frontend**

- Install TML frontend dependencies

Frontend use Gulp to perform build tasks, so you may use NPM to install dependencies :


    cd stage2015/TalentMapLite/frontend
	npm install

- Run TML frontend

TML frontend could be run in both development or production profile. It is possible to explicitly set the profile by running :

	gulp clean && gulp server_prod

or

	gulp clean && gulp server_dev
	
**Access from web browser**

With google chrome :

	"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" --disable-web-security


Configuration
------

- Backend

Two configuration files exists and are  applied to development and production environment.

These files are available in 
> stage2015/TalentMapLite/backend/config-dev.js

and

> stage2015/TalentMapLite/backend/config-prod.js

- Frontend

Two configuration files exists and are  applied to development and production environment.

These files are available in 
> stage2015/TalentMapLite/frontend/gulp/config-dev.js

and

> stage2015/TalentMapLite/frontend/gulp/config-prod.js


Development
------

- **Hierarchy**



The Backend hierarchy is the following :


	└───backend						-- App, config and run-scripts
    	├───bin
    	├───cert					-- SSL Certificate
	    ├───data					-- Database populate scripts (DEV)
	    │   ├───public					-- Contain public resources (Express)
	    ├───doc						-- Swagger documentation for API
	    ├───routes					-- Express routes for API REST
	    │   └───api					
	    │       └───v1				-- API V1
	    ├───schemas					-- Data schemas (users, skills ...)
	    └───services				-- Data retriever services

The Frontend hierarchy is the following :

	└───frontend					-- App, config
	    └───src						-- Contain public resources (Express)
	        ├───app					-- AngularJS app
	        │   ├───profile			-- AngularJS profile related 
	        │   └───services		-- AngularJS services
	        ├───data				-- Contain hard-coded data (Categories) 
	        ├───images				-- Public images
	        ├───js					-- Javascript
	        ├───stylesheets			-- Stylesheets
            ├───fonts				-- fonts
            └───images				-- images



Production
------
