# Smart City - Citizen Engagement - A pratical work for Web Services

## Introduction

The implementation is done with:

- [Spring Boot](http://projects.spring.io/spring-boot/)
- [Spring Data MongoDB](http://projects.spring.io/spring-data-mongodb/)
- [MongoDB](http://www.mongodb.org/)
- [Jersey](https://jersey.java.net/)

## Requirements

- Java 1.6+
- Maven 3.0.5+
- Mongo 2.6+

## Install / Deploy / Run

Just run the following command (or use the proper Run goal in Custom in NetBeans). Be sure MongoDB is running before running the command.

```bash
$> mvn clean package spring-boot:run
```

## Implementation

The current implementation is based on the info from [iFlux blog's post](http://www.iflux.io/use-case/2015/02/03/citizen-engagement.html).

For the model, we have pretty much the same as the Node.js implementation. They are subtle differences as the Spring Data MongoDB layer add
additional data to make the matching between the Java types and the documents stored in the DB.

### Data model:

- `Issue`, `IssueType` and `User` are three separate models (documents).
- `Issue` has:
	- `issueType` (`{'$id': ObjectId, '$ref': '<collectionName>'}`),
	- `owner` (`{'$id': ObjectId, '$ref': '<collectionName>'}`),
	- `assignee` (`{'$id': ObjectId, '$ref': '<collectionName>'}`),
	- `Tags` is just a list of string attached to an issue,
	- `comments` is a list of `Comment` that is a sub model (sub-document),
	- `updatedOn`: Date that should be updated each time the model is updated (done through a a custom `@Repository`. Use a default value corresponding to `Date.now`,
	- `state`: Just a string that we play with. Used states: `created`, `acknowledged`, `assigned`, `in_progress`, `solved`, `rejected`,
	- `description`: Free text to describe the issue,
	- `lat` and `lng`: Coordinates where the issue is located,
- `User` has:
	- `firstname`, `lastname` and `phone` that are `String`,
	- `roles` is a collection of `String` and should contains: `['citizen']`, `['staff']` or `['citizen', 'staff']`
- `IssueType`
	- `name` and `description` that are `String`
- `Comment` has:
	- `text` is a `String`
	- `postedOn` is a `Date` when the comment is posted,
	- `author` is the `User` who has posted the comment.  (`{'$id': ObjectId, '$ref': '<collectionName>'}`).

### Services:

- `ActionService` is responsible to process the different actions from the `ActionResource`

### Authentication / Authorization:

The authentication is done when the annotation `@Roles` is present on a path/method in a resource. There is a simplification to avoid creating
two different annotations. The authentication mechanism lookup for HTTP Header `x-user-id`.

For the authorization, the default behavior when `@Roles` is set without parameters is to allow an authenticated user to access the resource. This
is a way to define a role `any`. Otherwise, the annotation can be configured like `@Roles({ "staff", "citizen"})` and then the roles are checked
against the authenticated user.

The whole implementation of the both mechanism is done through the `DynamicFeature` from JAX-RS/Jersey. You can find more code to read in classes
`AuthenticationDynamicFeature`, `AuthenticationFilter` and `CityEngagementSecurityContext`. The authentication / authorization checks is done in
the `AuthenticationFilter`.

Once a user is authenticated / authorized, a custom security context is populated and injected in the resource through the annotation `@Context`.
This allow to get the current user when necessary in the resources.

### Paging and Sorting

Pagination and sorting is done through a `@BeanParam` directly injected in the resource method (like `findAll`). The constructor of the `PagerAndSorterParams`
class check for both `x-pagination` and `x-sort` HTTP headers. If one or more headers are missing, the constructor fallback to default values
to let the resource assume that paging and sorting is always usable without worry.

### Resources:

**Base path is `/api`**

- `Issue /issues`
	- `GET /` Retrieve all issues (pagination and sort is supported)
	- `POST /` Create new issue
	- `POST /search` Allow querying the issues (not implemented)
	- `GET /:id` Retrieve one issue
	- `POST /:id` Update one issue
	- `DELETE /:id` Delete one issue
- `User /users`
	- `GET /` Retrieve all users (pagination and sort is supported)
	- `POST /` Create new user
	- `GET /:id` Retrieve a user
	- `POST /:id` Update a user
	- `DELETE /:id` Delete a user
- `IssueType /issueTypes`
	- `GET /` Retrieve all issue types (pagination and sort is supported)
	- `POST /` Create new issue type
	- `GET /:id` Retrieve a issue type
	- `POST /:id` Update a issue type
	- `DELETE /:id` Delete a issue type
- `Action /issues`
 - `POST /:id/actions` Submit an action to do on an issue. Allowed actions: `comment`, `addTags`, `removeTags`, `replaceTags`, `assign`, `ack`, `start`, `reject` and `resolve`
- `Data /data`
	`POST /populate` Populate random data for the app to have something to test for the pagination and sorting

## Deploy on Heroku

1. Create the application in the Heroku admin console
2. If not already done, provide Credit Card infos
3. Type the command in the directory app on your machine `heroku addons:add mongolab`
4. Visit again your Heroku admin console to view the app details. You will find a link to see the admin console of the addon.
5. Once you are in the admin console of the addon, you can create a new database user. Do it with the following info:
	- User: `cityengagement`
	- Password: `cityengagement`
6. Configure environment variable to start the app with the following commands:
	- Set the user: `heroku config:set SB_MONGO_USER=cityengagement`
	- Set the password: `heroku config:set SB_MONGO_PASSWORD=cityengagement`
	- Set the db name: `heroku config:set SB_MONGO_DB={dbName}`
	- Set the host: `heroku config:set SB_MONGO_HOST={host}`
	- Set the port: `heroku config:set SB_MONGO_PORT={port}`
	- Set the environment: `heroku config:set SB_ENV=production`
7. Finally, you can push your code to Heroku
	- cmd: `git push heroku master`

## TODO

- Change the type of `lat` and `lng` for coordinates storage in `Issue`
- Implement the `query` information part