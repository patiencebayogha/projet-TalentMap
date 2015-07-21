# Database Schema

MongoDB est utilisé en tant que base de donnés.
Les données sont non-structuré, en revanche, il est nécessaire d'organiser les relations One-To-Many par réference afin de lister rapidement les compétences (sans avoir à parcourir les utilisateurs).

Mongoose est utilisé afin de facilité a créaion des schémas donnant une vision "structuré"


1. Categories
2. Skills
3. Users



Categories / Catégories
------

Under JSON format at /public/data/categories.json

- **id**
  : identifiant de la catégorie

- **category**
  : nom de la catégorie


Exemple :

[
    {
        "id": "b68d759c-a640-11e4-89d3-123b93f75cba",
        "category": "Langues"
    },
    {
        "id":"c09024a4-a640-11e4-89d3-123b93f75cba",
        "category": "Devloppement"

    },
    {
        "id": "d06bfe2a-a640-11e4-89d3-123b93f75cba",
        "category": "Management"
    },
    {
        "id": "5801da44-a641-11e4-89d3-123b93f75cba",
        "category": "Bureautique"
    },
    {
        "id": "6103760c-a641-11e4-89d3-123b93f75cba",
        "category": "Loisirs"
    }
]



Skills / Compétences
------

- **id**
  : identifiant de la compétence

- **name**
  : nom de la compétence

- **volatile**
  : vraie si la compétence doit être supprimer lorsque plus aucun utilisateurs ne possède cette compétence

- **category**
  : identifiant de la categorie rattaché


Exemple :

    {
       _id: 4567,
       name: "HTML",
	   volatile: false
       category: 6103760c-a641-11e4-89d3-123b93f75cba
    }



Users / Utilisateurs
------


- **id**
  : identifiant de l'utilisateur

- **email**
  : mail de l'utilisateur

- **password**
  : mot de passe de l'utilisateur hasher en MD5

- **categories**
  : liste des catégories liées à l'utilisateur

	- **skills**
	: liste des compétences liées à l'utilisateur

	- **level**
	: niveau de la compétence

- **name**
  : nom de l'utilisateur

- **surname**
  : prénom de l'utilisateur

- **photo**
  : photo de l'utilisateur

- **active**
  : vraie si le compte utilisateur à été activé

Exemple :

	{
	    _id: 7891,
	    email: "surname.name@viseo.com",
	    password: "13b104005f3c996a480f5899613c449e",
	    categories: [
		    {
		       _id: 1234,
		       category: "Languages",
			   skills: [
	                {
	                    _id: 4567,
					    skill: "HTML",
	                    level: 5
	                },
	                {
	                    _id: 8546,
						skill: "JEE",
	                    level: 3
	                }
			   ]
		    },
		    {
		       _id: 1234,
		       name: "Management",
			   skills: [ ]
		    }
	    ],
	    name: "name",
	    surname: "surname",
	    photo: "",
	    active: false
	}
